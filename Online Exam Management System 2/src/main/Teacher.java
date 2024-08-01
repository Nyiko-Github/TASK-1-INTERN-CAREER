package main;
/**
 * The type Teacher.
 */
public class Teacher extends User {
    /**
     * Instantiates a new Teacher.
     *
     * @param username the username
     * @param password the password
     */
    public Teacher(String username, String password) {
        super(username, password, "teacher");
    }
}
