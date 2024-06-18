//23832333 DharunSomalingam

package itertools;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A reverse iterator that iterates over elements from a DoubleEndedIterator in reverse order.
 *
 * @param <T> the type of elements returned by this iterator
 */
public class Reverse<T> implements Iterator<T> {
    private final DoubleEndedIterator<T> it;

    /**
     * Constructs a Reverse iterator that  a DoubleEndedIterator.
     *
     * @param it the DoubleEndedIterator to iterate over in reverse order
     */
    public Reverse(DoubleEndedIterator<T> it) {
        this.it = it;
    }

    /**
     * Returns true if the iteration has more elements in reverse order.
     *
     * @return true if the iteration has more elements in reverse order
     */
    @Override
    public boolean hasNext() {

        return it.hasNext();
    }

    /**
     * Returns the next element in the iteration in reverse order.
     *
     * @return the next element in the iteration in reverse order
     * @throws NoSuchElementException the iteration has no more elements
     */
    @Override
    public T next() {
        if (hasNext()) {
            return it.reverseNext();
        } else {
            throw new NoSuchElementException();
        }
    }
}

