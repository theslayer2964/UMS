package vn.molu.service.request;

public class Search {

    String value;
    boolean regex;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isRegex() {
        return regex;
    }

    public void setRegex(boolean regex) {
        this.regex = regex;
    }

    @Override
    public String toString() {
        return "Search{" +
                "value='" + value + '\'' +
                ", regex=" + regex +
                '}';
    }
}
