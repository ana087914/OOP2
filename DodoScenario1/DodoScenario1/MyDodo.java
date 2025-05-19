import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;

    public MyDodo() {
        super(EAST);
        myNrOfEggsHatched = 0;
    }

    public void act() {
        // Lasă-l gol pentru moment
    }

    public void move() {
        if (canMove()) {
            step();
        } else {
            showError("I'm stuck!");
        }
    }

    public boolean canMove() {
        if (borderAhead()) {
            return false;
        }
        if (fenceAhead()) {
            return false;
        }
        return true;
    }

    public void hatchEgg() {
        if (onEgg()) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError("There was no egg in this cell");
        }
    }

    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
    }

    public void jump(int distance) {
        int pasi = 0;
        while (pasi < distance) {
            move();
            pasi++;
            System.out.println("moved " + pasi);
        }
    }
         public boolean canLayEgg(){
        if(onEgg()) {
            return false;
        }else{
            return true;
        }
    }
    public void turn180(){
        turnRight();
        turnRight();
    }

    public void climbOverFence() {
        turnLeft();
        move();
        turnRight();
        move();
        turnRight();
        move();
        turnLeft();
    }

    public boolean grainAhead() {
        move();
        boolean esteGrau = onGrain();
        turn180();
        move();
        turn180();
        return esteGrau;
    }

    }

   
