package main;

import java.util.ArrayList;
import java.util.List;


public class State {
	private static int idCounter = 0;
	public final int id;

    public boolean isAccept;
    public List<Transition> transitions;

    public State() {
    	this.id = idCounter++;
    	this.isAccept = false;
    	this.transitions = new ArrayList<>();
        this.isAccept = false;
        this.transitions = new ArrayList<>();
    }

    public void addTransition(char symbol, State toState) {
        transitions.add(new Transition(symbol, toState));
    }

    public void addEpsilonTransition(State toState) {
        transitions.add(new Transition('\0', toState)); // '\0' = epsilon
    }
}
