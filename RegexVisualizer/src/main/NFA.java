package main;

import java.util.*;

public class NFA {
    public State start;
    public Set<State> acceptStates;

    public NFA(State start, Set<State> acceptStates) {
        this.start = start;
        this.acceptStates = acceptStates;
    }

    // Basic accept method
    public boolean accepts(String input) {
        return dfsEpsilon(start, input, 0, new HashSet<>());
    }

    private boolean dfsEpsilon(State state, String input, int index, Set<State> visited) {
        if (index == input.length() && acceptStates.contains(state)) {
            return true;
        }

        if (index < input.length()) {
            for (Transition t : state.transitions) {
                if (t.symbol == input.charAt(index)) {
                    if (dfsEpsilon(t.to, input, index + 1, new HashSet<>())) {
                        return true;
                    }
                }
            }
        }

        for (Transition t : state.transitions) {
            if (t.symbol == '\0' && !visited.contains(t.to)) {
                visited.add(t.to);
                if (dfsEpsilon(t.to, input, index, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    // New: trace the state sets for each step
    public List<Set<State>> traceExecution(String input) {
        List<Set<State>> trace = new ArrayList<>();

        Set<State> current = epsilonClosure(Set.of(start));
        trace.add(new HashSet<>(current));

        for (char c : input.toCharArray()) {
            Set<State> next = new HashSet<>();
            for (State state : current) {
                for (Transition t : state.transitions) {
                    if (t.symbol == c) {
                        next.add(t.to);
                    }
                }
            }
            current = epsilonClosure(next);
            trace.add(new HashSet<>(current));
        }

        return trace;
    }

    private Set<State> epsilonClosure(Set<State> states) {
        Set<State> closure = new HashSet<>(states);
        Stack<State> stack = new Stack<>();
        stack.addAll(states);

        while (!stack.isEmpty()) {
            State s = stack.pop();
            for (Transition t : s.transitions) {
                if (t.symbol == '\0' && !closure.contains(t.to)) {
                    closure.add(t.to);
                    stack.push(t.to);
                }
            }
        }

        return closure;
    }
}
