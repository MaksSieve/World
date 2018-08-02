package cyberlife.View.gui.windowsElements;

import cyberlife.model.world.World;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

import static cyberlife.View.TextView.animalToString;

public class StatisticView extends JPanel {


    private JLabel aliveStat = new JLabel();
    private JLabel oldestStat= new JLabel();
    private JLabel greenStat = new JLabel();
    private JLabel redStat = new JLabel();
    private JLabel blueStat = new JLabel();
    private JLabel currentTick = new JLabel();
    private JLabel currentAverageAge = new JLabel();
    private JLabel currentMaxAge = new JLabel();

    public StatisticView(World world) {
        super();
        this.setBorder(new BorderUIResource.LineBorderUIResource(Color.black, 1));
        this.setBackground(Color.LIGHT_GRAY);
        GridLayout layout = new GridLayout(5, 1);
        layout.setHgap(2);
        layout.setVgap(2);
        this.setLayout(layout);
        this.setBounds(new Rectangle(480,480));

        this.add(aliveStat);
        this.add(oldestStat);
        this.add(currentTick);
        this.add(currentAverageAge);
        this.add(currentMaxAge);

        update(world);
    }

    public void update(World world){
        aliveStat.setText("Alive population: " + world.getAliveNumber());
        oldestStat.setText("The oldest alive: " + animalToString(world.getOldest()));
        currentTick.setText("Current tick: " + world.getCurrentTick());
        currentAverageAge.setText("Current Average Age: " + world.getCurrentAverageAge());
        currentMaxAge.setText("Current Max Age: " + world.getMaxAge());
    }
}
