package cyberlife.model.life.genes;

import cyberlife.model.life.Animal;
import cyberlife.model.world.Cell;

import static cyberlife.model.life.Animal.ALIVE;

public class CarnivorousGene extends Gene {
    public CarnivorousGene(Animal host) {
        super(host);
    }

    @Override
    public void action() {
        Cell target = host.getCell().getNeibour(host.getDirection());
        if (target != null){
            if (target.getAnimal() != null){
                if (target.getAnimal().getStatus() == ALIVE) {
                    target.getAnimal().decreaseEnergy(1200);
                    host.increaseEnergy(1200);
                    host.increaseRed(100);
                }
            }
        }
    }
}
