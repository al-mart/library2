package main.java.chapter6;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BiFunction;

public class Lambda {

    public Runnable myRunnerWithThis = () -> {
        System.out.printf(this.toString());
    };

    private BiFunction<String, String, Integer> func =
            (first, second) -> first.length() - second.length();

    public static void main(String[] args) {
        Runnable myRunner = System.out::println;
        myRunner.run();
        // var timer = new Timer(1000, myRunner);
        // timer.start();
        // JOptionPane.showMessageDialog(null, "Quit program?");
    }

    public String[] testTheLambda(String[] array) {
        Comparator<String> myLengthComparator = (first, second) -> {
            return first.length() - second.length();
        };
        Arrays.sort(array, myLengthComparator);
        return array;
    }
}
