package cyberlife.model.life.genes;

import cyberlife.model.life.Animal;

public class HerbivourosGene extends Gene{


    public HerbivourosGene(Animal host) {
        super(host);
    }

    @Override
    public void action() {
        int food = (this.host.getCell().getGrassAmount() > 0)?30:0;
        host.increaseEnergy(food);
        host.getCell().decreaseGrass();
    }
}
