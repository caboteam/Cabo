package com.example.cabo;

import java.util.ArrayList;

public class Cabo {
    private final int[] SPECIAL = {7,8,9,10,11,12,13};
    private final int[] PEEK = {7,8};
    private final int[] SPY = {9,10};
    private final int[] SWAP = {11,12};
    private final int[] STEAL = {13};
    private boolean PLAY = true;

    public Deck deck = new Deck();
    public ArrayList<Player> players = new ArrayList<>();
    public  Counter turn;

    public Cabo(int n) {
        for (int i = 0; i < n; i++) {
            Player p = new Player(i);
            players.add(p);
        }
        turn = new Counter(n);
        this.deck.deal(players);
    }

    public int Turns(){
        if (this.turn.count() > this.players.size())
            this.turn.reset();
        return this.turn.getCount();

    }

    private boolean gameOn(){
        return this.PLAY;
    }



//    public static void main (String[] args) {
//        Cabo game = new Cabo(4);
//        game.deck.print();
//        for (int i = 0; i < 4; i++) {
//            System.out.print("Player " + Integer.toString(i + 1) + ": ");
//            game.players.get(i).showHand();
//            System.out.println();
//        }
//    }
}
