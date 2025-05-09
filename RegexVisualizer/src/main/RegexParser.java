package main;

import java.util.*;

public class RegexParser {

    private int pos;
    private String regex;

    public NFA buildFromRegex(String regex) throws Exception {
        this.regex = regex;
        this.pos = 0;
        NFA nfa = expression();
        if (pos < regex.length()) {
            throw new Exception("Unexpected character at position " + pos);
        }
        return nfa;
    }

    // expression ::= term ('|' term)*
    private NFA expression() throws Exception {
        NFA term = term();
        while (pos < regex.length() && regex.charAt(pos) == '|') {
            pos++; // skip '|'
            NFA other = term();
            term = union(term, other);
        }
        return term;
    }

    // term ::= factor+
    private NFA term() throws Exception {
        NFA result = factor();
        while (pos < regex.length() && regex.charAt(pos) != ')' && regex.charAt(pos) != '|') {
            result = concat(result, factor());
        }
        return result;
    }

    // factor ::= base ('*')?
    private NFA factor() throws Exception {
        NFA base = base();
        if (pos < regex.length() && regex.charAt(pos) == '*') {
            pos++;
            return star(base);
        }
        return base;
    }

    // base ::= '(' expression ')' | symbol
    private NFA base() throws Exception {
        if (regex.charAt(pos) == '(') {
            pos++;
            NFA nfa = expression();
            if (pos >= regex.length() || regex.charAt(pos) != ')') {
                throw new Exception("Unmatched parenthesis");
            }
            pos++;
            return nfa;
        } else {
            return symbol(regex.charAt(pos++));
        }
    }

    // Build NFA for a single symbol
    private NFA symbol(char c) {
        State start = new State();
        State accept = new State();
        accept.isAccept = true;

        start.addTransition(c, accept);

        Set<State> accepts = new HashSet<>();
        accepts.add(accept);
        return new NFA(start, accepts);
    }

    // Union of two NFAs
    private NFA union(NFA a, NFA b) {
        State start = new State();
        State accept = new State();
        accept.isAccept = true;

        start.addEpsilonTransition(a.start);
        start.addEpsilonTransition(b.start);

        for (State s : a.acceptStates) {
            s.addEpsilonTransition(accept);
            s.isAccept = false;
        }
        for (State s : b.acceptStates) {
            s.addEpsilonTransition(accept);
            s.isAccept = false;
        }

        Set<State> accepts = new HashSet<>();
        accepts.add(accept);
        return new NFA(start, accepts);
    }

    // Concatenation of two NFAs
    private NFA concat(NFA a, NFA b) {
        for (State s : a.acceptStates) {
            s.addEpsilonTransition(b.start);
            s.isAccept = false;
        }
        return new NFA(a.start, b.acceptStates);
    }

    // Kleene star of an NFA
    private NFA star(NFA a) {
        State start = new State();
        State accept = new State();
        accept.isAccept = true;

        start.addEpsilonTransition(a.start);
        start.addEpsilonTransition(accept);

        for (State s : a.acceptStates) {
            s.addEpsilonTransition(a.start);
            s.addEpsilonTransition(accept);
            s.isAccept = false;
        }

        Set<State> accepts = new HashSet<>();
        accepts.add(accept);
        return new NFA(start, accepts);
    }
}
