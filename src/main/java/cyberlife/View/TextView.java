package cyberlife.View;

import cyberlife.model.life.Animal;
import cyberlife.model.life.genes.Gene;
import cyberlife.model.life.genes.GeneTranslator;
import cyberlife.model.world.World;

import java.util.ArrayList;

public class TextView {

    public static void printWorld(World world){
        ArrayList<String> animals = new ArrayList<>();

        String stat = "";
        int alive = 0;
        int r_number = 0;
        int g_number = 0;
        int b_number = 0;


        for (int i = 0; i < world.getxSize(); i++){
            for (int j = 0; j < world.getySize(); j++){

                Animal animal = world.getMap().get(i).get(j).getAnimal();
//                String a = "0";
//
//                if (animal != null) {if (animal.getStatus() != 0) a="1"; else a = "-";}
//                int grassAmount = world.getMap().get(i).get(j).getGrassAmount();
//                String view = a+"|"+((grassAmount>9)?String.valueOf(grassAmount):"0"+String.valueOf(grassAmount)) + "   ";
//                System.out.print(view);

                String sa = "";

                if (animal != null && animal.getStatus() != 0) {
//                    String genome = "";
//                    int p = animal.getGenome().getPointer();
//                    int k = 0;
//                    for (Gene gene : animal.getGenome()){
//                        String sgene = "";
//                        if (k++ == p) sgene+="*";
//                        sgene += String.valueOf(GeneTranslator.geneToInt(gene));
//                        genome += sgene + " ";
//                    }
//                    int max_type = Math.max(animal.blue, Math.max(animal.green, animal.red));
//                    String r = (animal.red == max_type)?"r":"";
//                    String g = (animal.green == max_type)?"g":"";
//                    String b = (animal.blue == max_type)?"b":"";
//                    sa = sa + "ID" + animal.hashCode() + ", " +
//                            "i: " + i + " " +
//                            "j: " + j + " " +
//                            "X: " + String.valueOf(animal.getCell().getX()) + " " +
//                            "Y: " + String.valueOf(animal.getCell().getY()) + ", " +
//                            "Status: " + String.valueOf(animal.getStatus()) +  ", " +
//                            "Energy: " + String.valueOf(animal.getEnergy()) +  ", " +
//                            "Type: " + r + g + b  +  ", " +
//                            "Direction : " + String.valueOf(animal.getDirection()) +  ", " +
//                            "Genome: [" + genome+ "];";
//                    animals.add(sa);
                    alive++;

                    int max_type = Math.max(animal.blue, Math.max(animal.green, animal.red));
                    if(animal.red == max_type)r_number++;
                    if(animal.green == max_type)g_number++;
                    if(animal.blue == max_type)b_number++;

                }
            }
            //System.out.println();
        }
//        System.out.println();
//        for (String sa : animals){
//            System.out.println(sa);
//        }




        stat = stat + "Alive Population: " + alive + "\n" +
            "The oldest alive animal: " + ((world.getOldest() != null)?animalToString(world.getOldest()):"") + "\n" +
            "Number of green: " + g_number + " " + "Number of red: " + r_number +  " " + "Number of blue: " + b_number;


        System.out.println(stat);
    }

    public static String animalToString(Animal animal){
        StringBuilder genome = new StringBuilder();
        String sa = "";
        if (animal != null) {
            int p = animal.getGenome().getPointer();
            int k = 0;
            for (Gene gene : animal.getGenome()) {
                String sgene = "";
                //if (k++ == p) sgene += "*";
                sgene += String.valueOf(GeneTranslator.geneToInt(gene));
                genome.append(sgene).append(",");
            }
            int max_type = Math.max(animal.blue, Math.max(animal.green, animal.red));
            String r = (animal.red == max_type) ? "r" : "";
            String g = (animal.green == max_type) ? "g" : "";
            String b = (animal.blue == max_type) ? "b" : "";
            sa = "{\"ID\": " + animal.hashCode() + "," +
                    "\"X\": " + String.valueOf(animal.getCell().getX()) + "," +
                    "\"Y\": " + String.valueOf(animal.getCell().getY()) + "," +
                    "\"Status\": " + animal.getStatus() + " " +
                    "\"Age\": " + String.valueOf(animal.getAge()) + "," +
                    "\"Energy\": " + String.valueOf(animal.getEnergy()) + "," +
                    "\"Type\": \"" + r + g + b + "\"," +
                    "\"Genome\": [" + genome + "]}";
        }

        return sa;
    }

}
