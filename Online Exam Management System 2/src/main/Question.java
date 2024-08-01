package main;
/**
 * The type Question.
 */
public class Question {
    private String questionText;
    private String correctAnswer;

    /**
     * Instantiates a new Question.
     *
     * @param questionText  the question text
     * @param correctAnswer the correct answer
     */
    public Question(String questionText, String correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Gets question text.
     *
     * @return the question text
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Gets correct answer.
     *
     * @return the correct answer
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
