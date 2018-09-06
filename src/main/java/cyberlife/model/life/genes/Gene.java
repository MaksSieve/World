package cyberlife.model.life.genes;

import cyberlife.model.life.Animal;

import java.util.Random;

public class Gene{

    protected Animal host;
    private Random random = new Random();
    private int n;

    public Gene(Animal host){
        this.host = host;
        n = random.nextInt(10)+6;
    };

    public void action(){
        host.getGenome().increasePointer(n);
        try {
            if (host.k < 5) {
                host.k++;
                host.getGenome().getNext().action();
            }
        }catch (StackOverflowError e){
            e.printStackTrace();
        }
    }

    public int getN() {
        return n;
    }
}
