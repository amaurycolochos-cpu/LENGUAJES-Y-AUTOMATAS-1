import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TuringReplaceFX extends Application {

    private TextField inputField;
    private Label resultLabel;
    private TextArea processArea;

    @Override
    public void start(Stage stage) {

        Label title = new Label("Reemplazo de simbolos (a → b)");
        title.setStyle(
            "-fx-font-size: 18px;" +
            "-fx-font-style: italic;" +
            "-fx-font-family: 'Times New Roman';"
        );

        inputField = new TextField();
        inputField.setPromptText("Ejemplo: aaabba");

        Button runButton = new Button("Ejecutar simulacion");

        resultLabel = new Label();

        processArea = new TextArea();
        processArea.setEditable(false);
        processArea.setWrapText(true);
        processArea.setPrefHeight(300);

        runButton.setOnAction(e -> simulate());

        VBox root = new VBox(10, title, inputField, runButton, resultLabel, processArea);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 450, 400);

        stage.setTitle("Simulacion MT - Reemplazo a→b");
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

        String result = runTuringMachine(input);

        resultLabel.setText("Resultado: " + result);
    }

    private String runTuringMachine(String input) {

        char[] tape = (input + "_").toCharArray();
        int head = 0;

        processArea.appendText("Estado inicial: q0\n");
        processArea.appendText("Cinta inicial: " + new String(tape) + "\n\n");

        while (tape[head] != '_') {

            processArea.appendText("Leyendo: " + tape[head] + "\n");

            if (tape[head] == 'a') {
                tape[head] = 'b';
                processArea.appendText("Reemplazo: a → b\n");
            }

            head++;

            processArea.appendText("Cinta: " + new String(tape) + "\n");
            processArea.appendText("Cabezal en posicion: " + head + "\n\n");
        }

        processArea.appendText("Estado final alcanzado (qf)\n");

        return new String(tape).replace("_", "");
    }

    public static void main(String[] args) {
        launch();
    }
}