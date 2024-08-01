package test;

import main.Teacher;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TeacherTest {

    @Test
    void testTeacherInitialization() {
        // Test data
        String username = "teacherUser";
        String password = "teacherPass";

        // Create a new Teacher object
        Teacher teacher = new Teacher(username, password);

        // Validate that the Teacher object is correctly initialized
        assertNotNull(teacher, "Teacher object should not be null");
        assertEquals(username, teacher.getUsername(), "Username should match the input value");
        assertEquals(password, teacher.getPassword(), "Password should match the input value");
        assertEquals("teacher", teacher.getRole(), "Role should be 'teacher'");
    }
}
