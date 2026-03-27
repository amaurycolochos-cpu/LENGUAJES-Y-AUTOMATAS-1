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

        Label title = new Label("Simulacion de Maquina de Turing (0s y 1s)");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        inputField = new TextField();
        inputField.setPromptText("Ingresa cadena (ejemplo: 1010)");

        Button validateButton = new Button("Ejecutar simulacion");

        resultLabel = new Label();

        processArea = new TextArea();
        processArea.setEditable(false);
        processArea.setWrapText(true);
        processArea.setPrefHeight(300);

        validateButton.setOnAction(e -> simulate());

        VBox root = new VBox(10, title, inputField, validateButton, resultLabel, processArea);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 450, 400);

        stage.setTitle("Simulacion MT - 0s y 1s");
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

            // q0: buscar símbolo sin marcar
            if (state.equals("q0")) {

                if (tape[head] == '0') {
                    tape[head] = 'X';
                    state = "q1";
                    head++;
                    log(state, tape, head);

                } else if (tape[head] == '1') {
                    tape[head] = 'Y';
                    state = "q3";
                    head++;
                    log(state, tape, head);

                } else if (tape[head] == 'X' || tape[head] == 'Y') {
                    head++;

                } else if (tape[head] == '_') {
                    state = "qf";

                } else {
                    state = "qr";
                }
            }

            // q1: buscar 1 para emparejar con 0
            else if (state.equals("q1")) {

                if (tape[head] == '1') {
                    tape[head] = 'Y';
                    state = "q2";
                    head--;
                    log(state, tape, head);

                } else if (tape[head] == '0' || tape[head] == 'X' || tape[head] == 'Y') {
                    head++;

                } else {
                    state = "qr";
                }
            }

            // q3: buscar 0 para emparejar con 1
            else if (state.equals("q3")) {

                if (tape[head] == '0') {
                    tape[head] = 'X';
                    state = "q2";
                    head--;
                    log(state, tape, head);

                } else if (tape[head] == '1' || tape[head] == 'X' || tape[head] == 'Y') {
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

                for (char c : tape) {
                    if (c == '0' || c == '1') {
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