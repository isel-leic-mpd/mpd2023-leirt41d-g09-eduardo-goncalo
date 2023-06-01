package isel.mpd.streams.spliterators;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.Stream;

public class SortedIntersection<T> extends Spliterators.AbstractIntSpliterator<T> {

    private final Spliterator<T> seq1;
    private final Spliterator<T> seq2;//spliterators
    private  Comparator<T> cmp;

    public SortedIntersection(Spliterator<T> seq1, Spliterator <T> seq2){
        super(Integer.MAX_VALUE,0);
        this.seq1 = seq1;
        this.seq2 = seq2;
    }

    public SortedIntersection(Comparator<T> cmp, Stream<T> seq1, Stream <T> seq2){
        this(seq1.spliterator(), seq2.spliterator());
        this.cmp = cmp;
    }

    private T getElement(Stream<T> seq, int index){
        try {
            return seq.toList().get(index);
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public boolean tryAdvance(Consumer<? super Integer> action) {
        var index1 = 0;
        var index2 = 0;
        T a1 = getElement(seq1,index1);
        T a2= getElement(seq2,index2);
        if(a1 == a2){
        action.accept(a1);}
        return true;
    }
}