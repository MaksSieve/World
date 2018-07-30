package cyberlife;

import cyberlife.View.TextView;
import cyberlife.model.world.World;

public class Main {

    private static int TICKS = 100;

    public static void main(String[] args) throws InterruptedException {

        World BraveNewWorld = new World(10,10);
        TextView.printWorld(BraveNewWorld);
        for (int i = 0; i < TICKS; i++){
            BraveNewWorld.tick();
            if (i%5==0) TextView.printWorld(BraveNewWorld);
            Thread.sleep(1000);
        }
    }
}
