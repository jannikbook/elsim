package main.java.elsim.models;

import java.util.Scanner;

/**
 * An Item is a object that can be transported by a Passenger of an elevator.
 * This Class defines the common attributes like mass and required space
 * that are relevant for the simulation.
 * @see Load
 * @author ptomalak
 */
public class Item {
    static int MIN_MASS                 = 1;
    static int MAX_MASS                 = 20;
    static int MIN_WIDTH                = 10;
    static int MAX_WIDTH                = 50;
    static int MIN_HEIGHT               = 10;
    static int MAX_HEIGHT               = 50;
    static int MAX_ITEM_NUMBER          = 10;

    int mass;
    Rectangle spaceRequired;

    //Create a new Item
    public static Item SpawnItem(){
        int width = getRandomInteger(MIN_WIDTH,MAX_WIDTH);
        int height = getRandomInteger(MIN_HEIGHT,MAX_HEIGHT);
        Item myItem = new Item();
        myItem.mass = getRandomInteger(MIN_MASS,MAX_MASS);
        myItem.spaceRequired = new Rectangle(0,0);
        return myItem;
    }
    //Generate random integer in a given range
    public static int getRandomInteger(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    //Show attributes of newly created items
    public static void printItemInfo (int itemNumber){
        Item myItem = SpawnItem();
        System.out.println("Item #" + itemNumber + ":");
        System.out.println("Mass : " + myItem.mass + "kg");
        System.out.println("Required space : " + myItem.spaceRequired.getWidth() + "x" + myItem.spaceRequired.getHeight() + " cm");

    }
    //Testfunction that spawns new Items and shows their attributes
    public static void main (String[] args){
        Scanner in = new Scanner(System.in);
        int option = 0;
        int itemCount;
        int i;

        //repeat until "Exit" is chosen
        while (option != 2){
            System.out.println("What do you want to do?");
            System.out.println("(1) Create Items");
            System.out.println("(2) Exit");
            option = in.nextInt();
            if (option == 1){
                System.out.println("How many Items do you want to be created?");
                itemCount = in.nextInt();
                for (i = 0; i < itemCount; i++) {
                    printItemInfo(i+1);
                }
            }
        }
    }
}
