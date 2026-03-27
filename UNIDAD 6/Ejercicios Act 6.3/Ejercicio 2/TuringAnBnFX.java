import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TuringAnBnFX extends Application {

    private TextField inputField;
    private Label resultLabel;
    private TextArea processArea;

    @Override
    public void start(Stage stage) {

        Label title = new Label("Simulacion de Maquina de Turing (Palindromos)");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        inputField = new TextField();
        inputField.setPromptText("Ingresa cadena (ejemplo: abba)");

        Button validateButton = new Button("Ejecutar simulacion");
        validateButton.setStyle("-fx-font-size: 14px;");

        resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        processArea = new TextArea();
        processArea.setEditable(false);
        processArea.setWrapText(true);
        processArea.setPrefHeight(300);
        processArea.setMaxHeight(Double.MAX_VALUE);

        validateButton.setOnAction(e -> simulate());

        VBox root = new VBox(10, title, inputField, validateButton, resultLabel, processArea);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 450, 400);

        stage.setTitle("Simulacion MT - Palindromos");
        stage.setScene(scene);
        stage.show();
    }

    private void simulate() {
        String input = inputField.getText().trim();
        processArea.clear();

        if (input.isEmpty()) {
            resultLabel.setText("Ingresa una cadena");
            return;
        }

        boolean result = runTuringMachine(input);

        if (result) {
            resultLabel.setText("Resultado: Cadena valida (palindromo)");
        } else {
            resultLabel.setText("Resultado: Cadena invalida");
        }
    }

    private boolean runTuringMachine(String input) {

        char[] tape = (input + "_").toCharArray();
        int left = 0;
        int right = input.length() - 1;

        processArea.appendText("Estado inicial: q0\n");
        processArea.appendText("Cinta: " + new String(tape) + "\n\n");

        while (left < right) {

            processArea.appendText("Comparando posiciones " + left + " y " + right + "\n");

            if (tape[left] != tape[right]) {
                processArea.appendText("No coinciden -> rechazo\n");
                return false;
            }

            processArea.appendText("Coinciden -> marcar\n");

            tape[left] = 'X';
            tape[right] = 'X';

            log(tape);

            left++;
            right--;
        }

        processArea.appendText("\nEstado final alcanzado (qf)\n");
        return true;
    }

    private void log(char[] tape) {
        processArea.appendText("Cinta: " + new String(tape) + "\n\n");
    }

    public static void main(String[] args) {
        launch();
    }
}