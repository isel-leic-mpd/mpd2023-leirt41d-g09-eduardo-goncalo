package isel.mpd.queries;

import isel.mpd.queries.iterators.*;
import isel.mpd.queries.iterators.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

public interface StreamIterable<T> extends Iterable<T>  {
    //Iterator<T> iterator();

    // operações factory
    static <T> StreamIterable<T> from(Iterable<T> src) {
        return () -> src.iterator();
    }

    static <T> StreamIterable<T> iterate(T seed, Function<T,T> producer) {
        return () -> new Generator(seed, producer);
    }

    static StreamIterable<Integer> range(int li, int ls) {
        return () -> new Iterator<Integer>() {
            int next = li;

            @Override
            public boolean hasNext() {
                return next <= ls;
            }

            @Override
            public Integer next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return next++;
            }
        };
    }

    // operações intermédias
    default  StreamIterable<T> filter(Predicate<T> pred) {
        return () -> new FilterIterator<T>(this, pred);
    }

    default <U> StreamIterable<U> map(Function<T,U> mapper) {
        return () -> new MapIterator<>(this,mapper);
    }

    default <U> StreamIterable<U> flatMap(Function<T,Iterable<U>> mapper) {
        return () -> new FlatMapIterator<>(this,mapper);
    }


    default StreamIterable<T> limit(int lim) {
        return () -> new LimitIterator<>(this,lim);
    }


    // operações terminais

    default List<T> toList() {
        List<T> res =  new ArrayList<>();
        for(T e : this) res.add(e);
        return res;
    }

    default int count() {
        int c = 0;
        for(T e : this) c++;
        return c;
    }

}
