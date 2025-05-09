package main;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {

    @FXML private TextField regexField;
    @FXML private TextField inputField;
    @FXML private Label resultLabel;

    private NFA nfa;

    @FXML
    private void onCheck() {
        String regex = regexField.getText();
        String input = inputField.getText();

        try {
            RegexParser parser = new RegexParser();
            nfa = parser.buildFromRegex(regex);
        } catch (Exception e) {
            resultLabel.setText("❌ Invalid regex: " + e.getMessage());
            return;
        }

        boolean accepted = nfa.accepts(input);
        resultLabel.setText(accepted ? "✅ String accepted." : "❌ String rejected.");
    }
}
