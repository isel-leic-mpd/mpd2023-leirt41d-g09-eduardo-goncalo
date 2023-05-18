package isel.mpd.queries.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LimitIterator<T> implements Iterator<T> {
    private final Iterator<T> srcIt;
    private int remaining;

    public LimitIterator(Iterable<T> src, int lim) {
        this.srcIt = src.iterator();
        this.remaining = lim;
    }

    @Override
    public boolean hasNext() {
        return remaining > 0 && srcIt.hasNext();
    }

    @Override
    public T next() {
        if (!hasNext()) throw new NoSuchElementException();
        T result = srcIt.next();
        remaining--;
        return result;
    }
}
