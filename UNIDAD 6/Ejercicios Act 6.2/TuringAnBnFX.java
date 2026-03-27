import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TuringAnBnFX extends Application {

    private TextField inputField;
    private Label resultLabel;

    @Override
    public void start(Stage stage) {

        // Titulo
        Label title = new Label("Maquina de Turing (a^n b^n)");

        // Campo de entrada
        inputField = new TextField();
        inputField.setPromptText("Ingresa cadena (ej: aabb)");

        // Boton
        Button validateButton = new Button("Validar");

        // Resultado
        resultLabel = new Label();

        // Evento del boton
        validateButton.setOnAction(e -> validate());

        // Layout
        VBox root = new VBox(10, title, inputField, validateButton, resultLabel);

        // Escena
        Scene scene = new Scene(root, 350, 200);

        stage.setTitle("Simulacion MT");
        stage.setScene(scene);
        stage.show();
    }

    private void validate() {
        String input = inputField.getText();

        if (isValid(input)) {
            resultLabel.setText("Cadena valida");
        } else {
            resultLabel.setText("Cadena invalida");
        }
    }

    private boolean isValid(String str) {

        int i = 0;

        // contar 'a'
        while (i < str.length() && str.charAt(i) == 'a') {
            i++;
        }

        int countA = i;

        // contar 'b'
        int j = i;
        while (j < str.length() && str.charAt(j) == 'b') {
            j++;
        }

        int countB = j - i;

        // validar condiciones
        return (j == str.length()) && (countA == countB) && (countA > 0);
    }

    public static void main(String[] args) {
        launch();
    }
}