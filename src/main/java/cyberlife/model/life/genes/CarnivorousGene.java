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
        Animal target = host.findVictim();
        if (target != null){
            if (target.getStatus() == ALIVE) {
                target.decreaseEnergy(1000);
                host.increaseEnergy(1000);
                host.increaseRed(50);
            }
        }
    }
}
