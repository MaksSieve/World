package cyberlife.model.life.genes;

import cyberlife.model.life.Animal;

public class Gene{

    protected Animal host;


    public Gene(Animal host){
        this.host = host;
    };

    public void action(){
        host.getGenome().increasePointer(GeneTranslator.geneToInt(this));
        try {
            if (host.k < 5) {
                host.k++;
                host.getGenome().getNext().action();
            }
        }catch (StackOverflowError e){
            e.printStackTrace();
        }
    }

}
