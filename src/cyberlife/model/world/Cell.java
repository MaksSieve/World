package cyberlife.model.world;

import cyberlife.model.life.Animal;

import java.util.ArrayList;
import java.util.Random;

public class Cell {

    private int grass;
    private Animal animal = null;
    private int maxGrass = 5;
    private int x;
    private int y;

    World world;

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
            this.grass = 2;
        }
        if (prob>15 && prob<=90){
            this.grass = 3;
        }
        if (prob>0 && prob<=5){
            this.grass = 4;
        }
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public void increaseGrass() {
        if (this.grass<maxGrass) {this.grass++;}
    }

    public void decreaseGrass() {
        if (this.grass>0) {this.grass--;}
    }

    public int getGrassAmount() {
        return grass;
    }

    public ArrayList<Cell> getNeibours(){
        ArrayList<Cell> neibours = new ArrayList<Cell>();
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                neibours.add(world.getCell(this.x+i, this.y+j));
            }
        }
        return neibours;
    }

    public Cell getNeibour(int direction){
        return  getNeibours().get(direction);
    }

    public int getGrass() {
        return grass;
    }
}
