package domain;

public class Question {

    private static int lastID = -1;
    public static int getNextID() {
        return ++lastID;
    }

    public final int id;
    private String text;
    private Answer answer;

    public Question(int id, String text, Answer answer) {
        this.id = id;
        this.text = text;
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return id + ") " + text;
    }
}
