package test;
import main.PasswordUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class PasswordUtilsTest {

    @Test
    public void testHashPassword() {
        // Test data
        String password = "mySecretPassword";
        String expectedHash = "f5e7a5d5e7b8a3f16bb95d8d8d27c67b3ed8ad6f9adfa8ff871da0ec3d68adf"; // Adjust to match the correct hash

        // Perform hashing
        String actualHash = main.PasswordUtils.hashPassword(password);

        // Assert the hash matches the expected value
        assertEquals(expectedHash, actualHash, "The password hash should match the expected value.");
    }

    @Test
    public void testHashPasswordWithEmptyString() {
        // Test data
        String password = "";
        String expectedHash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"; // SHA-256 hash of empty string

        // Perform hashing
        String actualHash = PasswordUtils.hashPassword(password);

        // Assert the hash matches the expected value
        assertEquals(expectedHash, actualHash, "The password hash for an empty string should match the expected value.");
    }
}
