package cyberlife;

import cyberlife.View.TextView;
import cyberlife.View.gui.GUIVIew;
import cyberlife.model.world.World;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Main {

    private static int TICKS = 100;
    private static int world_X = 100;
    private static int world_Y = 150;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy_HHmmss.z");
    private static String dateTime = dateFormat.format(new Date(System.currentTimeMillis()));
    private static String fileName = "log_" + dateTime +".log";


    public static void main(String[] args) throws InterruptedException, IOException {

        World BraveNewWorld = new World(world_X, world_Y);
        System.out.println("Tick 0");
        TextView.printWorld(BraveNewWorld);
        GUIVIew window = new GUIVIew(world_X, world_Y, BraveNewWorld);

//        InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086", "root", "root");
//        String dbName = "CyberLife";
//        influxDB.setDatabase(dbName);
//        influxDB.enableGzip();

//        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
//        writer.write(BraveNewWorld.toString());

        try {
            int i = 1;
            while (true) {
                World ptr = BraveNewWorld.tick(i);
                if (ptr != null) {
                    if (i % 1 == 0) {
                        window.update(BraveNewWorld);
                        Thread.sleep(1);

                    }
                    if (i%24 == 0){
                        //influxDB.write(ptr.worldToPoint());
                        System.out.println("Tick " + String.valueOf(i));
//                        TextView.printWorld(ptr);
                        //writer.write(ptr.toString()+"\n");
                    }

                } else {
                    System.out.println("Tick " + String.valueOf(i));
                    //window.update(BraveNewWorld);
                    TextView.printWorld(BraveNewWorld);
                    System.out.println("POPULATION DEAD...");
                    break;
                }
                i++;
            }
        }finally {
            //writer.close();
        }


    }
}
