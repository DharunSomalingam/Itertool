//23832333 DharunSomalingam
package itertools;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.NoSuchElementException;

/**
 * A Reduced iterator that iteratively applies a binary function to the elements of an iterator, reducing them to a single value.
 *
 * @param <T> the type of elements in the iterator
 * @param <R> the type of the result
 */
public class Reduced<T, R> implements Iterator<R>, BiFunction<R, T, R> {
    private final Iterator<T> iterator;
    private final BiFunction<R, T, R> function;
    private final R initialResult;

    /**
     * Constructs a Reduced iterator that applies a binary function to the elements of an iterator, reducing them to a single value.
     *
     * @param iterator       the iterator containing the elements
     * @param initialResult  the initial value of the result
     * @param function       the binary function to apply
     */
    public Reduced(Iterator<T> iterator, R initialResult, BiFunction<R, T, R> function) {
        this.iterator = iterator;
        this.initialResult = initialResult;
        this.function = function;
    }

    /**
     * Applies the binary function to the given result and element.
     *
     * @param result the current result
     * @param t      the next element
     * @return the updated result after applying the function
     */
    @Override
    public R apply(R result, T t) {
        return function.apply(result, t);
    }

    /**
     * Returns true if the iterator has more elements.
     *
     * @return true if the iterator has more elements
     */
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * Returns the reduced result after applying the function iteratively to all elements.
     *
     * @return the reduced result
     */
    @Override
    public R next() {
        if (hasNext()) {
            R result = initialResult;
            while (iterator.hasNext()) {
                T next = iterator.next();
                result = apply(result, next);
            }
            return result;
        }
            throw new NoSuchElementException();
    }
}
