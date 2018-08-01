package cyberlife.View.gui.windowsElements;

import cyberlife.model.life.Animal;

import javax.swing.*;

public class AnimalView extends JLabel{

    private final Animal model;


    public AnimalView(Animal model) {
        this.model = model;

    }


    public void update(Animal newState) {

    }
}
