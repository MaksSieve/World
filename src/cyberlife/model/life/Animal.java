package cyberlife.model.life;

import cyberlife.model.LoopList;
import cyberlife.model.life.genes.Gene;
import cyberlife.model.life.genes.HerbivourosGene;
import cyberlife.model.world.Cell;
import cyberlife.model.world.World;

import java.util.Random;

import static cyberlife.model.life.genes.GeneTranslator.intToGene;

public class Animal {

    public static int ALIVE = 1;
    public static int CORPSE = 0;

    private LoopList<Gene> genome;
    private int energy;
    private int maxEnergy = 100;
    private int direction;
    private Cell cell;
    private World world;
    private int status;



    public Animal(int genomeSize, Cell cell, World world){
        genome = new LoopList<Gene>();
        for (int i = 0; i < genomeSize; i++){
            genome.add(new HerbivourosGene(this));
        }
        this.energy = 50;
        this.direction = 0;
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

    public Animal clone(){
        return new Animal(genome,energy,maxEnergy,direction,cell,world,status);
    }

    public Animal step(){

        genome.getNext().action();
        Random random = new Random();
        if (energy >= maxEnergy){
            Cell target = cell.getNeibour(random.nextInt(8));
            if (target.getAnimal() == null) {
                Animal clone = this.clone();
                target.setAnimal(clone);
                world.addToPopulation(clone, this);
            }
            else status = CORPSE;
        }
        if (energy<=0){
            status = CORPSE;
        }

        if (random.nextInt(4)==0){
            genome.set(random.nextInt(genome.size()), intToGene(random.nextInt(41), this));
        }

        return this;
    }

    public Animal increaseEnergy(int diff){
        energy += diff;
        return this;
    }

    public Animal decreaseEnergy(int diff){
        energy -= diff;
        return this;
    }

    public Cell getCell() {
        return cell;
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
}
