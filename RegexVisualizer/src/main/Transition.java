package main;

public class Transition {
    public char symbol;  // '\0' means epsilon
    public State to;

    public Transition(char symbol, State to) {
        this.symbol = symbol;
        this.to = to;
    }
}
