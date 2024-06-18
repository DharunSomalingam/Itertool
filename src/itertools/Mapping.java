//23832333 DharunSomalingam

package itertools;

import java.util.Iterator;
import java.util.function.Function;
import java.util.NoSuchElementException;

/**
 * A Mapping iterator that applies a function to each element of an underlying iterator.
 *
 * @param <T> the type of input elements
 * @param <R> the type of output elements
 */
public class Mapping<T, R> implements Iterator<R>, Function<T, R> {
    private final Iterator<T> iterator;
    private final Function<T, R> function;

    /**
     * Constructs a Mapping iterator that applies a function to each element of another iterator.
     *
     * @param iterator the underlying iterator
     * @param function the function to apply to each element
     */
    public Mapping(Iterator<T> iterator, Function<T, R> function) {
        this.iterator = iterator;
        this.function = function;
    }

    /**
     * Returns true if the iteration has more elements.
     *
     * @return true if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * Returns the next element in the iteration after applying the function.
     *
     * @return the next element in the iteration after applying the function
     */
    @Override
    public R next() {
        if (hasNext()) {
            T element = iterator.next();
            return apply(element);
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Applies the function to the given element.
     *
     * @param t the element to apply the function to
     * @return the result of applying the function to the element
     */
    @Override
    public R apply(T t) {
        return function.apply(t);
    }
}
