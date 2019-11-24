package com.example.cabo;

import java.util.ArrayList;

public class Player {
    private ArrayList<Integer> hand = new ArrayList<>();
    public int health = 100;
    public int order;


    public Player(int order) {
        this.order = order;
    }

    public void add_card(int value) {
        this.hand.add(value);
    }

    public int getOrder() {
        return this.order;
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
}
