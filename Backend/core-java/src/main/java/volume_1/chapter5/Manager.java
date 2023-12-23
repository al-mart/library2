package chapter5;

public class Manager extends Employee{
    public int numberOfEmployees;
    Manager(float salary, String name, int numberOfEmployees){
        super(salary, name);
        this.numberOfEmployees = numberOfEmployees;
    }

    @Override
    protected float getSalary() {
        return super.getSalary();
    }
}
