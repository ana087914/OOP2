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
    public boolean frontIsClear() {
    return canMove();
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
    
        public void gotoEgg() {
        while (!onEgg()) {
            move();
        }
    }


    
    public void walkToWorldEdge() {
        while (frontIsClear()) {
            move();
        }
    }

    
    public void goBackToStartOfRowAndFaceBack() {
        turn180();
        walkToWorldEdge();
        turn180();
    }

    
    public void walkToWorldEdgeClimbingOverFences() {
        while (frontIsClear()) {
            if (fenceAhead()) {
                climbOverFence();
            } else {
                move();
            }
        }
    }

   
    public void pickUpGrainsAndPrintCoordinates() {
        while (frontIsClear()) {
            if (onGrain()) {
                pickUpGrain();
                System.out.println("Graan op: (" + getX() + ", " + getY() + ")");
            }
            move();
        }
        if (onGrain()) {
            pickUpGrain();
            System.out.println("Graan op: (" + getX() + ", " + getY() + ")");
        }
    }

    
    public void stepOneCellBackwards() {
        turn180();
        move();
        turn180();
    }

    
    public void geenDubbeleEieren() {
        while (frontIsClear()) {
            if (nestPresent() && !onEgg()) {
                getEgg();
            }
            move();
        }
        if (nestPresent() && !onEgg()) {
            getEgg();
        }
    }

    
    public void naarNestEnHekkenOntwijken() {
        while (frontIsClear()) {
            if (nestPresent()) {
                getEgg();
                break;
            } else if (fenceAhead()) {
                climbOverFence();
            } else {
                move();
            }
        }
    }

    
    public void walkAroundFencedArea() {
        while (!onEgg()) {
            if (leftIsClear()) {
                turnLeft();
                move();
            } else if (frontIsClear()) {
                move();
            } else {
                turnRight();
            }
        }
    }

    
    public boolean leftIsClear() {
        turnLeft();
        boolean clear = frontIsClear();
        turnRight();
        return clear;
    }

    public void turnRight() {
        turnLeft();
        turnLeft();
        turnLeft();
    }
    public boolean nestPresent() {
        return getOneObjectAtOffset(0, 0, Nest.class) != null;
    }

   public void faceEast() {
    while (getDirection() != EAST) {
        turnLeft();
    }
}
public boolean locationReached(int x, int y) {
    return getX() == x && getY() == y;
}

public void goToLocation(int coordX, int coordY) {
    while (!locationReached(coordX, coordY)) {
        if (getX() < coordX) {
            setDirection(EAST);
        } else if (getX() > coordX) {
            setDirection(WEST);
        } else if (getY() < coordY) {
            setDirection(SOUTH);
        } else if (getY() > coordY) {
            setDirection(NORTH);
        }
        move();
    }
}
   
}
    

   
