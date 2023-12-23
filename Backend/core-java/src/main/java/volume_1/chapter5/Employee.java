package chapter5;

public class Employee {
    private final float salary;
    public String name;

    Employee(float salary, String name){
        this.salary = salary;
        this.name = name;
    }

    protected float getSalary() {
        return salary;
    }
}
