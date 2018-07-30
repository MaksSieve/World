package cyberlife.View;

import cyberlife.model.world.World;

import java.sql.SQLOutput;

public class TextView {

    public static void printWorld(World world){
        for (int i = 0; i < world.getxSize(); i++){
            for (int j = 0; j < world.getySize(); j++){
                int animal = (world.getMap().get(i).get(j).getAnimal()==null)?0:10;
                int cell = world.getMap().get(i).get(j).getGrassAmount();
                String view = String.valueOf(animal+cell) + " ";
                System.out.print(view);
            }
            System.out.println();
        }
    }

}
