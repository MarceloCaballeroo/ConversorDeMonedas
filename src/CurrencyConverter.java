import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter {
    private CurrencyService currencyService;
    private Scanner scanner;
    private Map<Integer, String> currencyOptions;

    public CurrencyConverter(Scanner scanner) {
        this.currencyService = new CurrencyService();
        this.scanner = scanner;
        initializeCurrencyOptions();
    }

    private void initializeCurrencyOptions() {
        currencyOptions = new HashMap<>();
        currencyOptions.put(1, "ARS - Peso Argentino");
        currencyOptions.put(2, "BOB - Boliviano");
        currencyOptions.put(3, "BRL - Real Brasileño");
        currencyOptions.put(4, "CLP - Peso Chileno");
        currencyOptions.put(5, "COP - Peso Colombiano");
        currencyOptions.put(6, "USD - Dólar Estadounidense");
    }

    public void start() {
        System.out.println("Bienvenido al Conversor de Monedas");

        while (true) {
            clearConsole();
            displayMenu();
            int option = getUserOption();

            switch (option) {
                case 1:
                    convertCurrency();
                    break;
                case 2:
                    System.out.println("Gracias por usar el Conversor de Monedas. ¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }

            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine(); // Consume the newline
            scanner.nextLine(); // Wait for user input
        }
    }

    private void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception e) {
            // Si falla la limpieza de la consola, simplemente imprimimos líneas en blanco
            for (int i = 0; i < 50; ++i) System.out.println();
        }
    }

    private void displayMenu() {
        System.out.println("\n1. Convertir Moneda");
        System.out.println("2. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private int getUserOption() {
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, ingrese un número válido.");
            scanner.next(); // Descarta la entrada inválida
        }
        return scanner.nextInt();
    }

    private void convertCurrency() {
        double amount = getValidAmount();
        String fromCurrency = getValidCurrencyCode("origen");
        String toCurrency = getValidCurrencyCode("destino");

        try {
            double convertedAmount = currencyService.convert(amount, fromCurrency, toCurrency);
            System.out.printf("%.2f %s = %.2f %s\n", amount, fromCurrency, convertedAmount, toCurrency);
        } catch (Exception e) {
            System.out.println("Error al convertir la moneda: " + e.getMessage());
        }
    }

    private double getValidAmount() {
        while (true) {
            System.out.print("Ingrese el monto a convertir: ");
            if (scanner.hasNextDouble()) {
                return scanner.nextDouble();
            } else {
                System.out.println("Por favor, ingrese un monto válido.");
                scanner.next(); // Descarta la entrada inválida
            }
        }
    }

    private String getValidCurrencyCode(String type) {
        while (true) {
            System.out.println("\nOpciones de moneda:");
            for (Map.Entry<Integer, String> entry : currencyOptions.entrySet()) {
                System.out.printf("%d. %s\n", entry.getKey(), entry.getValue());
            }
            System.out.println("7. Otra (ingresar manualmente)");
            System.out.print("\nSeleccione la moneda de " + type + " (1-7): ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (option >= 1 && option <= 6) {
                return currencyOptions.get(option);
            } else if (option == 7) {
                System.out.print("Ingrese el código de la moneda (por ejemplo, USD, EUR, JPY): ");
                String currency = scanner.nextLine().toUpperCase();
                if (currency.matches("[A-Z]{3}")) {
                    return currency;
                } else {
                    System.out.println("Código de moneda no válido. Por favor, intente de nuevo.");
                }
            } else {
                System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }
}