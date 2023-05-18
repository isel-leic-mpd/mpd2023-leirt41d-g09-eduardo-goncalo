package isel.mpd.queries.iterators;

import java.util.Iterator;
import java.util.function.Function;

public class Generator<T>  implements Iterator<T> {
    private T next;
    private  Function<T,T> producer;

    public Generator(T seed, Function<T,T> producer) {
        this.next = seed;
        this.producer = producer;
    }
    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public T next() {
        T res = next;
        next = producer.apply(next);
        return  res;
    }
}
