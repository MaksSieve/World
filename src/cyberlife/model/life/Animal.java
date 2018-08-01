package cyberlife.model.life;

import cyberlife.model.LoopList;
import cyberlife.model.life.genes.Gene;
import cyberlife.model.life.genes.HerbivorousGene;
import cyberlife.model.world.Cell;
import cyberlife.model.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import static cyberlife.model.life.genes.GeneTranslator.intToGene;

public class Animal {

    public static int ALIVE = 1;
    public static int CORPSE = 0;
    private static int UNIQUE_ID = 0;
    private static int MAX_COLOR = 100;
    public static int MAX_DEAD_COUNT = 20;
    private int uid = ++UNIQUE_ID;

    private Random random = new Random();
    private LoopList<Gene> genome;
    private int energy;
    private int maxEnergy = 100;
    private int direction;
    private Cell cell;
    private World world;
    private int status;


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
        this.energy = 50;
        this.direction = random.nextInt(8);
        this.cell = cell;
        this.world = world;
        this.status = ALIVE;
    }

    public Animal(LoopList<Gene> genome, int energy, int maxEnergy, int direction, Cell cell, World world, int status) {
        this.genome = genome;
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.direction = direction;
        this.cell = cell;
        this.world = world;
        this.status = status;
    }

    public int hashCode() {
        return uid;
    }

    public Animal clone(){
        return new Animal(genome,energy,maxEnergy,direction,cell,world,status);
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
        target.setAnimal(clone);
        world.addToPopulation(clone);
    }

    public void step(){
        this.k = 0;
        genome.getNext().action();

        if (energy >= maxEnergy){
            Cell target = cell.getNeibour(random.nextInt(8));
            if (target != null) {
                if (target.getAnimal() != null) {
                    if (target.getAnimal().getStatus() != ALIVE) {
                        reproduction(target);
                        this.decreaseEnergy(80);
                    }else dead();
                }else{
                    reproduction(target);
                    this.decreaseEnergy(80);
                }
            }else dead();
        }

        if (energy<=0){
            dead();
        }

        if (random.nextInt(4)==0){
            mutation(random.nextInt(genome.size()), random.nextInt(16), this);
        }

        this.decreaseEnergy(10);

    }

    public void mutation(int index, int geneCode, Animal host){
        genome.set(index, intToGene(geneCode, host));
    }

    private void dead(){
        status = CORPSE;
        //world.kickFromPopulation(this);
    }

    public void increaseEnergy(int diff){
        if (energy+diff<=maxEnergy) energy += diff; else energy = maxEnergy;
    }

    public void decreaseEnergy(int diff){
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

    public int getEnergy() {
        return energy;
    }

    private void setGenome(LoopList<Gene> genome) {
        this.genome = genome;
    }
}
