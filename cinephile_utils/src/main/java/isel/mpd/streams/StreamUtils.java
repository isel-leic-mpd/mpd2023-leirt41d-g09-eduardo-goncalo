package isel.mpd.streams;

import isel.mpd.streams.spliterators.SortedIntersection;

import java.util.*;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {

	public static <T> Stream<T> sortedIntersection(Comparator cmp, Stream<T> s1, Stream<T> s2) {
		return StreamSupport.stream(new SortedIntersection<T>(cmp,s1,s2),false);
	}
}