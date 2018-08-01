package cyberlife.View.gui.windowsElements;

import cyberlife.model.world.Cell;

import javax.swing.*;

public class CellView extends JLabel {

    private Cell model;
    private final ImageIcon greenBack = new ImageIcon(CellView.class.getResource("/green.png"));
    private final ImageIcon brownBack = new ImageIcon(CellView.class.getResource("/brown.png"));


    private AnimalView animal;

    public CellView(Cell model){
        this.model = model;
        this.animal = new AnimalView(model.getAnimal());
        update(model);
        setVisible(true);
    }


    public void update(Cell newCell){
        if (model.getGrassAmount()>0) this.setIcon(greenBack); else this.setIcon(brownBack);
        if (newCell.getAnimal() != null) animal.update(newCell.getAnimal()); else animal.setVisible(false);
    }

    public Cell getModel() {
        return model;
    }

    public void setModel(Cell model) {
        this.model = model;
    }

    public AnimalView getAnimal() {
        return animal;
    }

    public void setAnimal(AnimalView animal) {
        this.animal = animal;
    }
}
