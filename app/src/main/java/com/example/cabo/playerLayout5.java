package com.example.cabo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
//import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
//import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
//import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class playerLayout5 extends AppCompatActivity {
    //BOO
    Cabo game = new Cabo(5, 0);
    int order = 0;
    boolean pickCard, pickCard_discard, keep_card, card_pick, powerCard, choose_second, keep_13, swap_active, cabo_called = false, match_card = true;
    int discardCard, selected_card, other_card, other_player, other_card_index, card_number = 0;
    ImageView card, card2;
    ArrayList<Integer> no_dim = new ArrayList<>();
    //ArrayList<Integer> cards_to_match = new ArrayList<>();


    public synchronized void CPUController() {
        if (game.players.get(this.order).getTotal() <= 6) {
            View temp = findViewById(R.id.cabo_button_out);
            temp.performClick();
        } else if (this.discardCard <= 3){
            int index = game.players.get(this.order).findLargest();
            View temp = findViewById(R.id.discard_pile);
            temp.performClick();
            ImageView card = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString((this.order * (game.n - 1)) + index));
            card.performClick();

        } else{
            View temp = findViewById(R.id.deck_dummy);
            temp.performClick();
            if (selected_card <= 3) {
                View temp2 = findViewById(R.id.keep_button_out);
                temp2.performClick();
                int index = game.players.get(this.order).findLargest();
                ImageView card = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString((this.order * (game.n - 1)) + index));
                card.performClick();
            } else if (game.contains(Deck.POWER,selected_card)) {
                View temp2 = findViewById(R.id.power_button_out);
                temp2.performClick();
                if (selected_card == 7 || selected_card == 8) {
                    Random choose = new Random(game.players.get(this.order).length());
                    ImageView card = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString((this.order * (game.n - 1)) + choose.nextInt()));
                    card.performClick();

                } else if (selected_card == 9 || selected_card == 10) {
                    Random player = new Random(game.n);
                    Random index = new Random(4);

                    int numPlayer = player.nextInt();
                    while (numPlayer == this.order) {
                        numPlayer = player.nextInt();
                    }

                    ImageView card = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString((numPlayer * (game.n - 1)) + index.nextInt()));
                    card.performClick();
                } else if (selected_card == 11 || selected_card == 12) {
                    Random player = new Random(game.n);
                    Random index = new Random(4);

                    int numPlayer = player.nextInt();
                    while (numPlayer == this.order) {
                        numPlayer = player.nextInt();
                    }

                    ImageView card = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString((numPlayer * (game.n - 1)) + index.nextInt()));
                    card.performClick();


                    int i = game.players.get(this.order).findLargest();
                    ImageView our_card = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString((this.order * (game.n - 1)) + i));
                    our_card.performClick();

                } else {
                    Random player = new Random(game.n);
                    Random index = new Random(4);

                    int numPlayer = player.nextInt();
                    while (numPlayer == this.order) {
                        numPlayer = player.nextInt();
                    }

                    ImageView card = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString((numPlayer * (game.n - 1)) + index.nextInt()));
                    card.performClick();

                    int large = game.players.get(this.order).findLargest();

                    if (selected_card > game.players.get(this.order).getValue(large)) {
                        ImageView temp3 = findViewById(R.id.discard_button_out);
                        temp3.performClick();
                    }
                    else {
                        ImageView temp3 = findViewById(R.id.keep_button_out);
                        temp3.performClick();
                        ImageView our_card = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString((this.order * (game.n - 1)) + large));
                        our_card.performClick();
                    }


                }
            } else {
                View temp2 = findViewById(R.id.discard_button_out);
                temp2.performClick();
            }

        }
    }

    public void test() {
        game.deck.print();
        for (int i = 0; i < 5; i++) {
            if (game.players.get(i).cpu)
                System.out.print("CPU " + (i + 1) + ": ");
            else
                System.out.print("Player " + (i + 1) + ": ");
            game.players.get(i).showHand();
            System.out.println();
        }
        System.out.println("Discarded: " + discardCard);
        System.out.println("turn: " + game.turn.getCount());
        System.out.println("Order: " + order);
        System.out.println("Number of players: " + game.players.size());
    }

    public void endRound() {
        int totals[] = new int[game.n];
        int min_index = 0;
        int cabo_index = 0;
        for(int i = 0; i < game.n; i++){
            totals[i] = game.players.get(i).getTotal();
            if(totals[i] < totals[min_index])
                min_index = i;
            if(game.players.get(i).cabo)
                cabo_index = i;
//            System.out.print(totals[i] +  " ");
        }
//        System.out.println();
        if(totals[min_index] == totals[cabo_index]){
            TextView h_text = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString(cabo_index + 30));
            ImageView h_bar = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString(cabo_index + 40));
            //ClipDrawable mImageDrawable = (ClipDrawable) h_bar.getDrawable();
            //mImageDrawable.setLevel(5000);
            h_text.setText(Integer.toString(game.players.get(cabo_index).health + 10) + "%");
            game.players.get(cabo_index).health += 10;
        } else {
            TextView h_text = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString(cabo_index + 30));
            ImageView h_bar = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString(cabo_index + 40));
            //ClipDrawable mImageDrawable = (ClipDrawable) h_bar.getDrawable();
            //mImageDrawable.setLevel(5000);
            h_text.setText(Integer.toString(game.players.get(cabo_index).health - totals[cabo_index] - 10) + "%");
            game.players.get(cabo_index).health = game.players.get(cabo_index).health - totals[cabo_index] - 10;
        }
