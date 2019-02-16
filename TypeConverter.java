import java.awt.Color;

/*
 * static class used for conversions
 * between decimal and non-decimal types,
 * including hexadecimal, octal, and ASCII
 * character Strings
 */
public class TypeConverter {

    private TypeConverter() {
      // class cannot be instantiated
    }

    public static int charToInt(char digit) {
        switch (digit) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            default:
                throw new IllegalArgumentException();

        }
    }

    public static int stringToInt(String number) {
        int pow = 0;
        int result = 0;
        for (int i = number.length() - 1; i >= 0; i--) {
            result += charToInt(number.charAt(i)) * Math.pow(10, pow);
            pow++;
        }
        return result;
    }

    public static int binaryStringToInt(String number) {
        int pow = 0;
        int result = 0;
        for (int i = number.length() - 1; i >= 0; i--) {
            result += charToInt(number.charAt(i)) * Math.pow(2, pow);
            pow++;
        }
        return result;
    }

    public static int octalStringToInt(String number) {
        int pow = 0;
        int result = 0;
        for (int i = number.length() - 1; i >= 0; i--) {
            result += charToInt(number.charAt(i)) * Math.pow(8, pow);
            pow++;
        }
        return result;
    }

    public static int hexStringToInt(String number) throws Exception {
        if(number.length() < 2 || number.charAt(0) != '0' || number.charAt(1) != 'x')
            throw new Exception();
        else
            number = number.substring(2, number.length());
        int pow = 0;
        int result = 0;
        for (int i = number.length() - 1; i >= 0; i--) {
            switch (number.charAt(i)) {
                case 'A':
                    result += 10 * Math.pow(16, pow);
                    break;
                case 'B':
                    result += 11 * Math.pow(16, pow);
                    break;
                case 'C':
                    result += 12 * Math.pow(16, pow);
                    break;
                case 'D':
                    result += 13 * Math.pow(16, pow);
                    break;
                case 'E':
                    result += 14 * Math.pow(16, pow);
                    break;
                case 'F':
                    result += 15 * Math.pow(16, pow);
                    break;
                default:
                    result += charToInt(number.charAt(i)) * Math.pow(16, pow);
            }
            pow++;
        }
        return result;
    }

    /*
     * Converts integer argument to a binary
     * number in String format
     */
    public static String decToBin(int number) {
        String result = "";
        while (number > 0) {
            result = number % 2 + result;
            number /= 2;
        }

        while (result.length() % 8 != 0) {
            result = '0' + result;
        }

        return result;
    }

    /*
     * Converts integer argument to a octal
     * number in String format
     */
    public static String decToOct(int number) {
        String result = "";
        while (number > 0) {
            result = number % 8 + result;
            number /= 8;
        }
        return result;
    }

    /*
     * Converts integer argument to a hex
     * number in String format
     */
    public static String decToHex(int number) {
        String result = "";

        while (number > 0) {
            int remainder = number % 16;
            switch (remainder) {
                case 10:
                    result = "A" + result;
                    break;
                case 11:
                    result = "B" + result;
                    break;
                case 12:
                    result = "C" + result;
                    break;
                case 13:
                    result = "D" + result;
                    break;
                case 14:
                    result = "E" + result;
                    break;
                case 15:
                    result = "F" + result;
                    break;
                default:
                    result = remainder + result;
            }
            number /= 16;
        }
        return "0x" + result;
    }

    /*
     * Converts a binary number in String form to its
     * corresponding ASCII-encoded character String
     */
    public static String binToChars(String number) {
        String result = "";

        // pad number with leading 0's to ensure valid character encoding
        while (number.length() % 8 != 0) {
            number = '0' + number;
        }

        // extract each ASCII character from the binary number & append to @param result
        for (int i = 0; i < number.length(); i += 8) {
            char curChar = (char) Integer.parseInt(number.substring(i, i + 8), 2);
            result += curChar;
        }
        return result;
    }

    /*
     * Converts a character String into its corresponding
     * binary number representation in String form
     */
    public static String stringToBin(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            result += decToBin(str.charAt(i));
            while (result.length() % 8 != 0) {
                result = "0" + result;
            }
        }
        return result;
    }

    /*
     * Converts a Color object to a binary string
     * representing its respective alpha/RGB components
     */
    public static String colorToBinary(Color color) {
        StringBuilder result = new StringBuilder();
        String red = TypeConverter.decToBin(color.getRed());
        String green = TypeConverter.decToBin(color.getGreen());
        String blue = TypeConverter.decToBin(color.getBlue());

        result.append(red);
        result.append(green);
        result.append(blue);
        return result.toString();
    }

    /*
     * Converts a binary String to its corresponding 'float'
     * value per the IEEE standard, and returns this value
     * in String format
     */
    public static String binaryToFloat(String number) {
        while(number.length() < 32)
            number += "0";
        String result = "";

        // add negative sign if 'float' has leading '1'
        if(number.charAt(0) == '1')
            result += "-";

        // calculate exponent of the floating point number
        int power = TypeConverter.binaryStringToInt(number.substring(1, 9))-127;

        // calculate mantissa of the float
        double sum = 0;
        int exp = power;

        for(int i = 9; i < 32; i++) {
            if(number.charAt(i) == '1')
                sum += Math.pow(2, exp);
            exp--;
        }

        result = result + sum;

        return result;
    }

    /*
     * Converts a 'float' in String form to its corresponding
     * binary value per the IEEE standard, and returns it in
     * String format
     */
    public static String floatToBinary(String number) {
        StringBuilder result = new StringBuilder();
        float num = Float.parseFloat(number);

        if(num < 0) {
            result.append("1");
            num *= -1;
        } else {
            result.append("0");
        }


        // extract leading non-decimal portion of number
        String lead = TypeConverter.decToBin((int) num);
        lead = lead.substring(lead.indexOf('1'), lead.length());

        num = num % 1;

        int exp = lead.length()-1;

        // append exponent portion of float to @param result
        result.append(TypeConverter.decToBin(exp+127).substring(0,8));


        // append mantissa to @param result
        result.append(lead);

        while(result.length() < 32) {
            num *= 2;
            if(num > 1.0) {
                result.append("1");
                num -= 1;
            } else {
                result.append("0");
            }
        }

        return result.toString();
    }
}
