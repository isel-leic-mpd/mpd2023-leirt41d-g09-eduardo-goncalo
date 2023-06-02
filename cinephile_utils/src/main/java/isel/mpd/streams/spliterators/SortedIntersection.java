package isel.mpd.streams.spliterators;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class SortedIntersection<T> extends Spliterators.AbstractSpliterator<T>  {
    private final Spliterator<T> split1;
    private final Spliterator<T> split2;
    private final Comparator<T> cmp;
    private T next1;
    private T next2;

    public SortedIntersection(Comparator<T> cmp, Spliterator<T> split1, Spliterator <T> split2) {
        super(split1.estimateSize() + split2.estimateSize(), 0);
        this.split1 = split1;
        this.split2 = split2;
        this.cmp = cmp;
    }

    public SortedIntersection(Comparator<T> cmp, Stream<T> s1, Stream <T> s2) {
        this(cmp, s1.spliterator(), s2.spliterator());
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        while (split1.tryAdvance(n -> next1 = n) && split2.tryAdvance(n -> next2 = n)) {
            while (cmp.compare(next1, next2) < 0 && split1.tryAdvance(n -> next1 = n));
            while (cmp.compare(next1, next2) > 0 && split2.tryAdvance(n -> next2 = n));
            if (cmp.compare(next1, next2) == 0) action.accept(next1);
        }
        return false;
    }
}