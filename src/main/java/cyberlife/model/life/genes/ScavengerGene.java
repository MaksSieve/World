package cyberlife.model.life.genes;

import cyberlife.model.life.Animal;
import cyberlife.model.world.Cell;

import static cyberlife.model.life.Animal.CORPSE;

public class ScavengerGene extends Gene {

    public ScavengerGene(Animal host) {
        super(host);
    }

    @Override
    public void action() {
        Cell target = host.getCell().getNeibour(host.getDirection());
        if (target != null){
            if (target.getAnimal() != null){
                if (target.getAnimal().getStatus() == CORPSE && target.getAnimal().getEnergy() > 0) {
                    target.getAnimal().decreaseEnergy(450);
                    host.increaseEnergy(450+((target.getAnimal().getEnergy() > 0)?0:target.getAnimal().getEnergy()));
                    host.increaseBlue(10);
                }
            }
        }
    }
}
