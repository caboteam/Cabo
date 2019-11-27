package com.example.cabo;
import java.util.ArrayList;
import java.util.Arrays;

public class CPU extends Player {

    private int[][] memory;
    public int health = 100;
    public boolean turn = false;
    public int cardNum = 0;

    public CPU(final int order, final Cabo model, final int n) {
        super(order, model);
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
    public int length(){
        return this.cardNum;
    }


    @Override
    public int findLargest() {
        int index = 0;
        int largest = this.memory[this.order][0];
        for (int i = 1; i < this.memory[this.order].length; i++) {
            if (largest < this.memory[this.order][i]){
                index = i;
                largest = this.memory[this.order][i];
            }
        }
        return index;
    }

    @Override
    public int getValue (int index) {
        return this.memory[this.order][index];
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


    @Override
    public int swap(int index, int value){
        int temp = this.memory[this.order][index];
        this.memory[this.order][index] = value;
        return temp;
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

}