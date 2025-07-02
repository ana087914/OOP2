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

    /**
     * Constructor: stelt start-richting en aantal eieren op nul.
     */
    public MyDodo() {
        super(EAST);
        myNrOfEggsHatched = 0;
    }

    public void act() {}

    /**
     * Beweegt 1 stap vooruit als mogelijk, en telt stappen.
     */
    public void move() {
        if (canMove()) {
            step();
            myNrOfStepsTaken++; 
        } else {
            showError("I'm stuck!");
        }
    }

    /**
     * Controleert of de dodo vooruit kan bewegen.
     */
    public boolean canMove() {
        if (borderAhead() || fenceAhead()) {
            return false;
        }
        return true;
    }

    public boolean frontIsClear() {
        return canMove();
    }

    /**
     * Laat een ei uitkomen als de dodo erop staat.
     */
    public void hatchEgg() {
        if (onEgg()) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError("There was no egg in this cell");
        }
    }

    /**
     * Geeft het aantal uitgebroede eieren terug.
     */
    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
    }

    /**
     * Loopt meerdere stappen vooruit (tot aan afstand).
     */
    public void jump(int distance) {
        int pasi = 0;
        while (pasi < distance) {
            move();
            pasi++;
            System.out.println("moved " + pasi);
        }
    }

    /**
     * Controleert of er nog geen ei ligt op huidige locatie.
     */
    public boolean canLayEgg(){
        return !onEgg();
    }

    /**
     * Draait 180 graden.
     */
    public void turn180(){
        turnRight();
        turnRight();
    }

    /**
     * Klimt over een hek heen in een omweg.
     */
    public void climbOverFence() {
        turnLeft();
        move();
        turnRight();
        move();
        turnRight();
        move();
        turnLeft();
    }

    /**
     * Kijkt of er graan ligt voor de dodo.
     */
    public boolean grainAhead() {
        move();
        boolean esteGrau = onGrain();
        turn180();
        move();
        turn180();
        return esteGrau;
    }

    /**
     * Loopt recht vooruit totdat er een ei is.
     */
    public void gotoEgg() {
        while (!onEgg()) {
            move();
        }
    }

    /**
     * Loopt tot de rand van de wereld.
     */
    public void walkToWorldEdge() {
        while (frontIsClear()) {
            move();
        }
    }

    /**
     * Loopt naar begin van de rij en draait om.
     */
    public void goBackToStartOfRowAndFaceBack() {
        turn180();
        walkToWorldEdge();
        turn180();
    }

    /**
     * Loopt naar de rand, klimt indien nodig over hekken.
     */
    public void walkToWorldEdgeClimbingOverFences() {
        while (frontIsClear()) {
            if (fenceAhead()) {
                climbOverFence();
            } else {
                move();
            }
        }
    }

    /**
     * Pakt graan op en toont de coördinaten.
     */
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

    /**
     * Zet één stap achteruit.
     */
    public void stepOneCellBackwards() {
        turn180();
        move();
        turn180();
    }

    /**
     * Legt alleen eieren waar nog geen eieren liggen.
     */
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

    /**
     * Gaat naar het nest, ontwijkt onderweg hekken.
     */
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

    /**
     * Loopt rond een hek tot het nest bereikt is.
     */
    public void walkAroundFencedArea() {
        while (!onNest()) {
            if (leftIsClear()) {
                turnLeft();
                move();
            }  else if (frontIsClear()) {
                move();
            } else {
                turnRight();
            }
            if (nestAhead()) {
                move();
            }
        }
    }

    /**
     * Controleert of links vrij is.
     */
    public boolean leftIsClear() {
        turnLeft();
        boolean clear = frontIsClear();
        turnRight();
        return clear;
    }

    /**
     * Draait 90 graden naar rechts.
     */
    public void turnRight() {
        turnLeft();
        turnLeft();
        turnLeft();
    }

    /**
     * Checkt of er een nest op deze plek is.
     */
    public boolean nestPresent() {
        return getOneObjectAtOffset(0, 0, Nest.class) != null;
    }

    /**
     * Draait richting oost.
     */
    public void faceEast() {
        while (getDirection() != EAST) {
            turnLeft();
        }
    }

    /**
     * Checkt of een bepaalde locatie is bereikt.
     */
    public boolean locationReached(int x, int y) {
        return getX() == x && getY() == y;
    }

    /**
     * Gaat naar een specifieke locatie in het grid.
     */
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

    /**
     * Controleert of coördinaten geldig zijn binnen de wereld.
     */
    public boolean validCoordinates(int x, int y) {
        if (x >= 0 && x < getWorld().getWidth() && y >= 0 && y < getWorld().getHeight()) {
            return true;
        } else {
            showError("Invalid coordinates");
            return false;
        }
    }

    /**
     * Telt eieren op een rij.
     */
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

    /**
     * Volgt een spoor van eieren tot het nest is bereikt.
     */
    public void eggTrailToNest() {
        while (!nestPresent()) {
            if  (eggAhead()) {
                move();
                pickUpEgg();
            } else {
                while (!eggAhead() && !nestAhead()) {
                    turnRight();
                } 
            }
            if (nestAhead()) {
                move();
                showCompliment("Nest gevonden!");
            }
        }
    }

    /**
     * Legt een pad van eieren in een rechte lijn.
     */
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

    /**
     * Beweegt omlaag naar de volgende rij.
     */
    public void moveDown() {
        setDirection(SOUTH);
        move();
        setDirection(EAST);
    }

    /**
     * Telt alle eieren in de hele wereld.
     */
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

    /**
     * Bepaalt welke rij de meeste eieren bevat.
     */
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

    /**
     * Toont huidige score en stappen gezet.
     */
    public void getScore(int score1, int score2) {
        updateScores(Mauritius.MAXSTEPS - myNrOfStepsTaken, myScore);
        System.out.println("Huidige score: " + score1 + " punten");
        System.out.println("Aantal stappen gezet: " + score2);
    }

    /**
     * Beweegt willekeurig en verzamelt eieren.
     */
    public void moveRandomly() {
        while (myNrOfStepsTaken < Mauritius.MAXSTEPS) {
            int dir = randomDirection();
            setDirection(dir);

            if (!borderAhead() && !fenceAhead()) {
                move();
                myNrOfStepsTaken++;

                if (onEgg()) {
                    Egg e = pickUpEgg();
                    myScore += e.getValue();
                    getScore(myScore, myNrOfStepsTaken);
                }
            }
        }
    }

    /**
     * Zoekt het dichtstbijzijnde ei en geeft de locatie (coördinaten) terug.
     * (Let op: geeft GEEN Egg-object terug, enkel int[] voor eenvoud in navigatie)
     */
    public Egg  findClosestEggLocation() {
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
            return closest;
        }
        return null; 
    }

    /**
     * Verzamelt slim eieren door steeds naar het dichtstbijzijnde ei te gaan.
     */
    public void smartCollectEggs() {
        while (myNrOfStepsTaken < Mauritius.MAXSTEPS) {
            Egg target = findClosestEggLocation();

            if (target == null) {
                break; 
            }

            goToLocation(target.getX(), target.getY()); 

            if (onEgg()) {
                Egg e = pickUpEgg();
                myScore += e.getValue();
                getScore(myScore, myNrOfStepsTaken);
            }
        }
    }
}
