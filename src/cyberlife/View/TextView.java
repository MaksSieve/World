package cyberlife.View;

import cyberlife.model.life.Animal;
import cyberlife.model.life.genes.Gene;
import cyberlife.model.life.genes.GeneTranslator;
import cyberlife.model.world.World;

import java.util.ArrayList;

public class TextView {

    public static void printWorld(World world){
        ArrayList<String> animals = new ArrayList<>();

        for (int i = 0; i < world.getxSize(); i++){
            for (int j = 0; j < world.getySize(); j++){

                Animal animal = world.getMap().get(i).get(j).getAnimal();
                String a = "0";
                if (animal != null) {if (animal.getStatus() != 0) a="1"; else a = "-";}
                int grassAmount = world.getMap().get(i).get(j).getGrassAmount();
                String view = a+String.valueOf(grassAmount) + "   ";
                System.out.print(view);

                String sa = "";
                if (animal != null && animal.getStatus() != 0) {
                    String genome = "";
                    int p = animal.getGenome().getPointer();
                    int k = 0;
                    for (Gene gene : animal.getGenome()){
                        String sgene = "";
                        if (k++ == p) sgene+="*";
                        sgene += String.valueOf(GeneTranslator.geneToInt(gene));
                        genome += sgene + " ";
                    }
                    int max_type = Math.max(animal.blue, Math.max(animal.green, animal.red));
                    String r = (animal.red == max_type)?"r":"";
                    String g = (animal.green == max_type)?"g":"";
                    String b = (animal.blue == max_type)?"b":"";
                    sa = sa + "ID" + animal.hashCode() + ", " +
                            "X: " + String.valueOf(animal.getCell().getX()) + " " +
                            "Y: " + String.valueOf(animal.getCell().getY()) + ", " +
                            "Status: " + String.valueOf(animal.getStatus()) +  ", " +
                            "Energy: " + String.valueOf(animal.getEnergy()) +  ", " +
                            "Type: " + r + g + b  +  ", " +
                            "Direction : " + String.valueOf(animal.getDirection()) +  ", " +
                            "Genome: [" + genome+ "];";
                    animals.add(sa);
                }
            }
            System.out.println();
        }
        System.out.println("Alive Population: " + String.valueOf(animals.size()));
        for (String sa : animals){
            System.out.println(sa);
        }

        System.out.println();
    }

}
