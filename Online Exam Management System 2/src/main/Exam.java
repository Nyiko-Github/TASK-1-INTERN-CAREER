package main;

import java.util.List;

/**
 * The type Exam.
 */
public class Exam {
    private String title;
    private List<Question> questions;
    private int duration;

    /**
     * Instantiates a new Exam.
     *
     * @param title     the title
     * @param questions the questions
     * @param duration  the duration
     */
    public Exam(String title, List<Question> questions, int duration) {
        this.title = title;
        this.questions = questions;
        this.duration = duration;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets questions.
     *
     * @return the questions
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return title;
    }
}
