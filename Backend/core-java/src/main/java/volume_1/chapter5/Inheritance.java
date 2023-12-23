package chapter5;

public class Inheritance {
    public static void main(String[] args) {
        Employee manager = new Manager(10, "John", 3);
        System.out.println(manager.getSalary());
    }
}
