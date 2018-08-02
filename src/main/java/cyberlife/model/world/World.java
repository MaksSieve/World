package cyberlife.model.world;

import cyberlife.model.LoopList;
import cyberlife.model.life.Animal;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import static cyberlife.model.life.Animal.ALIVE;
import static cyberlife.model.life.Animal.MAX_DEAD_COUNT;

public class World {

    private int xSize;
    private int ySize;

    private ArrayList<ArrayList<Cell>> map = new ArrayList<ArrayList<Cell>>();
    private LoopList<Animal> population = new LoopList<>();
    private Random random = new Random();


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

        Cell target = map.get(random.nextInt(xSize)).get(random.nextInt(ySize));
        Animal first = new Animal(16, target, this);
        target.setAnimal(first);
        population.add(first);

    }

    public World tick(int tick){
        int n = population.size();
        int k = 0;
        for (int i = 0; i < n ; i++){
            Animal animal = population.getNext();
            if (animal.getStatus() == Animal.ALIVE) {
                animal.step();
                k++;
            }else{
                if (animal.dead_count < MAX_DEAD_COUNT){
                    animal.dead_count++;
                }else{
                    population.remove(animal);
                    population.decreasePointer();
                    animal.getCell().increaseMinerals(animal.getEnergy());
                    animal.getCell().setAnimal(null);
                    animal = null;
                }
            }
        }
        if (tick%50== 0) grassGrow();
        if (tick%100 == 0) grassUpdate();
        if (tick%500 == 0) godHand();
        if (k>0) return this;
        else return null;
    }

    private void grassGrow(){
        for (ArrayList<Cell> row : map){
            for (Cell cell : row){
                int minerals = 0;
                for (Cell neib : cell.getNeibours()){
                    if (neib != null)
                        minerals += (int)Math.round(neib.getMinerals()*0.05);
                }
                int r = random.nextInt(100);
                if (r<10) cell.decreaseGrass(250);
                else cell.increaseGrass(50 + (int)Math.round(minerals*0.01));
            }
        }
    }

    private void grassUpdate(){
        for (ArrayList<Cell> row : map){
            for (Cell cell : row){
                cell.setMaxGrass(250 + (int)Math.round(cell.getMinerals()*0.000001));
            }
        }
    }

    private Set<Integer> randomX(int x){
        Set<Integer> generated = new LinkedHashSet<Integer>();
        while (generated.size() < x)
        {
            Integer next = random.nextInt(16);
            // As we're adding to a set, this will automatically do a containment check
            generated.add(next);
        }
        return generated;
    }

    private void godHand(){
        System.out.println("HAAAAALELLUJAH!");
        for (Animal animal : population){
            if (animal.getStatus() == Animal.ALIVE) {
                for (Integer index : randomX(8)){
                    animal.mutation(index, random.nextInt(16), animal);
                }
            }
        }
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
        if (x<0 || x >= xSize){return null;}
        if (y<=0){y = y+ySize;}
        if (y>=ySize){y = y-ySize;}
        return map.get(x).get(y);
    }

    public void addToPopulation(Animal animal){
        population.add(animal);
    }

    public LoopList<Animal> getPopulation() {
        return population;
    }

    public Animal getOldest(){
        Animal oldest = null;
        int theOldestId = Integer.MAX_VALUE;
        for (Animal animal : population){
            if (animal.getStatus() == ALIVE) {
                if (animal.hashCode() < theOldestId) {
                    theOldestId = animal.hashCode();
                    oldest = animal;
                }
            }
        }
        return oldest;
    }

    public int getAliveNumber(){
        int k = 0;
        for (Animal animal : population){
            if (animal.getStatus() == ALIVE) k++;
        }
        return k;
    }
}
