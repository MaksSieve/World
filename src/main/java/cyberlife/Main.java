package cyberlife;

import cyberlife.View.TextView;
import cyberlife.View.gui.GUIVIew;
import cyberlife.model.world.World;

public class Main {

    private static int TICKS = 1000000;
    private static int world_X = 75;
    private static int world_Y = 100;


    public static void main(String[] args) throws InterruptedException {

        World BraveNewWorld = new World(world_X, world_Y);
        System.out.println("Tick 0");
        TextView.printWorld(BraveNewWorld);
        GUIVIew window = new GUIVIew(world_X, world_Y, BraveNewWorld);

        int i = 1;
        while(true){
            if(BraveNewWorld.tick(i) != null) {
                if (i%1==0){
                    window.update(BraveNewWorld);
                    Thread.sleep(100);
                }
                if (i%25==0){
                    System.out.println("Tick " + String.valueOf(i));
                    TextView.printWorld(BraveNewWorld);
                }

            }else{
                System.out.println("Tick " + String.valueOf(i));
                window.update(BraveNewWorld);
                TextView.printWorld(BraveNewWorld);
                System.out.println("POPULATION DEAD...");
                break;
            }
            i++;
        }

    }
}
