package domain;

public class BooleanAnswer extends Answer {

    private boolean result;

    public BooleanAnswer(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
