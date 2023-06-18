package more_streams;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class StreamUtils {
    static <T> Stream<T> filter(Stream<T> s, Predicate<T> test){
        return s.flatMap(n -> {
            if(test.test(n)){
                return Stream.of(n);
            }
            return Stream.of();
        });
    }
}
