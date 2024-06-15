package Models;

public class CastData {
    public static int parseInt(String value) {
        int result;
        try {
            result = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            result = 0;
        }
        return result;
    }

    public static double parseDouble(String value) {
        double result;
        try {
            result = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            result = 0;
        }
        return result;
    }
}
