import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AnalizadorLexicoFX extends Application {

    @Override
    public void start(Stage stage) {

        TextArea codigo = new TextArea();
        codigo.setPromptText("Escribe el codigo aqui...");

        Button analizar = new Button("Analizar");

        TextArea resultado = new TextArea();
        resultado.setEditable(false);

        analizar.setOnAction(e -> {

            String texto = codigo.getText();

            // Separar operadores y delimitadores para que queden como tokens independientes
            texto = texto.replace(";", " ; ")
                         .replace("=", " = ")
                         .replace("+", " + ")
                         .replace("-", " - ")
                         .replace("*", " * ")
                         .replace("/", " / ")
                         .replace("(", " ( ")
                         .replace(")", " ) ");

            String[] tokens = texto.trim().split("\\s+");

            StringBuilder salida = new StringBuilder("Token\t\tTipo\n");

            for (String token : tokens) {

                if (token.equals("int") || token.equals("float") || token.equals("double")) {
                    salida.append(token).append("\t\tPalabra reservada\n");
                }
                else if (token.matches("[a-zA-Z][a-zA-Z0-9]*")) {
                    salida.append(token).append("\t\tIdentificador\n");
                }
                else if (token.matches("[0-9]+")) {
                    salida.append(token).append("\t\tNumero\n");
                }
                else if (token.equals("=") || token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                    salida.append(token).append("\t\tOperador\n");
                }
                else if (token.equals(";") || token.equals("(") || token.equals(")")) {
                    salida.append(token).append("\t\tDelimitador\n");
                }
            }

            resultado.setText(salida.toString());
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                new Label("Codigo fuente"),
                codigo,
                analizar,
                new Label("Resultado"),
                resultado
        );

        Scene escena = new Scene(layout, 500, 400);
        stage.setTitle("Analizador Lexico");
        stage.setScene(escena);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}