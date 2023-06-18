package more_streams;

import org.junit.jupiter.api.Test;
import sequences.Sequence;

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
}
