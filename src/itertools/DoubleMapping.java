//23832333 DharunSomalingam

package itertools;


import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * A DoubleMapping iterator that applies a function to each element of a DoubleEndedIterator.
 *
 * @param <T> the type of input elements
 * @param <R> the type of output elements
 */
public class DoubleMapping<T, R> implements DoubleEndedIterator<R>, Function<T, R> {
    private final DoubleEndedIterator<T> iterator;
    private final Function<T, R> function;

    /**
     * Constructs a DoubleMapping iterator that applies a function to each element of another DoubleEndedIterator.
     *
     * @param iterator the underlying DoubleEndedIterator
     * @param function the function to apply to each element
     */
    public DoubleMapping(DoubleEndedIterator<T> iterator, Function<T, R> function) {
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
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public R next() {
        if (hasNext()) {
            T element = iterator.next();
            return function.apply(element);
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

    /**
     * Returns the previous element in the iteration after applying the function.
     *
     * @return the previous element in the iteration after applying the function
     * @throws NoSuchElementException if the iteration has no previous elements
     */
    @Override
    public R reverseNext() {
        if (iterator.hasNext()) {
            T element = iterator.reverseNext();
            return function.apply(element);
        } else {
            throw new NoSuchElementException();
        }
    }
}