//        System.out.println(game.players.get(cabo_index).health);
        for(int i = 30; i < 35 ; i++) {
            if(i != cabo_index+30 && i != min_index+30){
                TextView h_text = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString(i));
                ImageView h_bar = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString(i + 10));
                h_text.setText(Integer.toString(game.players.get(i - 30).health - totals[i - 30]) + "%");
                game.players.get(i - 30).health -= totals[i-30];
//                System.out.println("Health: " + game.players.get(i-30).health);
            }
        }
    }

    public synchronized int getCard(int n) {
        Resources res = getResources();
        return res.getIdentifier("c" + n, "drawable", getPackageName());
    }

    private synchronized void dimCards(ArrayList<Integer> no_dim) {
        for(int i = 0; i < 24; i++) {
            if(!no_dim.contains(i)){
                ImageView card = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString(i));
                card.setColorFilter(Color.argb(150, 0, 0, 0));
            }
        }
    }

    private synchronized void brightenCards() {
        for(int i = 0; i < 24; i++){
            ImageView card = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString(i));
            card.setColorFilter(Color.argb(0, 0, 0, 0));
        }
    }

    public void highlightCurrentPlayer(int n) {
        no_dim.clear();
        no_dim.add(order*4);
        no_dim.add(1 + order*4);
        no_dim.add(2 + order*4);
        no_dim.add(3 + order*4);
        for(int i = 20; i < 24; i++) {
            no_dim.add(i);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dimCards(no_dim);
            }
        }, n);
    }

    private synchronized void translate(View viewToMove, View target, long n) {
        viewToMove.animate()
                .x(target.getX())
                .y(target.getY())
                .setDuration(n)
                .start();
    }

    private synchronized void flip(final ImageView imageView, final int change, final int scale, final boolean center) {
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(imageView, "scaleX", 0f, 1f);

        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                super.onAnimationEnd(animation);
                imageView.setImageResource(change);
                if (center){
                    DisplayMetrics metrics = getResources().getDisplayMetrics();
                    ObjectAnimator translationX = ObjectAnimator.ofFloat(imageView, "x", metrics.widthPixels / 2 - imageView.getWidth() / 2);
                    translationX.setDuration(400);
                    translationX.start();
                    imageView.bringToFront();
                }
                imageView.animate().scaleX(scale).scaleY(scale).setDuration(400);
                oa2.start();
                oa2.setDuration(100);
            }
        });
        oa1.start();
        oa1.setDuration(100);
    }

    private synchronized void disableAllButtons() {
        ImageView button1 = findViewById(R.id.discard_button_in);
        ImageView button2 = findViewById(R.id.power_button_in);
        ImageView button3 = findViewById(R.id.keep_button_in);
        ImageView button4 = findViewById(R.id.cabo_button_in);

        button1.setVisibility(button1.INVISIBLE);
        button2.setVisibility(button2.INVISIBLE);
        button3.setVisibility(button3.INVISIBLE);
        button4.setVisibility(button4.INVISIBLE);
    }

    private synchronized void enableAllButtons() {
        ImageView button1 = findViewById(R.id.discard_button_in);
        ImageView button2 = findViewById(R.id.power_button_in);
        ImageView button3 = findViewById(R.id.keep_button_in);
        ImageView button4 = findViewById(R.id.cabo_button_in);

        button1.setVisibility(button1.VISIBLE);
        button2.setVisibility(button2.VISIBLE);
        button3.setVisibility(button3.VISIBLE);
        button4.setVisibility(button4.VISIBLE);
    }

    public synchronized void pickCardDeck(View view) {
        if (game.turn.getCount() == order && !pickCard_discard && !pickCard){
            selected_card = game.deck.drawCard();
            brightenCards();
            //System.out.println(selected_card);
            //game.deck.print();
            int change = getCard(selected_card);
            pickCard = true;
            ImageView button = findViewById(R.id.cabo_button_in);
            button.setVisibility(button.INVISIBLE);

            final ImageView imageView = findViewById(R.id.deck_dummy);
            //imageView.setVisibility(imageView.VISIBLE);
            flip(imageView, change, 3, true);
            match_card = false;
        }
    }

    public synchronized void  pickDiscardCard(View view) {
        if (game.turn.getCount() == order && !pickCard) {
            //System.out.println("DISCARD PILE CARD");
            brightenCards();
            pickCard_discard = true;
            keep_card = true;
            selected_card = discardCard;

            disableAllButtons();

            final ImageView imageView = findViewById(R.id.discard_dummy);

            imageView.animate().x(100).y(100).setDuration(400).start();
            imageView.animate().scaleX(1.5f).scaleY(1.5f).setDuration(400);

            no_dim.clear();

            no_dim.add(order*4);
            no_dim.add(1 + order*4);
            no_dim.add(2 + order*4);
            no_dim.add(3 + order*4);

            dimCards(no_dim);

            match_card = false;
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

    public synchronized void selectCard(View view) {
        card = (ImageView) view;
        card_number = Integer.parseInt(card.getTag().toString());

        if (game.turn.getCount() == order) {

//            if (card_number / 4 == order  && match_card) {
//                if(cards_to_match.contains(card_number)){
//                    card.setColorFilter(Color.argb(0, 0, 0, 0));
//                    cards_to_match.remove(new Integer(card_number));
//                } else {
//                    RelativeLayout stack = findViewById(R.id.stack_layout);
//                    stack.setVisibility(stack.VISIBLE);
//                    cards_to_match.add(card_number);
//                    card.setColorFilter(Color.argb(150, 128, 0, 128));
//                }
//                for(int i : cards_to_match){
//                    System.out.print(i + " ");
//                } System.out.println();
//            }
            if (keep_card && (card_number / 4) == order) {
                final int card_change, final_card, n;

                discardCard = game.players.get(order).getHand().get(card_number % 4);
                game.players.get(order).swap(card_number % 4, selected_card);

                final ImageView back;
                if (card_pick) {
                    n = 0;
                    final_card = R.drawable.card_back;
                    card_change = R.id.deck_dummy;
                    back = findViewById(R.id.deck);
                } else {
                    n = 400;
                    final_card = getCard(discardCard);
                    card_change = R.id.discard_dummy;
                    back = findViewById(R.id.discard_pile);
                    pickCard_discard = false;
                }

                final ImageView imageView = findViewById(card_change);

                translate(imageView, card, 1200);
                imageView.animate().scaleX(1).scaleY(1).setDuration(400);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(final_card);
                        translate(imageView, back, n);
                    }
                }, 1500);

                if (card_pick) {
                    final ImageView dummy = findViewById(R.id.discard_dummy);
                    final ImageView back2 = findViewById(R.id.discard_pile);
                    //int change = R.drawable.c9;

                    dummy.setImageResource(R.drawable.card_back);
                    translate(dummy, card, 0);
                    flip(dummy, getCard(discardCard), 1, false);

                    Handler handler2 = new Handler();
                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            translate(dummy, back2, 400);
                            //imageView.setImageResource(final_card);
                        }
                    }, 1500);
                }

                brightenCards();
                game.Turns();
                order = (order + 1) % 5;

                card_pick = false;
                keep_card = false;
                match_card = true;
                disableAllButtons();

                if(game.players.get(game.turn.getCount()).cabo)
                    endRound();

                if(order == game.turn.getCount()) {
                    highlightCurrentPlayer(2500);
                    enableAllButtons();
                    if (game.players.get(order).cpu == true) {
                        CPUController();
                    }
                }
                test();
            } else if(powerCard && !game.players.get(card_number / 4).cabo) {
                System.out.println("CABO? " + game.players.get(card_number / 4).getOrder() + " " + game.players.get(card_number / 4).cabo);
                if (selected_card == 7 || selected_card == 8) {
                    if(card_number / 4 == order){
                        int n = game.players.get(order).getHand().get(card_number % 4);
                        //System.out.println("Card: " + n);
                        int change = getCard(n);
                        flip(card, change, 1, false);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                flip(card, R.drawable.card_back, 1, false);
                            }
                        }, 2500);
                        game.Turns();
                        order = (order + 1) % 5;
                        if(game.players.get(game.turn.getCount()).cabo)
                            endRound();
                        test();
                        powerCard = false;
                        match_card = true;
                        brightenCards();
                        pickCard = false;
                        if(order == game.turn.getCount()) {
                            highlightCurrentPlayer(2500);
                            enableAllButtons();
                            if (game.players.get(order).cpu == true) {
                                CPUController();
                            }
                        }
                    }
                } else if(selected_card == 9 || selected_card == 10) {
                    if(card_number / 4 != order) {
                        int n = game.players.get((card_number/4)).getHand().get(card_number % 4);
                        //System.out.println("Card: " + n);
                        int change = getCard(n);
                        flip(card, change, 1, false);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                flip(card, R.drawable.card_back, 1, false);
                            }
                        }, 2500);
                        game.Turns();
                        order = (order + 1) % 5;

                        if(game.players.get(game.turn.getCount()).cabo)
                            endRound();

                        test();
                        match_card = true;
                        powerCard = false;
                        pickCard = false;
                        brightenCards();
                        if(order == game.turn.getCount()) {
                            highlightCurrentPlayer(2500);
                            enableAllButtons();
                            if (game.players.get(order).cpu == true) {
                                CPUController();
                            }
                        }
                    }
                } else if((selected_card == 11 || selected_card == 12 || selected_card == 13) && card_number / 4 != order && !choose_second && !swap_active) {
                    card2 = card;
                    other_player = card_number / 4;
                    other_card = game.players.get((card_number / 4)).getHand().get(card_number % 4);
                    other_card_index = card_number % 4;
                    choose_second = true;
                    //System.out.println(other_card);
                    no_dim.clear();
                    no_dim.add(card_number);
                    dimCards(no_dim);
                    for(int i = order*4; i < order*4 + 4; i++){
                        ImageView temp = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString(i));
                        temp.setColorFilter(Color.argb(0, 0, 0, 0));
                    }
                    if(selected_card == 13) {
                        ImageView button1 = findViewById(R.id.keep_button_in);
                        ImageView button2 = findViewById(R.id.discard_button_in);

                        button1.setVisibility(button1.VISIBLE);
                        button2.setVisibility(button2.VISIBLE);

                        int n = game.players.get(card_number / 4).getHand().get(card_number % 4);
                        flip(card, getCard(n), 1, false);

                        choose_second = false;
                        swap_active = true;
                    }
                } else if((card_number / 4 == order && choose_second) || (card_number / 4 == order && keep_13)) {
                    final ImageView dummy1 = findViewById(R.id.deck_dummy);
                    final ImageView dummy2 = findViewById(R.id.discard_dummy);

                    int first_card = game.players.get(order).getHand().get(card_number % 4);
                    game.players.get(order).swap(card_number % 4, other_card);
                    game.players.get(other_player).swap(other_card_index, first_card);

                    dummy2.setImageResource(R.drawable.card_back);
                    //System.out.println(4 * (other_player) + other_card_index);
                    no_dim.clear();
                    no_dim.add(card_number);
                    no_dim.add(4 * (other_player) + other_card_index);
                    dimCards(no_dim);

                    if(keep_13)
                        flip(card2, R.drawable.card_back, 1, false);

                    dummy1.setColorFilter(Color.argb(0, 0, 0, 0));
                    dummy2.setColorFilter(Color.argb(0, 0, 0, 0));

                    //System.out.println("GOES HERE " + first_card);

                    translate(dummy1, card, 0);
                    translate(dummy2, card2, 0);

                    card.setVisibility(card.INVISIBLE);
                    card2.setVisibility(card2.INVISIBLE);

                    translate(dummy1, card2, 2000);
                    translate(dummy2, card, 2000);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            card.setVisibility(card.VISIBLE);
                            card2.setVisibility(card2.VISIBLE);
                            translate(dummy1, findViewById(R.id.deck), 0);
                            translate(dummy2, findViewById(R.id.discard_pile), 0);
                            dummy2.setImageResource(getCard(discardCard));
                            brightenCards();
                        }
                    }, 3500);

                    keep_13 = false;
                    game.Turns();
                    order = (order + 1) % 5;
                    if(game.players.get(game.turn.getCount()).cabo)
                        endRound();
                    match_card = true;
                    choose_second = false;
                    powerCard = false;
                    pickCard = false;
                    if(order == game.turn.getCount()) {
                        highlightCurrentPlayer(3500);

                        enableAllButtons();
                        if (game.players.get(order).cpu == true) {
                            CPUController();
                        }
                    }
                    test();
                }
            }
        }
    }

    public synchronized void buttonClick(View view) {
        if (game.turn.getCount() == order) {
//            if(match_card) {
//                while(cards_to_match.isEmpty()) {
//                    if(!game.players.get(order).getHand().contains(cards_to_match.get(0)))
//                        break;
//                }
//                if(cards_to_match.isEmpty()){
//                    System.out.println("MATCH CARDS SUCCESS!!");
//                } else {
//                    System.out.println("MATCH CARDS FAILED!!");
//                }
//            }
            if(!pickCard && !pickCard_discard && view.getId() == R.id.cabo_button_out && !cabo_called) {
                final TextView textView = findViewById(R.id.call_cabo);
                textView.animate().alpha(100).setDuration(6000);
                disableAllButtons();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.animate().alpha(0).setDuration(1000);
                    }
                }, 1000);
                System.out.println("Cabo Called!");

                ArrayList<Integer> curHand = game.players.get(order).getHand();

                for(int i = order*4; i < order*4 + 4; i++){
                    ImageView card = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString(i));
                    flip(card, getCard(curHand.get(i - order*4)), 1, false);
                }
                game.players.get(order).cabo = true;
                game.Turns();
                order = (order + 1) % 5;
                match_card = true;
                pickCard = false;
                cabo_called = true;
                //System.out.println("CABO: " + game.players.get(order).getOrder() + " " + game.players.get(order).cabo);
                brightenCards();
                if(order == game.turn.getCount()) {
                    highlightCurrentPlayer(2500);

                    enableAllButtons();
                    if (game.players.get(order).cpu == true) {
                        CPUController();
                    }
                }
                test();
            } else if(pickCard && !pickCard_discard && view.getId() == R.id.discard_button_out){
                System.out.println("Card discarded!");
                if (powerCard && swap_active){
                    flip(card2, R.drawable.card_back, 1, false);
                    brightenCards();
                    keep_13 = false;
                    powerCard = false;
                    swap_active = false;
                } else if(!powerCard) {
                    final ImageView discard = findViewById(R.id.discard_pile);
                    final ImageView dummy = findViewById(R.id.discard_dummy);
                    final ImageView imageView = findViewById(R.id.deck_dummy);
                    final ImageView back = findViewById(R.id.deck);

                    translate(imageView, discard, 400);
                    imageView.animate().scaleX(1).scaleY(1).setDuration(400);
                    discard.setImageResource(getCard(selected_card));
                    dummy.setImageResource(getCard(selected_card));

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            translate(imageView, back, 0);
                            imageView.setImageResource(R.drawable.card_back);
                        }
                    }, 600);
                } else return;
                game.Turns();
                order = (order + 1) % 5;
                if(game.players.get(game.turn.getCount()).cabo)
                    endRound();
                match_card = true;
                discardCard = selected_card;
                pickCard = false;
                if(order == game.turn.getCount()) {
                    highlightCurrentPlayer(750);

                    enableAllButtons();
                    if (game.players.get(order).cpu == true) {
                        CPUController();
                    }
                }
                test();
            } else if(pickCard && !pickCard_discard && view.getId() == R.id.power_button_out && selected_card >= 7 && !powerCard) {
                System.out.println("Card power used!");

                disableAllButtons();
                powerCard = true;
                no_dim.clear();

                if(selected_card == 7 || selected_card == 8){
                    no_dim.add(order*4);
                    no_dim.add(1 + order*4);
                    no_dim.add(2 + order*4);
                    no_dim.add(3 + order*4);
                    dimCards(no_dim);
                } else if (selected_card >= 9) {
                    for(int i = 20; i < 24; i++) {
                        ImageView card = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString(i));
                        card.setColorFilter(Color.argb(150, 0, 0, 0));
                    }
                    for(int i = order*4; i < order*4 + 4; i++) {
                        ImageView card = findViewById(R.id.activity_detailed_view).findViewWithTag(Integer.toString(i));
                        card.setColorFilter(Color.argb(150, 0, 0, 0));
                    }
                }

                final ImageView discard = findViewById(R.id.discard_pile);
                final ImageView discard_dummy = findViewById(R.id.discard_dummy);
                final ImageView imageView = findViewById(R.id.deck_dummy);
                final ImageView back = findViewById(R.id.deck);

                discardCard = selected_card;
                int change = getCard(discardCard);

                translate(imageView, discard, 400);
                imageView.animate().scaleX(1).scaleY(1).setDuration(400);
                discard.setImageResource(change);
                discard_dummy.setImageResource(change);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        translate(imageView, back, 0);
                        imageView.setImageResource(R.drawable.card_back);
                    }
                }, 500);
            } else if(pickCard && !pickCard_discard && view.getId() == R.id.keep_button_out) {
                if (powerCard && swap_active) {
                    System.out.println("Swapping Card!!");
                    keep_13 = true;
                    disableAllButtons();
                    pickCard = false;
                    swap_active = false;
                } else if(!powerCard){
                    System.out.println("Card kept!");
                    no_dim.clear();

                    no_dim.add(order*4);
                    no_dim.add(1 + order*4);
                    no_dim.add(2 + order*4);
                    no_dim.add(3 + order*4);

                    dimCards(no_dim);

                    final ImageView imageView = findViewById(R.id.deck_dummy);

                    imageView.animate().x(100).y(100).setDuration(400).start();
                    imageView.animate().scaleX(1.5f).scaleY(1.5f).setDuration(400);
                    keep_card = true;
                    card_pick = true;
                    disableAllButtons();
                    pickCard = false;
                }
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

        //game.deck.print();
        //System.out.println(game.players.get(0).getOrder());

        discardCard = game.deck.drawCard();
        int change = getCard(discardCard);

        final ImageView discard = findViewById(R.id.discard_pile);
        final ImageView discard_dummy = findViewById(R.id.discard_dummy);

        discard.setImageResource(change);
        discard_dummy.setImageResource(change);

        ArrayList<Integer> curHand = game.players.get(order).getHand();

        final int card1_img = getCard(curHand.get(0));
        final int card2_img = getCard(curHand.get(1));

        final ImageView card1 = findViewById(R.id.player1_1);
        final ImageView card2 = findViewById(R.id.player1_2);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flip(card1, card1_img, 1, false);
                flip(card2, card2_img, 1, false);
            }
        }, 1500);

        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                flip(card1, R.drawable.card_back, 1, false);
                flip(card2, R.drawable.card_back, 1, false);
            }
        }, 6000);
        highlightCurrentPlayer(2500);

        test();
    }
}