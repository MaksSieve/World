package cyberlife.View.gui;

import cyberlife.View.gui.windowsElements.MapView;
import cyberlife.View.gui.windowsElements.StatisticView;
import cyberlife.model.world.World;

import javax.swing.*;
import java.awt.*;

public class GUIVIew extends JFrame{

    private static int MIN_X = 150;
    private static int MIN_Y = 200;

    private MapView worldMap;
    private StatisticView worldStat;
    private JMenuBar menuBar;


    public GUIVIew(int x, int y, World world){



        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));


        worldMap = new MapView(x, y, world);
        worldStat = new StatisticView(world);
        listPane.add(worldStat);
        listPane.add(worldMap);

        Container contentPane = getContentPane();
        contentPane.add(listPane, BorderLayout.CENTER);


        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setMinimumSize(new Dimension(1400, 1100));
        this.setVisible(true);
        this.setName("World");

    }

    public void update(World world) {
        worldMap.update(world);
        worldStat.update(world);
    }
}
