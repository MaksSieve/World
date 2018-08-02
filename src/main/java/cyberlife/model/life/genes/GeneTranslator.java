package cyberlife.model.life.genes;

import cyberlife.model.life.Animal;

import java.util.Random;

public class GeneTranslator {

    private static Random random = new Random();

    public static int geneToInt(Gene gene){
        if (gene instanceof ShareGene) return 0;
        if (gene instanceof HerbivorousGene) return 1;
        if (gene instanceof ScavengerGene) return 2;
        if (gene instanceof CarnivorousGene) return 3;
        if (gene instanceof MovementGene) return 4;
        if (gene instanceof TurnGene) return 5;
        return random.nextInt(10)+6;
    }

    public static Gene intToGene(int code, Animal host){
        switch (code){
            case 0: return new ShareGene(host);
            case 1: return new HerbivorousGene(host);
            case 2: return new ScavengerGene(host);
            case 3: return new CarnivorousGene(host);
            case 4: return new MovementGene(host);
            case 5: return new TurnGene(host);
            default: return new Gene(host);
        }
    }
}
