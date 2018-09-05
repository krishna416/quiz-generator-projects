package domain;

public class MultipleAnswer extends Answer {

    private String answerText1, answerText2, answerText3;

    private boolean answerTrue1, answerTrue2, answerTrue3;

    public MultipleAnswer(String answerText1, String answerText2, String answerText3, boolean answerTrue1, boolean answerTrue2, boolean answerTrue3) {
        this.answerText1 = answerText1;
        this.answerText2 = answerText2;
        this.answerText3 = answerText3;
        this.answerTrue1 = answerTrue1;
        this.answerTrue2 = answerTrue2;
        this.answerTrue3 = answerTrue3;
    }


    public void setAnswerTrue2(boolean answerTrue2) {
        this.answerTrue2 = answerTrue2;
    }

    public void setAnswerTrue3(boolean answerTrue3) {
        this.answerTrue3 = answerTrue3;
    }

    public void setAnswerTrue1(boolean answerTrue1) {
        this.answerTrue1 = answerTrue1;
    }

    public String getAnswerText1() {
        return answerText1;
    }

    public void setAnswerText1(String answerText1) {
        this.answerText1 = answerText1;
    }

    public String getAnswerText2() {
        return answerText2;
    }

    public void setAnswerText2(String answerText2) {
        this.answerText2 = answerText2;
    }

    public String getAnswerText3() {
        return answerText3;
    }

    public void setAnswerText3(String answerText3) {
        this.answerText3 = answerText3;
    }

    public boolean isAnswerTrue3() {
        return answerTrue3;
    }

    public boolean isAnswerTrue2() {
        return answerTrue2;
    }

    public boolean isAnswerTrue1() {
        return answerTrue1;
    }
}
