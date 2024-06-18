//23832333 DharunSomalingam
package studentstats;

import itertools.DoubleEndedIterator;
import java.util.NoSuchElementException;
import studentapi.*;

/**
 * A double-ended iterator over student records pulled from the student API.
 *
 * <p>This does not load the whole student list immediately, but rather queries the API ({@link
 * StudentList#getPage}) only as needed.
 */
public class StudentListIterator implements DoubleEndedIterator<Student> {
    private final StudentList list;            // Reference to the student list API
    private final int retries;                 // Number of retry attempts for API calls
    private int currentPage;         // Current page index for forward iteration
    private final int totalPages;              // Total number of pages available
    private int currentPageLength;   // Current position within the current page for forward iteration

    private int currentReversePage;      // Current page index for reverse iteration
    private int reversePageLength = -1;  // Current position within the current page for reverse iteration
    private Student[] currentPageStudents; // Array holding students of the current page for forward iteration
    private Student[] reversePageStudents; // Array holding students of the current page for reverse iteration
    private int finder;              // Position of the next student to fetch

    /**
     * Constructs a StudentListIterator with a specified number of retries for API calls.
     *
     * @param list the StudentList instance to iterate over
     * @param retries the number of retries for API calls
     */
    public StudentListIterator(StudentList list, int retries) {
        this.list = list;
        this.retries = retries;
        this.totalPages = list.getNumPages();
        this.currentReversePage = totalPages - 1;
    }

    /**
     * Constructs a StudentListIterator with a default number of retries (3) for API calls.
     *
     * @param list the StudentList instance to iterate over
     */
    public StudentListIterator(StudentList list) {
        this(list, 3);
    }

    /**
     * Checks if there are more students to fetch in the forward direction.
     *
     * @return true if there are more students, false otherwise
     */
    public boolean hasNext() {
        return finder < list.getNumStudents();
    }

    /**
     * Returns the next student in the forward direction.
     *
     * @return the next student
     * @throws NoSuchElementException if there are no more students
     */
    public Student next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        if (currentPageStudents == null || currentPageLength >= currentPageStudents.length) {
            fetchNextPage();
        }
        Student student = currentPageStudents[currentPageLength++];
        finder++;
        return student;
    }

    /**
     * Returns the next student in the reverse direction.
     *
     * @return the next student in reverse
     * @throws NoSuchElementException if there are no more students
     */
    public Student reverseNext() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        if (reversePageStudents == null || reversePageLength < 0) {
            fetchPreviousPage();
        }
        Student student = reversePageStudents[reversePageLength--];
        finder++;
        return student;
    }

    /**
     * Fetches the next page of students from the API for forward iteration.
     *
     * @throws ApiUnreachableException if the API cannot be reached after the specified number of retries
     */
    private void fetchNextPage() {
        int attempts = 0;
        while (attempts < retries) {
            try {
                currentPageStudents = list.getPage(currentPage);
                currentPageLength = 0;
                currentPage++;
                return;
            } catch (QueryTimedOutException e) {
                attempts++;
            }
        }
        throw new ApiUnreachableException();
    }

    /**
     * Fetches the previous page of students from the API for reverse iteration.
     *
     * @throws ApiUnreachableException if the API cannot be reached after the specified number of retries
     */
    private void fetchPreviousPage() {
        int attempts = 0;
        while (attempts < retries) {
            try {
                reversePageStudents = list.getPage(currentReversePage);
                reversePageLength = reversePageStudents.length - 1;
                currentReversePage--;
                return;
            } catch (QueryTimedOutException e) {
                attempts++;
            }
        }
        throw new ApiUnreachableException();
    }
}
