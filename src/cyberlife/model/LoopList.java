package cyberlife.model;

import java.util.ArrayList;

public class LoopList<T> extends ArrayList<T> {

    private int pointer = 0;

    public T getNext(){
        T item = get(pointer);
        increasePointer();
        return item;
    }

    private void increasePointer(){
        if (pointer < this.size()-1)pointer++; else pointer = 0;
    }

    public void increasePointer(int n){
        if (pointer + n >= this.size())
            pointer = pointer+n-this.size();
        else
            pointer = pointer+n;
    }

    @Override
    public boolean add(T t) {
        if (this.size()>0) {
            T curr = this.get(pointer);
            super.add(pointer + 1, curr);
            super.set(pointer, t);
            increasePointer();
            increasePointer();
            return true;
        }else{
            return super.add(t);
        }

    }

    public int getPointer() {
        return pointer;
    }
}
