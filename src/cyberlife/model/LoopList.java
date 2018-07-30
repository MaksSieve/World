package cyberlife.model;

import java.util.ArrayList;

public class LoopList<T> extends ArrayList<T> {

    private int pointer = 0;
    private int size = 0;

    public T getNext(){
        T item = get(pointer);
        increasePointer();
        return item;
    }

    private void increasePointer(){
        pointer = (pointer < size-1)?pointer++:0;
    }

    @Override
    public boolean add(T t) {
        size++;
        return super.add(t);
    }
}
