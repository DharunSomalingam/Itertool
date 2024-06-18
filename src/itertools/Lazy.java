//23832333 DharunSomalingam

package itertools;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A Lazy iterator that iterates over a specified number of elements from another iterator.
 *
 * @param <T> The type of elements in the iterator.
 */
public class Lazy<T> implements Iterator<T> {
    // The given iterator
    private final Iterator<T> it;
    // The maximum number of elements to iterate
    private final int count;
    // The current count in the iteration
    private int current;

    /**
     * Constructs a Lazy iterator that limits the number of elements to iterate.
     *
     * @param it    the underlying iterator
     * @param count the maximum number of elements to iterate
     */
    public Lazy(Iterator<T> it, int count) {
        this.it = it;
        this.count = count;
    }

    /**
     * Returns true if the iteration has more elements.
     *
     * @return true if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return current < count && it.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public T next() {
        if (hasNext()) {
            current++;
            return it.next();
        } else {
            throw new NoSuchElementException();
        }
    }
}
