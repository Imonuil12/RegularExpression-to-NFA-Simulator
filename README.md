
# RegEx to NFA: Regex → NFA Acceptance Tester

RegEx to NFA is a lightweight JavaFX-based tool that allows you to input a regular expression and a string, and tells you whether the string is accepted by the language defined by that regular expression.

This tool is built from scratch without external libraries and serves as an educational simulation of how regex-based languages are translated into nondeterministic finite automata (NFAs).

## Features

- Regex to NFA conversion using recursive descent parsing
- Supports:
  - Union `|`
  - Concatenation (implicit)
  - Kleene Star `*`
  - Parentheses `( )`
- Epsilon (ε) transition support
- Interactive JavaFX GUI
- Displays acceptance or rejection of input string

## Demo

Watch the tool in action: [Demo Video](https://youtu.be/SLa-4XSpi8M)

## How It Works

1. **RegexParser.java** parses the input regular expression into an NFA.
2. **NFA.java** simulates input string acceptance using epsilon-closure and DFS.
3. **MainController.java** connects GUI inputs to the simulation backend.
4. **State.java** and **Transition.java** model the NFA states and edges.

## Project Structure

- `Main.java` - Launches JavaFX app
- `Main.fxml` - Defines GUI layout (text fields, button, result label)
- `MainController.java` - Handles user actions and connects logic
- `RegexParser.java` - Builds the NFA from regex using recursive parsing
- `NFA.java` - Simulates the NFA for input strings
- `State.java`, `Transition.java` - Represent automaton components

## Getting Started

### Prerequisites
- Java 17+
- JavaFX SDK (set up in your IDE)

### Run Instructions

1. Clone this repo
2. Open in your Java IDE (Eclipse, IntelliJ, etc.)
3. Link your JavaFX SDK to the project
4. Run `Main.java`

## 📚 License

This project is created for educational purposes as part of CSE355: Theoretical Computer Science.

Built by Imonuil Suleimanov
