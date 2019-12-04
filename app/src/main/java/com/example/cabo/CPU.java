package com.example.cabo;
import java.util.ArrayList;
import java.util.Arrays;

public class CPU extends Player {

    private int[][] memory;
    public int health = 100;
    public boolean turn = false;
    public int cardNum = 0;
    private int n;

    public CPU(final int order, final Cabo model, final int n) {
        super(order, model);
        this.n = n;
        this.memory = new int[n][4];
        MakeCPU();
        for (int[] row : memory)
            Arrays.fill(row, -1);
    }

    private void MakeCPU() {
        this.cpu = true;
    }

    @Override
    public int getTotal() {
        int total = 0;
        for (int num : this.memory[this.order]) {
            total += num;
        }
        return total;
    }


    @Override
    public void add_card(int value) {
        memory[this.order][cardNum] = value;
        cardNum += 1;
    }

    //
    @Override
    public void showHand() {
        int count = 0;
        for (int i = 0; i < this.memory[0].length - 1; i++) {
            System.out.print(this.memory[this.order][i] + " + ");
            count += memory[this.order][i];
        }
        System.out.print(this.memory[this.order][this.memory[0].length - 1]);
        count += this.memory[this.order][this.memory[0].length - 1];
        System.out.print(" = " + count);
    }

    @Override
    public void clearHand() {
        this.memory = new int[this.n][4];
        cardNum = 0;
        for (int[] row : memory)
            Arrays.fill(row, -1);
//        for(int i = 0; i < 4; i++){
//            System.out.print("Cards: " + this.memory[this.order][i] + " ");
//        }
    }

    //
    @Override
    public ArrayList<Integer> getHand() {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int value : memory[this.order]) {
            temp.add(value);
        }

        return temp;
    }

    @Override
    public int swap(int index, int value){
        int temp = this.memory[this.order][index];
        this.memory[this.order][index] = value;
        return temp;
    }

    @Override
    public int findLargest() {
        int large = -1;
        int index = 0;
        int count = 0;
        for (int num: this.memory[this.order]){
            if (num > large) {
                large = num;
                index = count;
            }
            count += 1;
        }
        return index;
    }


    // First thing is whether to call cabo
    //Call if less than 5 or 6 (low values)
    // If one of it's cards can be matched (compare cards with discardCard)
    // If discardCard is 3 or lower, take it and swap with highest value
    // Pick a card from deck
    // Use power if can
    // 7, 8 - Look random card
    // 9, 10 -
    // Less than or equal to 3, keep
    // Otherwise discard


//    public static void main(String[] args) {
//		Cabo temp = new Cabo(1,5);
//		cpu test = new cpu(1, temp, 5);
//
//    }


}