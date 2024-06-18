package itertools;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.NoSuchElementException;

/**
 * A Zipper iterator that iterates over two collections simultaneously and applies a function to their elements.
 *
 * @param <T> the type of elements in the first iterator
 * @param <U> the type of elements in the second iterator
 * @param <R> the type of result elements
 */
public class Zipper<T, U, R> implements Iterator<R>, BiFunction<T, U, R> {
    private final Iterator<T> iteratorT; // Iterator for the first collection
    private final Iterator<U> iteratorU; // Iterator for the second collection
    private final BiFunction<T, U, R> function; // Function to apply to elements

    /**
     * Constructs a Zipper iterator that iterates over two collections and applies a function to their elements.
     *
     * @param iteratorT the iterator for the first collection
     * @param iteratorU the iterator for the second collection
     * @param function  the function to apply to elements from both collections
     */
    public Zipper(Iterator<T> iteratorT, Iterator<U> iteratorU, BiFunction<T, U, R> function) {
        this.iteratorT = iteratorT;
        this.iteratorU = iteratorU;
        this.function = function;
    }

    /**
     * Applies the function to the given elements from both collections.
     *
     * @param t the element from the first collection
     * @param u the element from the second collection
     * @return the result of applying the function to the elements
     */
    @Override
    public R apply(T t, U u) {
        return function.apply(t, u);
    }

    /**
     * Returns true if both iterators have more elements.
     *
     * @return true if both iterators have more elements
     */
    @Override
    public boolean hasNext() {
        return iteratorT.hasNext() && iteratorU.hasNext();
    }

    /**
     * Returns the result of applying the function to the next elements from both collections.
     *
     * @return the result of applying the function to the next elements
     */
    @Override
    public R next() {
        if (hasNext()) {
            T nextT = iteratorT.next();
            U nextU = iteratorU.next();
            return apply(nextT, nextU);
        } else {
            throw new NoSuchElementException();
        }
    }
}
