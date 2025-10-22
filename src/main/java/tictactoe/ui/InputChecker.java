package tictactoe.ui;

import java.util.Scanner;

/**
 * Valida las entradas del usuario.
 * 
 * @author Andrés López
 * @version 2
 */
public class InputChecker {
    private final Scanner s;
    private final UIUtilities uii;

    public InputChecker(Scanner scanner) {
        this.s = scanner;
        this.uii = new UIUtilities();
    }

    /**
     * Obtiene un entero entre min y max con validaciones.
     */
    public int getInteger(int min, int max) {
        // Bucle infinito que se romperá con un return
        while (true) {
            try {
                // Recibe la entrada
                System.out.printf("| > ");
                int input = s.nextInt();

                // Consume el salto de línea
                s.nextLine();

                if (input >= min && input <= max) {
                    // Entrada válida, retorna el valor
                    return input;
                } else {
                    // El número está fuera del rango permitido
                    System.out.println("| Entrada no valida, intenta de nuevo");
                    uii.printBlankLine();
                }
            } catch (java.util.InputMismatchException e) {
                // Limpia el buffer del scanner
                s.nextLine();

                // La entrada no fue un entero
                System.out.println("| Entrada no valida, intenta de nuevo");
                uii.printBlankLine();
            }
        }
    }

    /**
     * Obtiene un entero cualquiera mayor o igual a 0, con validaciones.
     */
    public int getInteger() {
        // Simplemente llama al otro método con un rango por defecto
        return getInteger(0, Integer.MAX_VALUE);
    }
}
