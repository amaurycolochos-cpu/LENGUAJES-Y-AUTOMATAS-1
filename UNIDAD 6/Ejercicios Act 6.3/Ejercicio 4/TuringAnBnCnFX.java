import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TuringAnBnCnFX extends Application {

    private TextField inputField;
    private Label resultLabel;
    private TextArea processArea;

    @Override
    public void start(Stage stage) {

        Label title = new Label("Lenguaje a\u207F b\u207F c\u207F");
        title.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-style: italic;" +
            "-fx-font-family: 'Times New Roman';"
        );

        inputField = new TextField();
        inputField.setPromptText("Ejemplo: aaabbbccc");

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

        stage.setTitle("Simulacion MT - a\u207F b\u207F c\u207F");
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

            // q0: buscar 'a'
           if (state.equals("q0")) {

    if (tape[head] == 'a') {
        tape[head] = 'X';
        state = "q1";
        head++;
        log(state, tape, head);

    } else if (tape[head] == 'X') {
        head++;

    } else if (tape[head] == 'b' || tape[head] == 'c') {
        //si encuentra b o c antes de terminar las a → error
        state = "qr";

    } else if (tape[head] == 'Y' || tape[head] == 'Z') {
        head++;

    } else if (tape[head] == '_') {
        state = "qf";

    } else {
        state = "qr";
    }
}

            // q1: buscar 'b'
            else if (state.equals("q1")) {

                if (tape[head] == 'b') {
                    tape[head] = 'Y';
                    state = "q2";
                    head++;
                    log(state, tape, head);

                } else if (tape[head] == 'a' || tape[head] == 'X' || tape[head] == 'Y') {
                    head++;

                } else {
                    state = "qr";
                }
            }

            // q2: buscar 'c'
            else if (state.equals("q2")) {

                if (tape[head] == 'c') {
                    tape[head] = 'Z';
                    state = "q3";
                    head--;
                    log(state, tape, head);

                } else if (tape[head] == 'b' || tape[head] == 'Y' || tape[head] == 'Z') {
                    head++;

                } else {
                    state = "qr";
                }
            }

            // q3: regresar al inicio
            else if (state.equals("q3")) {

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
                    if (c == 'a' || c == 'b' || c == 'c') {
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