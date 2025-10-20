package tictactoe.ui;

import java.util.Scanner;

/**
 * Valida las entradas del usuario.
 * 
 * @author Andrés López
 * @version 1
 */
public class InputChecker {
    private final Scanner s;

    public InputChecker(Scanner scanner) {
        this.s = scanner;
    }

    /**
     * Obtiene un entero entre min y max con validaciones
     */
    public int getInteger(int min, int max) {
        int input;

        try {
            do {
                System.out.printf("| > ");
                input = s.nextInt();
                s.nextLine(); // Consume el salto de línea para evitar problemas con lecturas posteriores

                if (input < min || input > max) {
                    System.out.println("| Entrada no valida, intenta de nuevo");
                    System.out.println("| ");
                }
            } while (input < min || input > max);

            return input;
        } catch (Exception e) {
            s.nextLine(); // Limpia el buffer en caso de entrada no numérica (ej. "abc")
            System.out.println("Entrada no valida, intenta de nuevo");
            System.out.println("| ");
        }

        // Si por algún motivo llegamos acá, que repita
        return getInteger(min, max);
    }

    /**
     * Obtiene un entero cualquiera, con validaciones
     */
    public int getInteger() {
        int input;

        do {
            System.out.printf("| > ");
            input = s.nextInt();
            s.nextLine(); // Consume el salto de línea

            if (input < 0) {
                System.out.println("Entrada no valida, intenta de nuevo");
            }
        } while (input < 0);

        return input;
    }
}
