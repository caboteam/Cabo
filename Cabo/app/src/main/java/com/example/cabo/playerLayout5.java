package com.example.cabo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class playerLayout5 extends AppCompatActivity {

    Cabo game = new Cabo(5);
    int order = 0;
    boolean pickCard, pickCard_discard, keep_card, card_pick = false;
    int card_number;

    private void translate(View viewToMove, View target, long n) {
        viewToMove.animate()
                .x(target.getX())
                .y(target.getY())
                .setDuration(n)
                .start();

        System.out.println(target.getX());
        System.out.println(target.getY());
    }

    private void flip(final ImageView imageView, final int change, final int scale) {
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(imageView, "scaleX", 0f, 1f);

        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                super.onAnimationEnd(animation);
                imageView.setImageResource(change);
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                ObjectAnimator translationX = ObjectAnimator.ofFloat(imageView, "x", metrics.widthPixels / 2 - imageView.getWidth() / 2);
                translationX.setDuration(400);
                translationX.start();
                imageView.animate().scaleX(scale).scaleY(scale).setDuration(400);
                oa2.start();
                oa2.setDuration(400);
                imageView.bringToFront();
            }
        });
        oa1.start();
        oa1.setDuration(400);

    }

    private void disableAllButtons() {
        ImageView button1 = findViewById(R.id.discard_button_in);
        ImageView button2 = findViewById(R.id.power_button_in);
        ImageView button3 = findViewById(R.id.keep_button_in);
        ImageView button4 = findViewById(R.id.cabo_button_in);

        button1.setVisibility(button1.INVISIBLE);
        button2.setVisibility(button2.INVISIBLE);
        button3.setVisibility(button3.INVISIBLE);
        button4.setVisibility(button4.INVISIBLE);
    }

    public void pickCardDeck(View view) {
        if (game.turn.getCount() == order && !pickCard_discard && !pickCard){
            pickCard = true;
            ImageView button = findViewById(R.id.cabo_button_in);
            button.setVisibility(button.INVISIBLE);

            final ImageView imageView = findViewById(R.id.deck_dummy);
            //imageView.setVisibility(imageView.VISIBLE);
            flip(imageView, R.drawable.swap, 3);
        }
    }

    public  void  pickDiscardCard(View view) {
        if (game.turn.getCount() == order && !pickCard) {
            System.out.println("DISCARD PILE CARD");
            pickCard_discard = true;
            keep_card = true;

            disableAllButtons();

            final ImageView imageView = findViewById(R.id.discard_dummy);

            imageView.animate().x(100).y(100).setDuration(400).start();
            imageView.animate().scaleX(1.5f).scaleY(1.5f).setDuration(400);
        }
    }

    public void rules(View view) {
        TextView rule = findViewById(R.id.rules);
        rule.setVisibility(rule.VISIBLE);
    }

    public void remove_rules(View view) {
        int is_visible;
        TextView rule = findViewById(R.id.rules);
        is_visible = rule.getVisibility();
        if (is_visible == 0)
            rule.setVisibility(rule.INVISIBLE);
    }

    public void goToMenu(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Quitting the game");
        builder.setMessage("Are you sure? You won't be able to come back!!");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void selectCard(View view) {
        ImageView card = (ImageView) view;
        card_number = Integer.parseInt(card.getTag().toString());

        if (keep_card){
            final int card_change, final_card, n;
            final ImageView back;
            if(card_pick){
                n = 0;
                final_card = R.drawable.card_back;
                card_change = R.id.deck_dummy;
                back = findViewById(R.id.deck);
            } else {
                n = 400;
                final_card = R.drawable.swap;
                card_change = R.id.discard_dummy;
                back = findViewById(R.id.discard_pile);
            }

            final ImageView imageView = findViewById(card_change);

            translate(imageView, card, 400);
            imageView.animate().scaleX(1).scaleY(1).setDuration(400);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    translate(imageView, back, n);
                    imageView.setImageResource(final_card);
                }
            }, 1000);

            if(card_pick){
                
            }

            disableAllButtons();
        }
    }

    public void buttonClick(View view) {
        if (game.turn.getCount() == order) {
            if(!pickCard && (view.getId() == R.id.cabo_button_in || view.getId() == R.id.cabo_button_out)){
                System.out.println("Cabo Called!");
                disableAllButtons();
            }
            else if(pickCard && !pickCard_discard && view.getId() == R.id.discard_button_out){
                System.out.println("Card discarded!");

                final ImageView discard = findViewById(R.id.discard_pile);
                final ImageView imageView = findViewById(R.id.deck_dummy);
                final ImageView back = findViewById(R.id.deck);

                translate(imageView, discard, 400);
                imageView.animate().scaleX(1).scaleY(1).setDuration(400);
                discard.setImageResource(R.drawable.swap);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        translate(imageView, back, 0);
                        imageView.setImageResource(R.drawable.card_back);
                    }
                }, 3000);

                disableAllButtons();
            }
            else if(pickCard && !pickCard_discard && view.getId() == R.id.power_button_out) {
                System.out.println("Card power used!");

                disableAllButtons();

                final ImageView discard = findViewById(R.id.discard_pile);
                final ImageView imageView = findViewById(R.id.deck_dummy);
                final ImageView back = findViewById(R.id.deck);

                translate(imageView, discard, 400);
                imageView.animate().scaleX(1).scaleY(1).setDuration(400);
                discard.setImageResource(R.drawable.swap);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        translate(imageView, back, 0);
                        imageView.setImageResource(R.drawable.card_back);
                    }
                }, 3000);
            }
            else if(pickCard && !pickCard_discard && view.getId() == R.id.keep_button_out) {
                System.out.println("Card kept!");

                disableAllButtons();

                final ImageView imageView = findViewById(R.id.deck_dummy);

                imageView.animate().x(100).y(100).setDuration(400).start();
                imageView.animate().scaleX(1.5f).scaleY(1.5f).setDuration(400);

                keep_card = true;
                card_pick = true;
                //translate(imageView, card, 400);
            }
        }
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_layout5);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        game.deck.print();
        System.out.println(game.players.get(0).getOrder());
//        for (int i = 0; i < 5; i++) {
//            System.out.print("Player " + Integer.toString(i + 1) + ": ");
//            game.players.get(i).showHand();
//            System.out.println();
//        }
    }
}
