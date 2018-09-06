package cyberlife.View;

import cyberlife.model.life.Animal;
import cyberlife.model.life.genes.Gene;
import cyberlife.model.life.genes.GeneTranslator;
import cyberlife.model.world.World;

public class TextView {

    public static void printWorld(World world){
        int alive = 0;
        int r_number = 0;
        int g_number = 0;
        int b_number = 0;

        for (int i = 0; i < world.getxSize(); i++){
            for (int j = 0; j < world.getySize(); j++){
                Animal animal = world.getMap().get(i).get(j).getAnimal();
                if (animal != null && animal.getStatus() == Animal.ALIVE) {
                    alive++;
                    int max_type = Math.max(animal.blue, Math.max(animal.green, animal.red));
                    if(animal.red == max_type)r_number++;
                    if(animal.green == max_type)g_number++;
                    if(animal.blue == max_type)b_number++;
                }
            }

        }

        String stat1 = "Alive Population: " + alive + "\n" +
                "The oldest alive animal: " + ((world.getEverOldest() != null)?animalToString(world.getEverOldest()):"") + "\n" +
                "Number of green: " + g_number + " " + "Number of red: " + r_number +  " " + "Number of blue: " + b_number;

        System.out.println(stat1);

        for (Animal animal : world.getPopulation()) {

            if (animal != null && animal.getStatus() == Animal.ALIVE) {
                alive++;
                int max_type = Math.max(animal.blue, Math.max(animal.green, animal.red));
                if (animal.red == max_type) r_number++;
                if (animal.green == max_type) g_number++;
                if (animal.blue == max_type) b_number++;
            }

        }
        String stat2 = "Alive Population: " + alive + "\n" +
            "The oldest alive animal: " + ((world.getEverOldest() != null)?animalToString(world.getEverOldest()):"") + "\n" +
            "Number of green: " + g_number + " " + "Number of red: " + r_number +  " " + "Number of blue: " + b_number;

        System.out.println(stat2);
    }

    public static String animalToString(Animal animal){
        StringBuilder genome = new StringBuilder();
        String sa = "";
        if (animal != null) {
            for (Gene gene : animal.getGenome()) {
                String sgene = "";
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
