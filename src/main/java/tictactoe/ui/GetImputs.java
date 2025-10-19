package tictactoe.ui;

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Gestiona el ingreso de datos por parte del usuario con validación incluida
 * 
 * @author Andrés López
 * @version 1
 */
public class GetImputs {
    private Scanner s = new Scanner(System.in);

    public int getInteger(int min, int max) {
        // Repite hasta obtener un resultado válido
        while (true) {
            System.out.printf("> Ingresa un valor entre %d y %d\n", min, max);
            System.out.printf("> -> ");

            // Intenta la entrada del usuario hasta que sea correcta
            try {
                int choice = s.nextInt();

                // Retorna el valor si está en el rango correcto
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.err.println("> Valor fuera de rango. Intenta de nuevo.\n> ");
                }
            } catch (InputMismatchException e) {
                System.err.println("> Entrada no válida. Por favor, ingresa un número.\n> ");
                s.next(); // Importante: consume la entrada incorrecta para evitar un bucle infinito
            }
        }
    }
}
