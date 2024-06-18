//23832333 DharunSomalingam
package itertools;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * A Predict iterator that filters elements based on a predicate.
 *
 * @param <T> the type of elements returned by this iterator
 */
public class Filter<T> implements Iterator<T>, Predicate<T> {
    private final Iterator<T> it;
    private final Predicate<T> predicate;
    private T predictor;
    private boolean predicted;

    /**
     * Constructs a Predict iterator that wraps another iterator and filters elements based on a predicate.
     *
     * @param it   the underlying iterator
     * @param predicate the predicate to filter elements
     */
    public Filter(Iterator<T> it, Predicate<T> predicate) {
        this.it = it;
        this.predicate = predicate;
        this.predictor = null;
        this.predicted = false;
    }

    /**
     * Tests if the given element satisfies the predicate.
     *
     * @param t the element to be tested
     * @return true if the element satisfies the predicate, otherwise false
     */
    @Override
    public boolean test(T t) {
        return predicate.test(t);
    }

    /**
     * Returns true if the iteration has more elements satisfying the predicate.
     *
     * @return true if the iteration has more elements satisfying the predicate
     */
    @Override
    public boolean hasNext() {
        if (!predicted) {
            predict();
            predicted = true;
        }
        return predictor != null;
    }

    /**
     * Returns the next element in the iteration that satisfies the predicate.
     *
     * @return the next element in the iteration that satisfies the predicate
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public T next() {
        if (hasNext()) {
            T result = predictor;
            predicted = false;
            predictor = null;
            return result;
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Predicts the next element that satisfies the predicate.
     */
    private void predict() {
        while (it.hasNext()) {
            T nextElement = it.next();
            if (test(nextElement)) {
                this.predictor = nextElement;
                return;
            }
        }
        this.predictor = null;
    }
}
