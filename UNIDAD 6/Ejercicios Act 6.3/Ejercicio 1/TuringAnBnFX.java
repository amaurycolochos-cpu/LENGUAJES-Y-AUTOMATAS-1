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

        Label title = new Label("Simulacion de Maquina de Turing (a\u207F b\u207F)");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        inputField = new TextField();
        inputField.setPromptText("Ingresa cadena (ejemplo: aabb)");

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

        stage.setTitle("Simulacion MT paso a paso");
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
            resultLabel.setText("Resultado: Cadena valida");
        } else {
            resultLabel.setText("Resultado: Cadena invalida");
        }
    }

    private boolean runTuringMachine(String input) {

        char[] tape = (input + "_").toCharArray();
        int head = 0;
        String state = "q0";

        processArea.appendText("Estado inicial: q0\n");
        processArea.appendText("Cinta: " + new String(tape) + "\n\n");

        while (true) {

            // q0: buscar 'a' sin marcar
            if (state.equals("q0")) {

                if (tape[head] == 'a') {
                    tape[head] = 'X';
                    state = "q1";
                    head++;
                    log(state, tape, head);

                } else if (tape[head] == 'X') {
                    head++;

                } else if (tape[head] == 'Y') {
                    head++;

                } else if (tape[head] == '_') {
                    state = "qf";
                } else {
                    state = "qr";
                }
            }

            // q1: buscar 'b'
            else if (state.equals("q1")) {

                if (tape[head] == 'a' || tape[head] == 'X') {
                    head++;

                } else if (tape[head] == 'b') {
                    tape[head] = 'Y';
                    state = "q2";
                    head--;
                    log(state, tape, head);

                } else if (tape[head] == 'Y') {
                    head++;

                } else {
                    state = "qr";
                }
            }

            // q2: regresar al inicio
            else if (state.equals("q2")) {

                if (head > 0) {
                    head--;
                } else {
                    state = "q0";
                    log(state, tape, head);
                }
            }

            // estado final
            else if (state.equals("qf")) {

                // Validación final: no debe haber 'a' o 'b' sin marcar
                for (char c : tape) {
                    if (c == 'a' || c == 'b') {
                        processArea.appendText("\nCadena rechazada (qr)\n");
                        return false;
                    }
                }

                processArea.appendText("\nEstado final alcanzado (qf)\n");
                return true;
            }

            // rechazo
            else {
                processArea.appendText("\nCadena rechazada (qr)\n");
                return false;
            }
        }
    }

    private void log(String state, char[] tape, int head) {
        processArea.appendText("Estado: " + state + "\n");
        processArea.appendText("Cinta: " + new String(tape) + "\n");
        processArea.appendText("Cabezal en posicion: " + head + "\n\n");
    }

    public static void main(String[] args) {
        launch();
    }
}