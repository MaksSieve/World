package cyberlife.model.world;

import cyberlife.model.LoopList;
import cyberlife.model.life.Animal;
import org.influxdb.dto.Point;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import static cyberlife.model.life.Animal.ALIVE;
import static cyberlife.model.life.Animal.MAX_DEAD_COUNT;

public class World {

    private int xSize;
    private int ySize;

    private int currentTick = 0;

    private ArrayList<ArrayList<Cell>> map = new ArrayList<ArrayList<Cell>>();
    private LoopList<Animal> population = new LoopList<>();
    private Random random = new Random();
    private int MAX_AGE = 0;
    private int month = 3;
    private int season  = 1;
    private int R_COUNT = 0;
    private int G_COUNT = 0;
    private int B_COUNT = 0;
    private int winterLong = 4;


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
        currentTick++;
        int n = population.size();
        int k = 0;
        int r_number = 0;
        int g_number = 0;
        int b_number = 0;
        for (int i = 0; i < n ; i++){
            Animal animal = population.getNext();
            if (animal.getStatus() == Animal.ALIVE) {
                animal.step();
                k++;
                if (animal.getAge() > MAX_AGE) MAX_AGE = animal.getAge();
                int max_type = Math.max(animal.blue, Math.max(animal.green, animal.red));
                if(animal.red == max_type)r_number++;
                if(animal.green == max_type)g_number++;
                if(animal.blue == max_type)b_number++;

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
        R_COUNT = r_number;
        G_COUNT = g_number;
        B_COUNT = b_number;

        if (tick%720 == 0)increaseMonth();
        if (tick%360 == 0) grassGrow();
        if (tick%4320 == 0) grassUpdate();
        if (tick%43200 == 0) godHand();
        if (k>0) return this;
        else return null;
    }

    private void grassGrow(){
        for (ArrayList<Cell> row : map){
            for (Cell cell : row){
                int minerals = 0;
                if (getTemperature() < 0){
                    cell.decreaseGrass(cell.getGrassAmount());
                }
                else {
                    double t = getTemperature();
                    for (Cell neib : cell.getNeibours()) {
                        if (neib != null)
                            minerals += (int) Math.round(neib.getMinerals() * 0.05);
                    }
                    int r = random.nextInt(100);
                    if (r < 10) cell.decreaseGrass(250);
                    else cell.increaseGrass(Math.round(25 + (int) Math.round(minerals * 0.01)*t/30));
                }
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
                for (Integer index : randomX(4)){
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

    public int getCurrentTick(){
        return currentTick;
    }

    public int getMaxAge(){
        return MAX_AGE;
    }

    public double getCurrentAverageAge(){
        int k = 0;
        int s = 0;
        for (Animal animal : population){
            if (animal.getStatus() == ALIVE) {
                k++;
                s += animal.getAge();
            }
        }
        if (k > 0) return s/k; else return 0;
    }

    @Override
    public String toString() {
        return worldToPoint().toString();
    }

    public double getTemperature(){
        return  random.nextInt(10) + 30*Math.cos(Math.PI*(0.185*month-1))- 5;
    }

    public void increaseMonth(){
        if (month + 1 > 11){
            month = 0;
            season = 0;
            winterLong = random.nextInt(9);
        }else{
            month++;
            if (month >= winterLong) season = 1;
            if (month >= winterLong + 2) season = 2;
            if (month >= 10) season = 3;
        }
    }

    public double getAverageEnergy(){
        int k = 0;
        int s = 0;
        for (Animal animal: population){
            if (animal.getStatus() == ALIVE) {
                s += animal.getEnergy();
                k++;
            }
        }

        return s/k;
    }

    public double  getMaxEnergy(){
        double max = 0;
        for (Animal animal: population){
            if (animal.getStatus() == ALIVE) {
                max = (max < animal.getEnergy()) ? animal.getEnergy() : max;
            }
        }
        return max;
    }

    public double  getMinEnergy(){
        double min = 10000;
        for (Animal animal: population){
            if (animal.getStatus() == ALIVE) {
                min = (min > animal.getEnergy()) ? animal.getEnergy() : min;
            }
        }
        return min;
    }

    public Point worldToPoint(){
        return Point.measurement("CyberLife")
                .addField("alive", getAliveNumber())
                .addField("dead", population.size()-getAliveNumber())
                .addField("month", month)
                .addField("oldest", getOldest().getAge())
                .addField("maxAge", MAX_AGE)
                .addField("currentTick", currentTick)
                .addField("red", R_COUNT)
                .addField("green", G_COUNT)
                .addField("blue", B_COUNT)
                .addField("maxEnergy", getMaxEnergy())
                .addField("minEnergy", getMinEnergy())
                .addField("avgEnergy", getAverageEnergy())
                .addField("temperature", getTemperature())
                .build();
    }
}
