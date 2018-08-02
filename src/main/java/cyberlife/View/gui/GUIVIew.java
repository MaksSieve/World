package cyberlife.View.gui;

import cyberlife.View.gui.windowsElements.MapView;
import cyberlife.model.world.World;

import javax.swing.*;

public class GUIVIew extends JFrame{

    private static int MIN_X = 150;
    private static int MIN_Y = 200;

    private MapView worldMap;
    private JPanel worldStat;
    private JMenuBar menuBar;


    public GUIVIew(int x, int y, World world){
        worldMap = new MapView(x, y, world);
        this.add(worldMap);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setName("World");

    }

    public void update(World world) {
        worldMap.update(world);
    }
}
