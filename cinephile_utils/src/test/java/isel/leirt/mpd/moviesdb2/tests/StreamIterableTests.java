package isel.leirt.mpd.moviesdb2.tests;

import isel.mpd.queries.StreamIterable;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class StreamIterableTests {
    private List<Character> chars(String s) {
        var l = new ArrayList<Character>();
        for(int i=0; i< s.length(); ++i)
            l.add(s.charAt(i));
        return l;
    }

    private int countOccurrences(
            List<List<String>> words,
            char letter
    ) {
        return StreamIterable.from(words)
                .flatMap(l -> l)
                .flatMap(s -> chars(s))
                .filter(    c -> c == letter)
                .count();
    }

    @Test
    public void countOcurrencesTest() {
        List<List<String>> data = List.of(
                List.of( "abc" , "bd"),
                List.of(),
                List.of("123a", "bda")
        );

        int expected = 3;

        int res = countOccurrences(data, 'b');

        assertEquals(expected, res);
    }

    @Test
    public void ofTest() {
        var res = StreamIterable.of("a", "b", "c").toList();
        var expected = List.of("a", "b", "c");
        assertEquals(expected, res);
    }

    @Test
    public void skipTest() {
        var res = StreamIterable.of("a", "b", "c")
                .skip(2)
                .toList();

        var expected = List.of("c");
        assertEquals(expected, res);
    }

    @Test
    public void takeWhileTest() {
        var res = StreamIterable.of("a", "a", "b", "c")
                .takeWhile(p -> p.equals("a"))
                .toList();

        var expected = List.of("a", "a");
        assertEquals(expected, res);
    }

    @Test
    public void maxTest() {
        var res = StreamIterable.of(1, 2, 3, 2 ,1)
                .max(Comparator.naturalOrder());

        var expected = Optional.of(3);
        assertEquals(expected, res);
    }

    @Test
    public void firstTest() {
        var res = StreamIterable.of(1, 2, 3)
                .first(Comparator.naturalOrder());

        var expected = Optional.of(1);
        assertEquals(expected, res);
    }
}

