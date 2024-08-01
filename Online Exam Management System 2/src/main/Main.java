package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Pos;


/**
 * The type Main.
 */
public class Main extends Application {
    private UserManager userManager = new UserManager();
    private Stack<Scene> sceneHistory = new Stack<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Online Examination System");

        // Create login and registration forms
        Scene loginScene = createLoginScene(primaryStage);
        Scene registrationScene = createRegistrationScene(primaryStage, loginScene);

        // Set initial scene
        primaryStage.setScene(loginScene);
        primaryStage.setWidth(primaryStage.getWidth() / 2);
        primaryStage.setHeight(primaryStage.getHeight() / 2);
        primaryStage.show();
    }

    private Scene createLoginScene(Stage primaryStage) {
        GridPane grid = createGridPane();

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            User user = userManager.login(username, password);
            if (user != null) {
                showMessage("Login successful!");
                sceneHistory.push(primaryStage.getScene());
                // Clear the input fields for security reasons
                usernameField.setText("");
                passwordField.setText("");
                // Navigate to user-specific interface based on role
                if ("student".equalsIgnoreCase(user.getRole())) {
                    primaryStage.setScene(createStudentScene(primaryStage));
                } else if ("teacher".equalsIgnoreCase(user.getRole())) {
                    primaryStage.setScene(createTeacherScene(primaryStage));
                }
            } else {
                showMessage("Invalid credentials!");
            }
        });

        Button switchToRegisterButton = new Button("Register");
        switchToRegisterButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        switchToRegisterButton.setOnAction(e -> {
            sceneHistory.push(primaryStage.getScene());
            primaryStage.setScene(createRegistrationScene(primaryStage, primaryStage.getScene()));
            // Clear the input fields for security reasons
            usernameField.setText("");
            passwordField.setText("");
        });

        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);
        grid.add(switchToRegisterButton, 1, 3);

        return new Scene(grid, 800, 400);
    }

    private Scene createRegistrationScene(Stage primaryStage, Scene loginScene) {
        GridPane grid = createGridPane();

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Label roleLabel = new Label("Role (student/teacher):");
        TextField roleField = new TextField();

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #FFFF00; -fx-text-fill: #000000;"); // Yellow background, black text
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleField.getText();

            boolean registrationSuccess = userManager.registerUser(username, password, role);
            if (registrationSuccess) {
                showMessage("Registration successful!");
                primaryStage.setScene(loginScene);
            } else {
                showMessage("Registration failed: Username already exists. Please choose a different username.");
            }
        });

        Button switchToLoginButton = new Button("Login");
        switchToLoginButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        switchToLoginButton.setOnAction(e -> primaryStage.setScene(loginScene));

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            if (!sceneHistory.isEmpty()) {
                primaryStage.setScene(sceneHistory.pop());
            }
        });

        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(roleLabel, 0, 2);
        grid.add(roleField, 1, 2);
        grid.add(registerButton, 1, 3);
        grid.add(switchToLoginButton, 1, 4);
        grid.add(backButton, 1, 5);

        return new Scene(grid, 800, 400);
    }

    private Scene createStudentScene(Stage primaryStage) {
        GridPane grid = createGridPane();

        Label welcomeLabel = new Label("Welcome, Student!");

        Button takeExamButton = new Button("Take Exam");
        takeExamButton.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
        takeExamButton.setOnAction(e -> {
            sceneHistory.push(primaryStage.getScene());
            primaryStage.setScene(createExamSelectionScene(primaryStage));
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            if (!sceneHistory.isEmpty()) {
                primaryStage.setScene(sceneHistory.pop());
            }
        });

        grid.add(welcomeLabel, 0, 0);
        grid.add(takeExamButton, 0, 1);
        grid.add(backButton, 0, 2);

        return new Scene(grid, 800, 400);
    }

    private Scene createTeacherScene(Stage primaryStage) {
        GridPane grid = createGridPane();

        Label welcomeLabel = new Label("Welcome, Teacher!");

        Button createExamButton = new Button("Create Exam");
        createExamButton.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
        createExamButton.setOnAction(e -> {
            sceneHistory.push(primaryStage.getScene());
            primaryStage.setScene(createExamCreationScene(primaryStage));
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            if (!sceneHistory.isEmpty()) {
                primaryStage.setScene(sceneHistory.pop());
            }
        });

        grid.add(welcomeLabel, 0, 0);
        grid.add(createExamButton, 0, 1);
        grid.add(backButton, 0, 2);

        return new Scene(grid, 800, 400);
    }

    private Scene createExamCreationScene(Stage primaryStage) {
        GridPane grid = createGridPane();

        Label titleLabel = new Label("Exam Title:");
        TextField titleField = new TextField();
        Label durationLabel = new Label("Duration (minutes):");
        TextField durationField = new TextField();

        List<Question> questions = new ArrayList<>();
        Label questionLabel = new Label("Question:");
        TextField questionField = new TextField();
        Label answerLabel = new Label("Answer:");
        TextField answerField = new TextField();

        Button addQuestionButton = new Button("Add Question");
        addQuestionButton.setStyle("-fx-background-color: skyblue; -fx-text-fill: black;");
        addQuestionButton.setOnAction(e -> {
            String questionText = questionField.getText();
            String correctAnswer = answerField.getText();
            questions.add(new Question(questionText, correctAnswer));
            questionField.clear();
            answerField.clear();
        });

        Button submitExamButton = new Button("Submit Exam");
        submitExamButton.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
        submitExamButton.setOnAction(e -> {
            String title = titleField.getText();
            try {
                int duration = Integer.parseInt(durationField.getText());
                Exam exam = new Exam(title, questions, duration);
                userManager.createExam(exam);
                showMessage("Exam created successfully!");
                primaryStage.setScene(createTeacherScene(primaryStage));
            } catch (NumberFormatException ex) {
                showMessage("Please enter a valid duration in minutes.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            if (!sceneHistory.isEmpty()) {
                primaryStage.setScene(sceneHistory.pop());
            }
        });

        grid.add(titleLabel, 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(durationLabel, 0, 1);
        grid.add(durationField, 1, 1);
        grid.add(questionLabel, 0, 2);
        grid.add(questionField, 1, 2);
        grid.add(answerLabel, 0, 3);
        grid.add(answerField, 1, 3);
        grid.add(addQuestionButton, 1, 4);
        grid.add(submitExamButton, 1, 5);
        grid.add(backButton, 1, 6);

        return new Scene(grid, 800, 400);
    }

    private Scene createExamSelectionScene(Stage primaryStage) {
        GridPane grid = createGridPane();

        Label selectExamLabel = new Label("Select an Exam:");
        ComboBox<Exam> examComboBox = new ComboBox<>();
        examComboBox.getItems().addAll(userManager.getExams());

        Button startExamButton = new Button("Start Exam");
        startExamButton.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
        startExamButton.setOnAction(e -> {
            Exam selectedExam = examComboBox.getValue();
            if (selectedExam != null) {
                sceneHistory.push(primaryStage.getScene());
                primaryStage.setScene(createExamScene(primaryStage, selectedExam));
            } else {
                showMessage("Please select an exam.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            if (!sceneHistory.isEmpty()) {
                primaryStage.setScene(sceneHistory.pop());
            }
        });

        grid.add(selectExamLabel, 0, 0);
        grid.add(examComboBox, 1, 0);
        grid.add(startExamButton, 1, 1);
        grid.add(backButton, 1, 2);

        return new Scene(grid, 800, 400);
    }

    private Scene createExamScene(Stage primaryStage, Exam exam) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setStyle("-fx-background-color: #87CEEB;"); // Modern sky blue background

        Label examTitleLabel = new Label("Exam: " + exam.getTitle());
        examTitleLabel.setStyle("-fx-text-fill: #000000;"); // Black text
        GridPane.setConstraints(examTitleLabel, 0, 0);

        Label durationLabel = new Label("Duration: " + exam.getDuration() + " minutes");
        durationLabel.setStyle("-fx-text-fill: #000000;"); // Black text
        GridPane.setConstraints(durationLabel, 0, 1);

        List<Question> questions = exam.getQuestions();
        int rowIndex = 2;
        Map<Question, TextField> questionAnswerFields = new HashMap<>();
        for (Question question : questions) {
            Label questionLabel = new Label(question.getQuestionText());
            questionLabel.setStyle("-fx-text-fill: #000000;"); // Black text
            GridPane.setConstraints(questionLabel, 0, rowIndex);
            TextField answerField = new TextField();
            GridPane.setConstraints(answerField, 1, rowIndex);
            grid.getChildren().addAll(questionLabel, answerField);
            questionAnswerFields.put(question, answerField);
            rowIndex++;
        }

        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #FFFFF; -fx-text-fill: #000000; -fx-border-color: #FFD700;"); // White background, black text, yellow border
        GridPane.setConstraints(submitButton, 1, rowIndex);

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #FFFFF; -fx-text-fill: #000000; -fx-border-color: #FFD700;"); // White background, black text, yellow border
        GridPane.setConstraints(backButton, 1, rowIndex + 1);

        grid.getChildren().addAll(submitButton, backButton);

        // Countdown timer
        Label timerLabel = new Label();
        timerLabel.setStyle("-fx-text-fill: #000000;"); // Black text
        GridPane.setConstraints(timerLabel, 0, rowIndex + 2);
        grid.getChildren().add(timerLabel);

        // Set up the countdown timer
        Timeline timeline = new Timeline();
        AtomicInteger remainingTime = new AtomicInteger(exam.getDuration() * 60); // seconds

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            int time = remainingTime.get();
            if (time > 0) {
                remainingTime.decrementAndGet();
                int minutes = time / 60;
                int seconds = time % 60;
                timerLabel.setText(String.format("Time Remaining: %02d:%02d", minutes, seconds));
            } else {
                timeline.stop();
                submitExam(primaryStage, exam, questionAnswerFields, true); // Timer ran out
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        submitButton.setOnAction(e -> {
            timeline.stop(); // Stop the timer when the exam is submitted manually
            submitExam(primaryStage, exam, questionAnswerFields, false);
        });

        backButton.setOnAction(e -> {
            timeline.stop(); // Stop the timer when navigating back
            if (!sceneHistory.isEmpty()) {
                primaryStage.setScene(sceneHistory.pop());
            }
        });

        // Set the scene with a size that fills 50% of the screen
        Scene scene = new Scene(grid, primaryStage.getWidth() * 0.5, primaryStage.getHeight() * 0.5);
        return scene;
    }

    private int gradeExam(Exam exam, Map<Question, String> studentAnswers) {
        int score = 0;
        for (Question question : exam.getQuestions()) {
            String correctAnswer = question.getCorrectAnswer();
            String studentAnswer = studentAnswers.get(question);
            if (correctAnswer != null && correctAnswer.equalsIgnoreCase(studentAnswer)) {
                score++;
            }
        }
        return score;
    }


    private void submitExam(Stage primaryStage, Exam exam, Map<Question, TextField> questionAnswerFields, boolean isTimedOut) {
        Map<Question, String> studentAnswers = new HashMap<>();
        for (Map.Entry<Question, TextField> entry : questionAnswerFields.entrySet()) {
            studentAnswers.put(entry.getKey(), entry.getValue().getText());
        }

        int score = gradeExam(exam, studentAnswers);

        String message = isTimedOut ? "Time is up! Your exam was submitted automatically." : "Exam submitted successfully.";
        message += "\nYou scored " + score + " out of " + exam.getQuestions().size();

        showMessage(message);
        primaryStage.setScene(createStudentScene(primaryStage));
    }

    private GridPane createGridPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: lightblue;");
        return grid;
    }

    private void showMessage(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getDialogPane().setId("alertDialogPane");
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }



    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}