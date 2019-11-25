package com.example.cabo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;



public class Cabo {
    private final int[] SPECIAL = { 7, 8, 9, 10, 11, 12, 13 };
    private final int[] PEEK = { 7, 8 };
    private final int[] SPY = { 9, 10 };
    private final int[] SWAP = { 11, 12 };
    private final int[] STEAL = { 13 };
    private boolean PLAY = true;

    public static final String TURN_CHANGE = "Turn Changed";
    public static final String NO_CARDS = "No Cards";

    public Deck deck = new Deck(this);
    public ArrayList<Player> players = new ArrayList<>();
    public Counter turn;
    public int n;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(propertyName, listener);
    }

    public Cabo(int n, int cpu) {
        this.n = n + cpu;
        this.turn = new Counter(n);
        for (int i = 0; i < n; i++) {
            Player p = new Player(i, this);
            players.add(p);
        }

        for (int i = n; i < n + cpu; i++) {
            CPU c = new CPU(i, this, this.n);
            c.run();
            players.add(c);
        }

        this.deck.deal(players);
    }

    public int Turns() {
        if(this.turn.count() == n){
            this.turn.reset();
        }
        this.pcs.firePropertyChange(Cabo.TURN_CHANGE, null, this.turn.getCount());
        return this.turn.getCount();

    }

    private boolean gameOn() {
        return this.PLAY;
    }

    public static void main(String[] args) {
        int n = 1;
        int cpu = 4;
        Cabo game = new Cabo(n, cpu);
        game.deck.print();


        for (int j = 0; j < 10; j++) {
            System.out.println("Turns: " + game.Turns());
            for (int i = 0; i < n + cpu; i++) {
                System.out.println("Player " + i + " "+ game.players.get(i).turn);
            }
            System.out.println();
        }
    }

}
