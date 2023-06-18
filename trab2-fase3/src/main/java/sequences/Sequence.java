package sequences;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Sequence<T> {
    boolean tryAdvance(Consumer<T> action);

    // constuctor operations

    static <T> Sequence<T> empty() {
        return action -> false;
    }

    static <T> Sequence<T> of(T ... args) {
        int[] index = {0};
        return action -> {
            if (index[0] >= args.length) return false;
            action.accept(args[index[0]++]);
            return true;
        };
    }

    static <T> Sequence<T> from(Iterable<T> src) {
        return action -> {
            Iterator<T> it = src.iterator();
            if (!it.hasNext()) return false;
            action.accept(it.next());
            return it.hasNext();
        };
    }

    // intermediate operations

    default <U> Sequence<U> map(Function<T,U> mapper) {
        return action -> tryAdvance(
                t -> action.accept(mapper.apply(t)));
    }

    default Sequence<T> concat(Sequence<T> other) {
        return action -> {
            if (tryAdvance(action)) return true;
            return (other.tryAdvance(action));
        };
    }

    default Sequence<T> filter(Predicate<T> pred) {
        return action -> {
            boolean[] done = {false};
            while (!done[0] && tryAdvance(t -> {
                if (pred.test(t)) {
                    action.accept(t);
                    done[0] = true;
                }

            }));
            return done[0];
        };
    }

    default Sequence<T> concat2(Sequence<T> other) {
        return action ->
                tryAdvance(action) || other.tryAdvance(action);
    }

    default Sequence<T> skipWhile(Predicate<T> pred) {
        return action -> {
            boolean[] skip = { true };
            return tryAdvance(element -> {
                if (skip[0] && pred.test(element))
                    return;
                else {
                    skip[0] = false;
                    action.accept(element);
                }
            });
        };
    }

    // terminal operation

    default <U> U reduce(U initial, BiFunction<U, T, U> accum) {
        U[] res = (U[]) new Object[1];
        Sequence<T> seq = this;
        while (seq.tryAdvance((element) -> res[0] = accum.apply(initial, element)));
        return res[0];
    }

    default Optional<T> min(Comparator<T> cmp) {
        T[] min = (T[]) new Object[1];
        Sequence<T> seq = this;
        seq.tryAdvance(element -> min[0] = element);
        while (seq.tryAdvance(element -> {
            if (cmp.compare(element, min[0]) < 0) {
                min[0] = element;
            }
        }));
        return Optional.ofNullable(min[0]);
    }

    default void forEach(Consumer<T> action) {
        while (tryAdvance(action)) {}
    }

    default List<T> toList() {
        List<T> res =  new ArrayList<>();
        forEach(res::add);
        return res;
    }

    default int count() {
        int c = 0;
        while (tryAdvance(__ -> {})) c++;
        return c;
    }
}
