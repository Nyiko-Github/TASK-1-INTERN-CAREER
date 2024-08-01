package test;

import main.DatabaseManager;
import main.User;
import main.Teacher;
import main.Exam;
import main.Question;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DatabaseManagerTest {

    private DatabaseManager databaseManager;

    @BeforeAll
    void setUp() {
        databaseManager = new DatabaseManager();
    }

    @AfterAll
    void tearDown() {
        databaseManager.closeConnection();
    }

    @Test
    void testUsernameExists() {
        // Add a user to test against
        User testUser = new User("testuser", "password", "student");
        databaseManager.registerUser(testUser);

        // Test the usernameExists method
        assertTrue(databaseManager.usernameExists("testuser"));
        assertFalse(databaseManager.usernameExists("nonexistentuser"));
    }

    @Test
    void testRegisterUser() {
        User newUser = new User("newuser", "password", "student");

        assertTrue(databaseManager.registerUser(newUser));
        assertFalse(databaseManager.registerUser(newUser)); // Duplicate registration should fail

        // Clean up by deleting the user
        deleteUser("newuser");
    }

    @Test
    void testGetUser() {
        User testUser = new User("testuser2", "password", "student");
        databaseManager.registerUser(testUser);

        User retrievedUser = databaseManager.getUser("testuser2");

        assertNotNull(retrievedUser);
        assertEquals("testuser2", retrievedUser.getUsername());
        assertEquals("password", retrievedUser.getPassword());
        assertEquals("student", retrievedUser.getRole());

        // Clean up by deleting the user
        deleteUser("testuser2");
    }

    @Test
    void testSaveExam() {
        Question question1 = new Question("What is 2+2?", "4");
        Question question2 = new Question("What is the capital of France?", "Paris");
        Exam exam = new Exam("Sample Exam", List.of(question1, question2), 30);

        databaseManager.saveExam(exam);

        List<Exam> exams = databaseManager.getAllExams();
        assertTrue(exams.size() > 0);
        boolean found = false;
        for (Exam ex : exams) {
            if (ex.getTitle().equals("Sample Exam")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    void testGetAllExams() {
        List<Exam> exams = databaseManager.getAllExams();
        assertNotNull(exams);
        assertTrue(exams.size() >= 0);
    }

    private void deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement stmt = databaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }
}
