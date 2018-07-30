package cyberlife.model.life.genes;

import cyberlife.model.life.Animal;

public class GeneTranslator {

    public static int geneToInt(Gene gene){
        if (gene instanceof HerbivourosGene) return 25;
        if (gene instanceof MovementGene) return 30;
        if (gene instanceof TurnGene) return 35;
        return 0;
    }

    public static Gene intToGene(int code, Animal host){
        switch (code){
            case 25: return new HerbivourosGene(host);
            case 30: return new MovementGene(host);
            case 35: return new TurnGene(host);
            default: return new Gene(host);
        }
    }
}
