package cyberlife.View.gui.windowsElements;

import cyberlife.model.life.Animal;

import javax.swing.*;

import static cyberlife.View.TextView.animalToString;
import static cyberlife.model.life.Animal.CORPSE;

public class AnimalView extends JLabel{



    private final ImageIcon green = new ImageIcon(CellView.class.getResource("/greenAnimal.png"));
    private final ImageIcon red = new ImageIcon(CellView.class.getResource("/redAnimal.png"));
    private final ImageIcon blue = new ImageIcon(CellView.class.getResource("/blueAnimal.png"));
    private final ImageIcon dead = new ImageIcon(CellView.class.getResource("/blackAnimal.png"));

    public AnimalView() {
    }


    public void update(Animal animal) {
        if (animal.getStatus() == CORPSE) this.setIcon(dead);
        else{
            int max_type = Math.max(animal.blue, Math.max(animal.green, animal.red));
            if (animal.green == max_type) setIcon(green);
            else{
                if(animal.blue == max_type) setIcon(blue);
                else setIcon(red);
            }
        }

        this.setToolTipText("<html><p width=\"500\">" +animalToString(animal)+"</p></html>");
        setVisible(true);
    }
    public void off(){
        setVisible(false);
    }
}
