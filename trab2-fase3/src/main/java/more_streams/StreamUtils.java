package more_streams;

import sequences.Sequence;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

    // converts all characters from the strings between 'start' and 'max' in a list to lower case.
    // string length must be greater than 10
    public static List<String> ex1Original(List<String> lines, int start, int max) {
        int i = start;
        List<String> result = new ArrayList<>();
        while(i - start <= max && i < lines.size() && lines.get(i).length() > 10) {
            result.add(lines.get(i++).toLowerCase());
        }
        return result;
    }

    public static List<String> ex1(List<String> lines, int start, int max) {
        return lines.stream()
                .skip(start)
                .limit(max + 1)
                .filter(line -> line.length() > 10)
                .map(String::toLowerCase)
                .toList();
    }

    // removes duplicate strings from a nested list and returns a sorted list of unique strings
    public static List<String> ex2Original(List<List<String>> text) {
        var result = new ArrayList<String>();
        for(var l : text) {
            for(var s : l) {
                if (!result.contains(s)) result.add(s);
            }
        }
        result.sort(Comparator.naturalOrder());
        return result;
    }

    public static List<String> ex2(List<List<String>> text) {
        return text.stream()
                .flatMap(List::stream)
                .distinct()
                .toList();
    }

    static <T> Sequence<T> SequencefromStream(Stream<T> stream) {
        var split = stream.spliterator();
        return split::tryAdvance;
    }

    static <T> Iterable<T> IterablefromStream(Stream<T> stream) {
        return stream::iterator;
    }
}
