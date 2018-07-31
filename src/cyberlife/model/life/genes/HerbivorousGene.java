package cyberlife.model.life.genes;

import cyberlife.model.life.Animal;

public class HerbivorousGene extends Gene{


    public HerbivorousGene(Animal host) {
        super(host);
    }

    @Override
    public void action() {
        int food = (this.host.getCell().getGrassAmount() > 0)?50:0;
        host.increaseEnergy(food);
        host.getCell().decreaseGrass();
        host.increaseGreen(10);
    }
}
