package test;

import main.Question;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class QuestionTest {

    @Test
    void testQuestionConstructor() {
        // Test data
        String questionText = "What is the capital of France?";
        String correctAnswer = "Paris";

        // Create a new Question object
        Question question = new Question(questionText, correctAnswer);

        // Assert the object is not null
        assertNotNull(question, "The Question object should not be null.");

        // Assert the question text and correct answer are correctly set
        assertEquals(questionText, question.getQuestionText(), "The question text should match the provided value.");
        assertEquals(correctAnswer, question.getCorrectAnswer(), "The correct answer should match the provided value.");
    }

    @Test
    void testGetQuestionText() {
        // Test data
        String questionText = "What is the capital of France?";
        String correctAnswer = "Paris";

        // Create a new Question object
        Question question = new Question(questionText, correctAnswer);

        // Assert the question text is correctly returned
        assertEquals(questionText, question.getQuestionText(), "The question text should match the provided value.");
    }

    @Test
    void testGetCorrectAnswer() {
        // Test data
        String questionText = "What is the capital of France?";
        String correctAnswer = "Paris";

        // Create a new Question object
        Question question = new Question(questionText, correctAnswer);

        // Assert the correct answer is correctly returned
        assertEquals(correctAnswer, question.getCorrectAnswer(), "The correct answer should match the provided value.");
    }
}
