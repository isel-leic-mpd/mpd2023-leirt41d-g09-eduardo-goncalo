package more_streams;

import org.junit.jupiter.api.Test;
import sequences.Sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamUtilTests {
    @Test
    public void filter_test() {
        var ex = Stream.of(2,4,6,8,3,1,-1,-2);
        var res = StreamUtils.filter(ex, n -> n %2 != 0).toList();
        var expected = List.of(3,1,-1);
        assertEquals(res, expected);


        var ex2 = Stream.of("Tar", "MPD", "PG", "Tele", "SO", "FPR" );
        var res2 = StreamUtils.filter(ex2, n -> n.length() > 3 );
        var expected2 = List.of("Tele");
        assertEquals(res2.toList(), expected2);
    }

    @Test
    public void ex1Test() {
        List<String> l = new ArrayList<>();
        l.add("This is LINE 1");
        l.add("This is LINE 2");
        l.add("This is LINE 3");
        l.add("This is LINE 4");
        l.add("This is LINE 5");

        var expected = StreamUtils.ex1Original(l, 1, 3);
        var res = StreamUtils.ex1(l, 1, 3);

        assertEquals(expected, res);
    }

    @Test
    public void ex2Test() {
        List<String> l1 = new ArrayList<>();
        l1.add("This is line 1");
        l1.add("This is line 2");
        l1.add("This is line 3");
        l1.add("This is line 4");
        l1.add("This is line 5");

        List<String> l2 = new ArrayList<>();
        l2.add("This is line 3"); // duplicate
        l2.add("This is line 4"); // duplicate
        l2.add("This is line 5"); // duplicate
        l2.add("This is line 6");
        l2.add("This is line 7");

        List<List<String>> listList = new ArrayList<>();
        listList.add(l1);
        listList.add(l2);

        var expected = StreamUtils.ex2Original(listList);
        var res = StreamUtils.ex2(listList);

        assertEquals(expected, res);
    }
}
