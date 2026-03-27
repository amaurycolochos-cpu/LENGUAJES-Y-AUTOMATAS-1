import java.util.Scanner;

public class AnalizadorSintactico {

    private static String input;
    private static int pos = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingresa una expresion: - AnalizadorSintactico.java:11");
        input = scanner.nextLine().replaceAll("\\s+", "");

        if (expr() && pos == input.length()) {
            System.out.println("Expresion valida - AnalizadorSintactico.java:15");
        } else {
            System.out.println("Expresion invalida - AnalizadorSintactico.java:17");
        }
    }

    // Expr -> Term { + Term }
    private static boolean expr() {
        if (!term()) return false;

        while (pos < input.length() && input.charAt(pos) == '+') {
            pos++; // consumir '+'
            if (!term()) return false;
        }

        return true;
    }

    // Term -> id (letra)
    private static boolean term() {
        if (pos < input.length() && Character.isLetter(input.charAt(pos))) {
            pos++; // consumir identificador
            return true;
        }
        return false;
    }
}