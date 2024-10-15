import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyService {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/76bad8f8577337ab1d738389/latest/USD";
    private Gson gson;

    public CurrencyService() {
        this.gson = new Gson();
    }

    public double convert(double amount, String fromCurrency, String toCurrency) throws IOException {
        double exchangeRate = getExchangeRate(fromCurrency, toCurrency);
        return amount * exchangeRate;
    }

    private double getExchangeRate(String fromCurrency, String toCurrency) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        if (conn.getResponseCode() != 200) {
            throw new IOException("Error en la API: " + conn.getResponseCode());
        }

        StringBuilder response = new StringBuilder();
        try (Scanner scanner = new Scanner(url.openStream())) {
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
        }

        JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
        JsonObject rates = jsonObject.getAsJsonObject("conversion_rates");

        if (!rates.has(fromCurrency) || !rates.has(toCurrency)) {
            throw new IllegalArgumentException("Código de moneda no válido");
        }

        return rates.get(toCurrency).getAsDouble() / rates.get(fromCurrency).getAsDouble();
    }
}