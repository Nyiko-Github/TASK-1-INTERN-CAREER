package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Database manager.
 */
public class DatabaseManager {
    private Connection connection;

    /**
     * Instantiates a new Database manager.
     */
    public DatabaseManager() {
        try {
            String url = "jdbc:mysql://localhost:3306/online_exam";
            String username = "root";
            String password = "Tr!ckle187K!wi,Apl3";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    /**
     * Gets the connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Username exists boolean.
     *
     * @param username the username
     * @return the boolean
     */
    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking username", e);
        }
        return false;
    }

    public boolean registerUser(User user) {
        if (usernameExists(user.getUsername())) {
            return false; // Username already exists
        }

        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving user", e);
        }
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    public User getUser(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String password = rs.getString("password");
                String role = rs.getString("role");
                if ("teacher".equalsIgnoreCase(role)) {
                    return new Teacher(username, password);
                } else {
                    return new User(username, password, role);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching user", e);
        }
        return null;
    }

    /**
     * Save exam.
     *
     * @param exam the exam
     */
    public void saveExam(Exam exam) {
        try {
            String examSQL = "INSERT INTO exams (title, duration) VALUES (?, ?)";
            try (PreparedStatement examStmt = connection.prepareStatement(examSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
                examStmt.setString(1, exam.getTitle());
                examStmt.setInt(2, exam.getDuration());
                examStmt.executeUpdate();

                ResultSet generatedKeys = examStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int examId = generatedKeys.getInt(1);

                    String questionSQL = "INSERT INTO questions (exam_id, question_text, correct_answer) VALUES (?, ?, ?)";
                    try (PreparedStatement questionStmt = connection.prepareStatement(questionSQL)) {
                        for (Question question : exam.getQuestions()) {
                            questionStmt.setInt(1, examId);
                            questionStmt.setString(2, question.getQuestionText());
                            questionStmt.setString(3, question.getCorrectAnswer());
                            questionStmt.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving exam", e);
        }
    }

    /**
     * Gets all exams.
     *
     * @return the all exams
     */
    public List<Exam> getAllExams() {
        List<Exam> exams = new ArrayList<>();
        String sql = "SELECT * FROM exams";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int examId = rs.getInt("id");
                String title = rs.getString("title");
                int duration = rs.getInt("duration");
                List<Question> questions = getQuestionsForExam(examId);
                exams.add(new Exam(title, questions, duration));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching exams", e);
        }
        return exams;
    }

    private List<Question> getQuestionsForExam(int examId) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE exam_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, examId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String questionText = rs.getString("question_text");
                String correctAnswer = rs.getString("correct_answer");
                questions.add(new Question(questionText, correctAnswer));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching questions", e);
        }
        return questions;
    }

    /**
     * Close connection.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error closing the database connection", e);
        }
    }
}
