package cyberlife.model.life.genes;

import cyberlife.model.life.Animal;

import java.util.Random;

public class GeneTranslator {

    private static Random random = new Random();

    public static int geneToInt(Gene gene){
        if (gene instanceof HerbivorousGene) return 5;
        if (gene instanceof MovementGene) return 10;
        if (gene instanceof TurnGene) return 15;
        if (gene instanceof ScavengerGene) return 6;
        if (gene instanceof CarnivorousGene) return 7;
        return random.nextInt(5)+1;
    }

    public static Gene intToGene(int code, Animal host){
        switch (code){
            case 5: return new HerbivorousGene(host);
            case 6: return new ScavengerGene(host);
            case 7: return new CarnivorousGene(host);
            case 10: return new MovementGene(host);
            case 15: return new TurnGene(host);
            default: return new Gene(host);
        }
    }
}
