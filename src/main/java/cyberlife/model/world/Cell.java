package cyberlife.model.world;

import cyberlife.model.life.Animal;

import java.util.ArrayList;
import java.util.Random;

public class Cell {


    private int MAX_GRASS = 250;
    private int grass;
    private int minerals;
    private Animal animal = null;

    private int x;
    private int y;

    private World world;

    Cell(int x, int y, World world){

        this.x = x;
        this.y = y;
        this.world = world;

        Random random = new Random();

        int prob = random.nextInt(100);

        if (prob>=0 && prob<=5){
            this.grass = 0;
        }
        if (prob>5 && prob<=15){
            this.grass = 250;
        }
        if (prob>15 && prob<=75){
            this.grass = 100;
        }
        if (prob>75 && prob<=100){
            this.grass = 25;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public void increaseGrass(int n) {
        if (this.grass+n<MAX_GRASS) {this.grass+= n;} else this.grass = MAX_GRASS;
    }
    public void decreaseGrass(int n) {
        if (this.grass-n>0) {this.grass-= n;} else this.grass = 0;
    }

    public void decreaseGrass() {
        if (this.grass>0) {this.grass--;}
    }

    public void increaseMinerals(int n) {
        {this.minerals += n;}
    }

    public int getGrassAmount() {
        return grass;
    }

    public ArrayList<Cell> getNeibours(){
        ArrayList<Cell> neibours = new ArrayList<Cell>();
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                Cell potentialNeighbour = world.getCell(this.x+i, this.y+j);
                neibours.add(potentialNeighbour);
            }
        }
        return neibours;
    }

    public Cell getNeibour(int direction){
        try {
            return getNeibours().get(direction);
        }catch (Exception e){
            return null;
        }
    }

    public int getMinerals() {
        return minerals;
    }

    public void setMaxGrass(int maxGrass) {
        this.MAX_GRASS = maxGrass;
    }
}
