package main;

import java.util.List;

/**
 * The type User manager.
 */
public class UserManager {
    private DatabaseManager dbManager;

    /**
     * Instantiates a new User manager.
     */
    public UserManager() {
        dbManager = new DatabaseManager();
    }

    /**
     * Login user.
     *
     * @param username the username
     * @param password the password
     * @return the user
     */
    public User login(String username, String password) {
        User user = dbManager.getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * Register user boolean.
     *
     * @param username the username
     * @param password the password
     * @param role     the role
     * @return the boolean
     */
    public boolean registerUser(String username, String password, String role) {
        User user = new User(username, password, role);
        return dbManager.registerUser(user);
    }

    /**
     * Create exam.
     *
     * @param exam the exam
     */
    public void createExam(Exam exam) {
        dbManager.saveExam(exam);
    }

    /**
     * Gets exams.
     *
     * @return the exams
     */
    public List<Exam> getExams() {
        return dbManager.getAllExams();
    }
}
