import java.util.Scanner;

public class Input {
    public static int scanInt(String message, Scanner scan) {
        while (true) {
            try {
                System.out.print(message);
                int value = scan.nextInt();
                return value;
            } catch (Exception e) {
                System.out.println("Valor inválido, digite um número inteiro.");
            } finally {
                scan.nextLine();
            }
        }
    }

    public static String scanString(String message, Scanner scan) {
        while (true) {
            try {
                System.out.print(message);
                String value = scan.nextLine().trim();
                if (value.isEmpty()) {
                    System.out.println("Valor inválido, tente novamente.");
                } else {
                    return value;
                }
            } catch (Exception e) {
                System.out.println("Valor inválido, digite um texto válido.");
            }
        }
    }
}