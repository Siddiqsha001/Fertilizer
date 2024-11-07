import java.util.Scanner;
public class Display {
    private String name;
    private int id;
    private String department;
    public Display(String name, int id, String department) {
        this.name = name;
        this.id = id;
        this.department = department;
    }
    public void displayDetails() {
        System.out.println("Employee Name: " + name);
        System.out.println("Employee ID: " + id);
        System.out.println("Department: " + department);
    }
    public static void main(String[] args) {
        Display employee1 = new Display("Ram", 101, "HR");
        Display employee2 = new Display("Anu", 102, "Finance");
        employee1.displayDetails();
        System.out.println();
        employee2.displayDetails();
    }
}
