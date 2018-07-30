package cyberlife.model.life.genes;

import cyberlife.model.life.Animal;

public class Gene{

    protected Animal host;

    public Gene(Animal host){
        this.host = host;
    };

    public void action(){};

}
