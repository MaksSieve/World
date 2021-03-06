package cyberlife.model.life;

import cyberlife.View.TextView;
import cyberlife.model.LoopList;
import cyberlife.model.life.genes.Gene;
import cyberlife.model.life.genes.GeneTranslator;
import cyberlife.model.life.genes.HerbivorousGene;
import cyberlife.model.world.Cell;
import cyberlife.model.world.World;
import org.influxdb.dto.Point;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static cyberlife.model.life.genes.GeneTranslator.intToGene;

public class Animal {

    public static int ALIVE = 1;
    public static int CORPSE = 0;
    private static int UNIQUE_ID = 0;
    private static int MAX_COLOR = 100;
    public static int MAX_DEAD_COUNT = 10;
    private int uid = ++UNIQUE_ID;
    private static int maxEnergy = 1000;


    private Random random = new Random();
    private LoopList<Gene> genome;
    private double energy;
    private int direction;
    private Cell cell;
    private World world;
    private int status;
    private ArrayList<Animal> neighbours = null;
    private int age = 0;

    public int green = 0;
    public int red = 0;
    public int blue = 0;
    public int k = 0;
    public int dead_count = 0;

    public Animal(int genomeSize, Cell cell, World world){
        genome = new LoopList<>();
        for (int i = 0; i < genomeSize; i++){
            genome.add(new HerbivorousGene(this));
        }
        this.energy = 500;
        this.direction = random.nextInt(8);
        this.setCell(cell);
        this.world = world;
        this.status = ALIVE;
    }

    public Animal(LoopList<Gene> genome, int energy, int direction, Cell cell, World world, int status) {
        this.genome = genome;
        this.energy = energy;
        this.direction = direction;
        this.cell = cell;
        this.world = world;
        this.status = status;
    }

    public int hashCode() {
        return uid;
    }

    private LoopList<Gene> genomeClone(Animal recipient){
        LoopList<Gene> genome2 = new LoopList<>();
        for (Gene gene : genome){
            try {
                try {
                    Constructor<? extends Gene> constructor =  gene.getClass().getConstructor(Animal.class);
                    genome2.add(constructor.newInstance(recipient));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return genome2;
    }

    private void reproduction(Cell target){
        Animal clone = new Animal(genome.size(), target, world);
        clone.setGenome(genomeClone(clone));
        clone.setDirection(random.nextInt(8));
        clone.setColor(red, green, blue);
        target.setAnimal(clone);
        world.addToPopulation(clone);
    }

    public void step(){
        this.age++;
        this.k = 0;
        setNeighbours(updateNeighbours());
        genome.getNext().action();
        if (energy >= maxEnergy){
            Cell target = cell.getNeibour(random.nextInt(8));
            if (target != null) {
                if (target.getAnimal() != null) {
                    if (target.getAnimal().getStatus() != ALIVE) {
                        reproduction(target);
                        this.decreaseEnergy(300);
                    }else dead();
                }else{
                    reproduction(target);
                    this.decreaseEnergy(300);
                }
            }else dead();
        }

        if (energy<=0){
            dead();
        }

        if (random.nextInt(50)==0){
            mutation(random.nextInt(genome.size()), random.nextInt(15), this);
        }

        shareEnergy();

        this.decreaseEnergy(Math.round(-5 * world.getTemperature()/2500));

    }

    public void mutation(int index, int geneCode, Animal host){
        genome.set(index, intToGene(geneCode, host));
    }

    private void dead(){
        status = CORPSE;
        //world.kickFromPopulation(this);
    }

    public void increaseEnergy(double diff){
        if (energy+diff<=maxEnergy) energy += diff; else energy = maxEnergy;
    }

    public void decreaseEnergy(double diff){
        if (energy-diff>=0) energy -= diff; else energy = 0;
    }

    public void increaseGreen(int diff){
        if (green+diff<=MAX_COLOR) green += diff; else green = MAX_COLOR;
        if (red-diff>=0) red -= diff; else red = 0;
        if (blue-diff>=0) blue -= diff; else blue = 0;
    }

    public void increaseRed(int diff){
        if (red+diff<=MAX_COLOR) red += diff; else red = MAX_COLOR;
        if (green-diff>=0) green -= diff; else green = 0;
        if (blue-diff>=0) blue -= diff; else blue = 0;
    }

    public void increaseBlue(int diff){
        if (blue+diff<=MAX_COLOR) blue += diff; else blue = MAX_COLOR;
        if (green-diff>=0) green -= diff; else green = 0;
        if (red-diff>=0) red -= diff; else red = 0;
    }

    public Cell getCell() {
        return cell;
    }

    private void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getDirection() {
        return direction;
    }

    public LoopList<Gene> getGenome() {
        return genome;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getStatus() {
        return status;
    }

    public double getEnergy() {
        return energy;
    }

    private void setGenome(LoopList<Gene> genome) {
        this.genome = genome;
    }

    private void setColor(int r, int g, int b){
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    public void setNeighbours(ArrayList<Animal> neighbours) {
        this.neighbours = neighbours;
    }

    public ArrayList<Animal> getNeighbours() {
        return neighbours;
    }

    public boolean isGenomeEquals(Animal other){
        int k = 0;
        LoopList<Gene> otherGenome =  other.getGenome();
        for (int i = 0; i < genome.size(); i++){
            if (genome.get(i).getClass() != otherGenome.get(i).getClass()){
                k++;
            }
        }
        return k < 10;
    }

    private ArrayList<Animal> updateNeighbours(){
        ArrayList<Cell> neighbCells = this.cell.getNeibours();
        ArrayList<Animal> neighbours = new ArrayList<>();
        for (Cell cell:neighbCells) {
            if (cell != null) {
                Animal potentialNeighbour = cell.getAnimal();
                if (potentialNeighbour != null) {
                    if (isGenomeEquals(potentialNeighbour)) {
                        neighbours.add(potentialNeighbour);
                    }
                }
            }
        }
        return neighbours;
    }

    public int getUid() {
        return uid;
    }

    public int getAge() {
        return age;
    }

    private void shareEnergy(){
        int energyForShare = (int)Math.round(this.getEnergy()*0.32);
        this.decreaseEnergy(energyForShare);
        int neighbNumber = this.getNeighbours().size();
        for (Animal animal : this.getNeighbours()){
            animal.increaseEnergy(Math.round(energyForShare/neighbNumber));
        }
    }

    @Override
    public String toString() {
        return TextView.animalToString(this);
    }

    public Point toInfluxPoint(){
        int max_type = Math.max(this.blue, Math.max(this.green, this.red));
        String r = (this.red == max_type) ? "r" : "";
        String g = (this.green == max_type) ? "g" : "";
        String b = (this.blue == max_type) ? "b" : "";

        return Point.measurement("CyberLife")
                .time(System.currentTimeMillis()+ this.hashCode()/1000, TimeUnit.MILLISECONDS)
                .addField("Id", String.valueOf(this.hashCode()))
                .tag("Status", String.valueOf(this.getStatus()))
                .addField("Age", this.getAge())
                .addField("Energy", this.getEnergy())
                .tag("Type", r+g+b)
                .build();
    }

    public Animal findVictim(){

        for (Cell neibCell : cell.getNeibours()){
            if (neibCell != null && neibCell.getAnimal() != null) return neibCell.getAnimal();
        }
        return null;
    }
}
