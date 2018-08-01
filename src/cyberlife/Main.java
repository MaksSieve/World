package cyberlife;

import cyberlife.View.GUIView;
import cyberlife.View.TextView;
import cyberlife.model.world.World;

import java.awt.*;

public class Main {

    private static int TICKS = 100000;

    public static void main(String[] args) throws InterruptedException {

        int world_X = 5;
        int world_Y = 5;

        World BraveNewWorld = new World(world_X, world_Y);
        System.out.println("Tick 0");
        TextView.printWorld(BraveNewWorld);
        int i = 1;
        while(true){
            if(BraveNewWorld.tick(i) != null) {
                if (i%1==0){
                    System.out.println("Tick " + String.valueOf(i));
                    TextView.printWorld(BraveNewWorld);
                    Thread.sleep(5000);
                }

            }else{
                System.out.println("Tick " + String.valueOf(i));
                TextView.printWorld(BraveNewWorld);
                System.out.println("POPULATION DEAD...");
                break;
            }
            i++;
        }

    }
}
