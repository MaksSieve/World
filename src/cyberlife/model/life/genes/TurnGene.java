package cyberlife.model.life.genes;

import cyberlife.model.life.Animal;

import static cyberlife.model.life.genes.GeneTranslator.geneToInt;

public class TurnGene extends Gene{

    public TurnGene(Animal host) {
        super(host);
    }

    @Override
    public void action() {
        host.setDirection(geneToInt(host.getGenome().getNext())%8);
    }
}
