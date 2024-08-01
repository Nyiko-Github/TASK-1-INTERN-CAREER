package test;

import main.Exam;
import main.Question;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExamTest {

    @Test
    void testExamConstructorAndGetters() {
        Question question1 = new Question("What is 2+2?", "4");
        Question question2 = new Question("What is the capital of France?", "Paris");
        List<Question> questions = List.of(question1, question2);
        String title = "Sample Exam";
        int duration = 30;

        Exam exam = new Exam(title, questions, duration);

        assertNotNull(exam);
        assertEquals(title, exam.getTitle());
        assertEquals(questions, exam.getQuestions());
        assertEquals(duration, exam.getDuration());
    }

    @Test
    void testToString() {
        Question question1 = new Question("What is 2+2?", "4");
        Question question2 = new Question("What is the capital of France?", "Paris");
        List<Question> questions = List.of(question1, question2);
        String title = "Sample Exam";

        Exam exam = new Exam(title, questions, 30);

        assertEquals(title, exam.toString());
    }
}
