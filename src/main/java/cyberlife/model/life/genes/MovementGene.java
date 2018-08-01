package cyberlife.model.life.genes;

import cyberlife.model.life.Animal;
import cyberlife.model.world.Cell;

public class MovementGene extends Gene{

    public MovementGene(Animal host) {
        super(host);
    }

    @Override
    public void action() {
        Cell target = host.getCell().getNeibour(host.getDirection());
        if (target != null){
            host.getCell().setAnimal(null);
            target.setAnimal(host);
            host.decreaseEnergy(100);
        }
    }
}
