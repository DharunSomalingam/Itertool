package studentstats;

import itertools.DoubleEndedIterator;
import studentapi.*;
import test.studentstats.MockStudentList;

/**
 * A (double ended) iterator over student records pulled from the student API.
 *
 * <p>This does not load the whole student list immediately, but rather queries the API ({@link
 * StudentList#getPage}) only as needed.
 */
public class StudentListIteratorEx implements DoubleEndedIterator<Student> {
    private StudentList list;
    private int retries;
    private int currentPage;
    private int studentIndexInPage;
    private Student[] currentPageStudents;

    /**
     * Construct an iterator over the given {@link StudentList} with the specified retry quota.
     *
     * @param list The API interface.
     * @param retries The number of times to retry a query after getting {@link
     *     QueryTimedOutException} before declaring the API unreachable and throwing an {@link
     *     ApiUnreachableException}.
     */
    public StudentListIteratorEx(StudentList list, int retries) {
        this.list = list;
        this.retries = retries;
        this.currentPage = 0;
        this.studentIndexInPage = 0;
        this.currentPageStudents = null;
    }

    /**
     * Construct an iterator over the given {@link StudentList} with a default retry quota of 3.
     *
     * @param list The API interface.
     */
    public StudentListIteratorEx(StudentList list) {
        this(list, 3);
    }

    @Override
    public boolean hasNext() {
        if (currentPageStudents == null || studentIndexInPage >= currentPageStudents.length) {
            if (fetchNextPage()) {
                studentIndexInPage = 0;
            } else {
                return false;
            }
        }System.out.println("Index"+studentIndexInPage+"Current"+currentPageStudents.length);
        return true;
    }

    @Override
    public Student next() {
        if (!hasNext()) {
            return null;
        }
        return currentPageStudents[studentIndexInPage++];
    }

    @Override
    public Student reverseNext() {
        return null;
    }

    private boolean fetchNextPage() {
        int attempts = 0;
        while (attempts < retries) {
            try {
                currentPageStudents = list.getPage(currentPage);
                System.out.println(currentPageStudents.length);
                if (currentPageStudents != null && currentPageStudents.length > 0) {
                    currentPage++;
                    return true;
                } else {
                    return false;
                }
            } catch (QueryTimedOutException e) {
                attempts++;
            }
        }
        throw new ApiUnreachableException();
    }
}
