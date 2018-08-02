package cyberlife.View.gui.windowsElements;

import cyberlife.model.world.Cell;
import cyberlife.model.world.World;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.util.ArrayList;

public class MapView extends JPanel {

    private ArrayList<ArrayList<CellView>> map = new ArrayList<ArrayList<CellView>>();

    public MapView(int x, int y, World world) {

        super();
        this.setBorder(new BorderUIResource.LineBorderUIResource(Color.black, 1));
        this.setBackground(Color.LIGHT_GRAY);
        GridLayout layout = new GridLayout(x, y);
        layout.setHgap(2);
        layout.setVgap(2);
        this.setLayout(layout);
        this.setBounds(new Rectangle(480,480));

        for (ArrayList<Cell> row : world.getMap()){
            ArrayList<CellView> ROW = new ArrayList<>();
            for (Cell cell : row){
                CellView cellView = new CellView(cell);
                ROW.add(cellView);
                this.add(cellView);
            }
            map.add(ROW);
        }
    }

    public void update(World world){
        for (int i = 0; i < world.getxSize(); i++){
            for (int j = 0; j < world.getySize(); j++){
                Cell newCell = world.getMap().get(i).get(j);
                CellView oldCell = map.get(i).get(j);
                oldCell.update(newCell);
            }
        }


    }
}
