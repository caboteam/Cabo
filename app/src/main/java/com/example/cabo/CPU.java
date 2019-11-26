package com.example.cabo;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;

public class CPU extends Player implements Runnable{

    private int[][] memory;
    private ArrayList<Integer> hand = new ArrayList<>();
    public int health = 100;
    public boolean turn = false;
    public boolean cpu;

    public CPU(final int order, final Cabo model, final int n) {
        super(order, model);
        this.cpu = true;
        this.memory = new int[n][4];

        for (int[] row: memory)
            Arrays.fill(row, -1);
    }


    private class TurnListener implements PropertyChangeListener {

        private Player n;

        public TurnListener(Player n) {
            this.n = n;
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            int propertyName = (int)evt.getNewValue();
            System.out.println(propertyName);
            if (propertyName == n.getOrder())
                this.n.turn = true;
            else
                this.n.turn = false;
        }

    }


    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

    public static void main(String[] args) {
//		Cabo temp = new Cabo(1,5);
//		cpu test = new cpu(1, temp, 5);

    }


}
