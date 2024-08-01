package main;

/**
 * The type Student.
 */
public class Student extends User {
    /**
     * Instantiates a new Student.
     *
     * @param username the username
     * @param password the password
     */
    public Student(String username, String password) {
        super(username, password, "student");
    }
}
