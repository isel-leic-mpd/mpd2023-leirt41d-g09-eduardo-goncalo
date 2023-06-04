package isel.mpd.streams;

import isel.mpd.streams.spliterators.SortedIntersection;

import java.util.*;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {
	public static <T> Stream<T> sortedIntersection(Comparator<T> cmp, Stream<T> s1, Stream<T> s2) {
		return StreamSupport.stream(new SortedIntersection<>(cmp, s1, s2), false);
	}

	@SafeVarargs
	public static <T> Stream<T> sortedIntersection(Comparator<T> cmp, Stream<T>... streams) {
		Stream<T> res = streams[0];
		for (int i = 1; i < streams.length; ++i) {
			res = res.toList().stream(); // Regenerate 'res'
			res = sortedIntersection(cmp, res, streams[i]);
		}
		return res;
	}
}
