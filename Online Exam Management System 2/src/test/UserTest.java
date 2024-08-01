package test;

import main.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void testGetUsername() {
        User user = new User("testUser", "testPassword", "testRole");
        assertEquals("testUser", user.getUsername(), "The username should be 'testUser'");
    }

    @Test
    void testGetPassword() {
        User user = new User("testUser", "testPassword", "testRole");
        assertEquals("testPassword", user.getPassword(), "The password should be 'testPassword'");
    }

    @Test
    void testGetRole() {
        User user = new User("testUser", "testPassword", "testRole");
        assertEquals("testRole", user.getRole(), "The role should be 'testRole'");
    }
}
