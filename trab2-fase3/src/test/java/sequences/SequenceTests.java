package sequences;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SequenceTests {
    @Test
    public void ofTest() {
        var res = Sequence.of(1, 2, 3).toList();
        var expected = List.of(1, 2, 3);

        assertEquals(res, expected);
    }

    @Test
    public void skipWhileTest() {
        var res = Sequence.of(1, 2, 3, 4, 5, 6, 7)
                .skipWhile(e -> e < 5)
                .toList();

        var expected = List.of(5, 6, 7);

        assertEquals(res, expected);
    }

    @Test
    public void minTest() {
        var res = Sequence.of(7, 3, 5)
                .min(Comparator.naturalOrder());

        var expected = Optional.of(3);

        assertEquals(res, expected);
    }
}
