package cyberlife;

import cyberlife.View.GUIView;
import cyberlife.View.TextView;
import cyberlife.model.world.World;

import java.awt.*;

public class Main {

    private static int TICKS = 100000;

    public static void main(String[] args) throws InterruptedException {

        World BraveNewWorld = new World(20,40);
        System.out.println("Tick 0");
        TextView.printWorld(BraveNewWorld);
        for (int i = 1; i < TICKS; i++){
            if(BraveNewWorld.tick(i) != null) {
                if (i%100==0){
                    System.out.println("Tick " + String.valueOf(i));
                    TextView.printWorld(BraveNewWorld);
                    Thread.sleep(5000);
                }

            }else{
                System.out.println("POPULATION DEAD...");
                break;
            }
        }

    }
}
