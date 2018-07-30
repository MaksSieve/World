package cyberlife.model.world;

import cyberlife.model.LoopList;
import cyberlife.model.life.Animal;

import java.util.ArrayList;
import java.util.Random;

public class World {

    private int xSize;
    private int ySize;

    private ArrayList<ArrayList<Cell>> map = new ArrayList<ArrayList<Cell>>();
    private LoopList<Animal> population = new LoopList<>();


    public World(int x, int y){
        setxSize(x);
        setySize(y);

        for (int i = 0; i < xSize; i++){
            ArrayList<Cell> row = new ArrayList<Cell>();
            for (int j = 0; j < ySize; j++){
                row.add(new Cell(i,j,this));
            }
            map.add(row);
        }

        Random random = new Random();

        Cell target = map.get(random.nextInt(xSize)).get(random.nextInt(ySize));
        Animal first = new Animal(16, target, this);
        target.setAnimal(first);
        population.add(first);

    }


    public World tick(){

        for (Animal animal: population){
            if (animal.getStatus() == Animal.ALIVE) animal.step();
        }

        return this;
    }

    public int getxSize() {
        return xSize;
    }

    private void setxSize(int xSize) {
        this.xSize = xSize;
    }

    public int getySize() {
        return ySize;
    }

    private void setySize(int ySize) {
        this.ySize = ySize;
    }

    public ArrayList<ArrayList<Cell>> getMap() {
        return map;
    }

    Cell getCell(int x, int y){
        if (x<0 || x > xSize){return null;}
        if (y<=0){y = y+ySize;}
        if (y>=ySize){y = y-ySize;}
        return map.get(x).get(y);
    }

    public void addToPopulation(Animal animal, Animal host){
        population.add(population.indexOf(host)-1, animal);
    }
}
