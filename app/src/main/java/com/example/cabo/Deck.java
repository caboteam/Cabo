package com.example.cabo;
import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private final static int[] VALUES = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,12,13};
    public final static int[] POWER = {7,8,9,10,11,12,13};
    public ArrayList<Integer> deck = new ArrayList<>();
    public ArrayList<Integer> discard_pile = new ArrayList<>();
    private ArrayList<Integer> inPlay = new ArrayList<>();
    private int count = 0;
    private Cabo model;

    public Deck(Cabo model) {
        for (int val: VALUES) {
            this.model = model;
            for (int i = 1; i <= 4; i++) {
                if (i > 2 && (val == 0 || val == 13))
                    continue;
                this.deck.add(val);
                count++;
            }
        }
        shuffle();
//        this.deck.set(30, 11);
//        this.deck.set(29, 12);
//        this.deck.set(28, 13);
//        this.deck.set(27, 13);
    }

    private void shuffle() {
        System.out.println("Shuffled!");
        int j, k, temp;
        Random rand = new Random();
        for (int i = 0; i < 500; i++) {
            // Obtain a number between [0 - 51]
            j = rand.nextInt(this.count);
            k = rand.nextInt(this.count);
            System.out.print(j + " " + k + " ");

            temp = this.deck.get(j);
            this.deck.set(j, this.deck.get(k));
            this.deck.set(k, temp);
        }
    }


    public void deal(ArrayList<Player> players) {
        for (int i = 0; i < 4; i++) {
            for (int p = 0; p < players.size(); p++) {
                int temp = this.deck.remove(this.count - 1);
                players.get(p).add_card(temp);
                this.inPlay.add(temp);
                this.count--;
            }
        }
    }

    public int drawCard() {
        int temp = this.deck.remove(this.count -1);
        this.count--;
        //reshuffle();
        if(this.count == 1)
            reshuffle();
        return temp;
    }

	public void reshuffle() {
		this.deck.addAll(this.discard_pile);
		this.count = this.deck.size();
		this.discard_pile.clear();
        shuffle();
	}

     public boolean isEmpty() {
        return this.count == 0;
    }


    public void print() {
        System.out.println("Cards in deck: " + Integer.toString(this.count));
        for (int card: this.deck) {
            System.out.print(card + " ");
        }
        System.out.println("\n");
    }

    public void printDeck() {
        System.out.println("Cards in discard_pile: " + (this.discard_pile.size()));
        for (int card: this.discard_pile) {
            System.out.print(card + " ");
        }
        System.out.println("\n");
    }
//	public static void main(String[] arg) {
//		// Testing for creating a shuffled deck
//		Deck temp = new Deck();
//		temp.print();
//
//
//		// Testing for card draw
//		int val;
//		for (int i = 0; i < 52; i++) {
//			val = temp.drawCard();
//			System.out.println("Card Drawn: " + Integer.toString(val));
//			System.out.println("Empty = " +  Boolean.toString(temp.isEmpty()));
//			temp.print();
//		}
//		System.out.println();
//
//
//	}

}
