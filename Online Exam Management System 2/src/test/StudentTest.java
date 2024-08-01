package test;

import main.Student;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class StudentTest {

    @Test
    public void testStudentCreation() {
        // Create a new student
        Student student = new Student("testUser", "testPass");

        // Check if the username is set correctly
        assertEquals("testUser", student.getUsername(), "Username should be 'testUser'");

        // Check if the password is set correctly
        assertEquals("testPass", student.getPassword(), "Password should be 'testPass'");

        // Check if the role is set correctly
        assertEquals("student", student.getRole(), "Role should be 'student'");
    }
}
