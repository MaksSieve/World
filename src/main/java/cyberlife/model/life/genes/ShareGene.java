package cyberlife.model.life.genes;

import cyberlife.model.life.Animal;

public class ShareGene extends Gene{
    public ShareGene(Animal host) {
        super(host);
    }

    @Override
    public void action() {

        int energyForShare = (int)Math.round(host.getEnergy()*0.32);
        host.decreaseEnergy(energyForShare);
        int neighbNumber = host.getNeighbours().size();
        for (Animal animal : host.getNeighbours()){
            animal.increaseEnergy(Math.round(energyForShare/neighbNumber));
        }
    }
}
