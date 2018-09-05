package domain;

public class FillAnswer extends Answer {

    private String text;

    public FillAnswer(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
