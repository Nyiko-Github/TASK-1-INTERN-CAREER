package test;

import main.UserManager;
import main.DatabaseManager;
import main.User;
import main.Exam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserManagerTest {

    @InjectMocks
    private UserManager userManager;

    @Mock
    private DatabaseManager dbManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testLoginSuccessful() {
        // Setup
        String username = "testUser";
        String password = "testPass";
        User user = new User(username, password, "student");
        when(dbManager.getUser(username)).thenReturn(user);

        // Execute
        User result = userManager.login(username, password);

        // Verify
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(password, result.getPassword());
    }

    @Test
    public void testLoginFailure() {
        // Setup
        String username = "testUser";
        String password = "testPass";
        when(dbManager.getUser(username)).thenReturn(null);

        // Execute
        User result = userManager.login(username, password);

        // Verify
        assertNull(result);
    }

    @Test
    public void testRegisterUser() {
        // Setup
        String username = "testUser";
        String password = "testPass";
        String role = "student";
        User user = new User(username, password, role);
        when(dbManager.registerUser(user)).thenReturn(true);

        // Execute
        boolean result = userManager.registerUser(username, password, role);

        // Verify
        assertTrue(result);
    }

    @Test
    public void testCreateExam() {
        // Setup
        Exam exam = new Exam("Math", new ArrayList<>(), 60);
        doNothing().when(dbManager).saveExam(exam);

        // Execute
        userManager.createExam(exam);

        // Verify
        verify(dbManager, times(1)).saveExam(exam);
    }

    @Test
    public void testGetExams() {
        // Setup
        List<Exam> exams = new ArrayList<>();
        when(dbManager.getAllExams()).thenReturn(exams);

        // Execute
        List<Exam> result = userManager.getExams();

        // Verify
        assertNotNull(result);
        assertEquals(exams, result);
    }
}
