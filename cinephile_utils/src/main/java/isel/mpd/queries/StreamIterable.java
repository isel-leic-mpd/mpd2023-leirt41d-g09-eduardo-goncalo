package isel.mpd.queries;

import isel.mpd.queries.iterators.*;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public interface StreamIterable<T> extends Iterable<T>  {
    //Iterator<T> iterator();

    // operações factory
    static <T> StreamIterable<T> of(T... params) {
        return from(List.of(params));
    }

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
    default StreamIterable<T> skip(int n) {
        return filter(p -> toList().indexOf(p) > n - 1);
    }

    default StreamIterable<T> takeWhile(Predicate<T> pred) {
        return filter(pred);
    }

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
    default Optional<T> max(Comparator<T> cmp) {
        if (toList().isEmpty()) return Optional.empty();
        T max = toList().get(0);
        for (var t : this)
            if (cmp.compare(max, t) < 0) max = t;
        return Optional.of(max);
    }

    default Optional<T> first(Comparator<T> cmp) {
        if (toList().isEmpty()) return Optional.empty();
        T first = toList().get(0);
        return Optional.of(first);
    }

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
