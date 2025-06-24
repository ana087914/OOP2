import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;
private int myScore;
private int myNrOfStepsTaken;

    public MyDodo() {
        super(EAST);
        myNrOfEggsHatched = 0;
    }

    public void act() {

    }

    public void move() {
    if (canMove()) {
        step();
        myNrOfStepsTaken++; 
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
        while (!onNest()) {
            if (leftIsClear()) {
                turnLeft();
                move();
            }  else if (frontIsClear()) {
                move();
            } else {
                turnRight();
            } if (nestAhead()) {
                move();}

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
    while (!locationReached(coordX, coordY) && myNrOfStepsTaken < Mauritius.MAXSTEPS) {
        if (getX() < coordX) {
            setDirection(EAST);
        } else if (getX() > coordX) {
            setDirection(WEST);
        } else if (getY() < coordY) {
            setDirection(SOUTH);
        } else if (getY() > coordY) {
            setDirection(NORTH);
        }

        if (myNrOfStepsTaken < Mauritius.MAXSTEPS) {
            move();
        }
    }
}


    public boolean validCoordinates(int x, int y) {
        if (x >= 0 && x < getWorld().getWidth() && y >= 0 && y < getWorld().getHeight()) {
            return true;
        } else {
            showError("Invalid coordinates");
            return false;
        }
    }

    public int countEggsInRow() {
        int count = 0;
        while (frontIsClear()) {
            if (onEgg()) {
                count++;
            }
            move();
        }
        if (onEgg()) {
            count++;
        }
        goBackToStartOfRowAndFaceBack();
        return count;
    }

    public void eggTrailToNest() {
        while (!nestPresent()) {
            if  (eggAhead()) {
                move();
                pickUpEgg();

            } else {
                while(!eggAhead()&& !nestAhead()){
                    turnRight();

                } 
            }
            if (nestAhead()) {
                move();
                showCompliment("Nest gevonden!");
            }
        }}
       public void layTrailOfEggs(int n) {
    if (n <= 0) {
        showError("Aantal eieren moet positief zijn.");
        return;
    }

    for (int i = 0; i < n; i++) {
        if (canLayEgg()) {
            layEgg();
        }
        if (i < n - 1) {
            if (canMove()) {
                move();
            } else {
                showError("Kan niet verder bewegen, obstakel of rand bereikt.");
                return;
            }
        }
    }
}
public void moveDown() {
    setDirection(SOUTH);
    move();
    setDirection(EAST);
}

public void countAllEggsInWorld() {
    int total = 0;
    int rows = getWorld().getHeight();

    goToLocation(0, 0);
    setDirection(EAST);

    for (int i = 0; i < rows; i++) {
        total += countEggsInRow();
        if (i < rows - 1) {
            moveDown();
        }
    }

    System.out.println("Eieren in de wereld: " + total);
}
public void findRowWithMostEggs() {
  
    int max = 0;
    int bestRow = getY();
    
    faceEast(); 

    for (int i = 0; i < getWorld().getHeight(); i++) {
        int count = countEggsInRow();
        if (count > max) {
            max = count;
            bestRow = getY();
        }

        if (i < getWorld().getHeight() - 1) {
            moveDown(); 
        }
    }

     System.out.println("Rij met meeste eieren: " + bestRow);
}


public void getScore(int score1, int score2) {
    updateScores(Mauritius.MAXSTEPS -myNrOfStepsTaken,myScore);
    System.out.println("Huidige score: " + score1 + " punten");
    System.out.println("Aantal stappen gezet: " + score2);
}


public void moveRandomly() {
    while (myNrOfStepsTaken < Mauritius.MAXSTEPS) {
        int dir = randomDirection();
        setDirection(dir);

        if (!borderAhead() && !fenceAhead()) {
            move();
            myNrOfStepsTaken++;

            if (onEgg()) {
                Egg e = pickUpEgg();
                myScore+=e.getValue();
                // if (e instanceof BlueEgg) {
                    // myScore += 1;
                // } else if (e instanceof GoldenEgg) {
                    // myScore += 5;
                // }
                getScore(myScore, myNrOfStepsTaken);
            }
        }
    }
}
public int[] findClosestEggLocation() {
    Egg closest = null;
    int minDistance = Integer.MAX_VALUE;

    for (Object obj : getWorld().getObjects(Egg.class)) {
        Egg egg = (Egg) obj;
        int dx = Math.abs(egg.getX() - getX());
        int dy = Math.abs(egg.getY() - getY());
        int dist = dx + dy;

        if (dist < minDistance) {
            minDistance = dist;
            closest = egg;
        }
    }

    if (closest != null) {
        return new int[]{closest.getX(), closest.getY()};
    }
    return null; 
}
public void smartCollectEggs() {
    while (myNrOfStepsTaken < Mauritius.MAXSTEPS) {
        int[] target = findClosestEggLocation();

        if (target == null) {
            break; 
        }

        goToLocation(target[0], target[1]); 
       

        if (onEgg()) {
            Egg e = pickUpEgg();
            myScore += e.getValue();
            getScore(myScore, myNrOfStepsTaken);
        }
    }
}

}





     

    



