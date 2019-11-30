package com.example.cabo;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class Player {
    private ArrayList<Integer> hand = new ArrayList<>();
    public int health = 100;
    public int order;
    public boolean turn = false;
    public final Cabo model;
    public boolean cpu;
    public boolean cabo = false;

    public Player(final int order, final Cabo model) {
        this.cpu = false;
        this.order = order;
        this.model = model;
        registerListener(this.order);
    }

    public int getTotal() {
        int count = 0;
        for (int i = 0; i < this.hand.size() - 1; i++) {
            count += this.hand.get(i);
        }
        count += this.hand.get(this.hand.size() - 1);
        return count;
    }

    public void add_card(int value) {
        this.hand.add(value);
    }

    public int getOrder() {
        return this.order;
    }

    public int getValue (int index) {
        return this.hand.get(index);
    }

    public int length() {
        return this.hand.size();
    }

    public void showHand() {
        int count = 0;
        for (int i = 0; i < this.hand.size() - 1; i++) {
            System.out.print(this.hand.get(i) + " + ");
            count += this.hand.get(i);
        }
        System.out.print(this.hand.get(this.hand.size() - 1));
        count += this.hand.get(this.hand.size() - 1);
        System.out.print(" = " + count);
    }

    public ArrayList<Integer> getHand() {
        return hand;
    }

    public int getCard(int i) {
        return this.hand.get(i);
    }

    public void decreaseHealth(int value) {
        this.health -= value;
    }

    public void increaseHealth() {
        this.health += 10;
    }

    public int swap(int index, int value){
        int temp = this.hand.get(index);
        this.hand.set(index, value);
        return temp;
    }


    public int findLargest() {
    return 0;
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

    public void registerListener(int order) {
        this.model.addPropertyChangeListener(Cabo.TURN_CHANGE, new TurnListener(this));
    }


}
