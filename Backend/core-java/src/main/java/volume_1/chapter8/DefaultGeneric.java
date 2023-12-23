package chapter8;

import java.io.Serializable;
import java.time.*;

public class DefaultGeneric {
    public static void main(String[] args) {
        LocalDate[] birthdays =
                {
                        LocalDate.of(1906, 12, 9), // G. Hopper
                        LocalDate.of(1815, 12, 10), // A. Lovelace
                        LocalDate.of(1903, 12, 3), // J. von Neumann
                        LocalDate.of(1910, 6, 22), // K. Zuse
                };
        Pair<LocalDate> mm = ArrayAlg.minmax(birthdays);
        System.out.println("min = " + mm.getFirst());
        System.out.println("max = " + mm.getSecond());
    }
}

class Pair<T> {
    private T first;
    private T second;

    public Pair() {
        first = null;
        second = null;
    }

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    public void setFirst(T newValue) {
        first = newValue;
    }

    public void setSecond(T newValue) {
        second = newValue;
    }
}
 // Raw type of Pair
/*public class Pair
{
    private Object first;
    private Object second;

    public Pair(Object first, Object second)
    {
        this.first = first;
        this.second = second;
    }

    public Object getFirst() { return first; }
    public Object getSecond() { return second; }

    public void setFirst(Object newValue) { first = newValue; }
    public void setSecond(Object newValue) { second = newValue; }
}*/

class Interval<T extends Comparable & Serializable> implements Serializable
{
    private T lower;
    private T upper;
    public Interval(T first, T second)
    {
        if (first.compareTo(second) <= 0) { lower = first; upper = second; }
        else { lower = second; upper = first; }
    }
}
// Raw type for Interval

/*
public class Interval implements Serializable
{
    private Comparable lower;
    private Comparable upper;
    public Interval(Comparable first, Comparable second) { . . . }
}
*/

class ArrayAlg {
    /**
     * Gets the minimum and maximum of an array of objects of type T.
     *
     * @param a an array of objects of type T
     * @return a pair with the min and max values, or null if a is null or empty
     */
    public static <T extends Comparable & Serializable> Pair<T> minmax(T[] a) {
        if (a == null || a.length == 0) return null;
        T min = a[0];
        T max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (min.compareTo(a[i]) > 0) min = a[i];
            if (max.compareTo(a[i]) < 0) max = a[i];
        }
        return new Pair<>(min, max);
    }
}
