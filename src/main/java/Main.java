import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class Main {

    public static final String NUMBER_VALIDATION_PATTERN = "\\d+\\.?\\d*";
    public static final String NUMBER_DELIMITER_PATTERN = "\\.";
    public static final String NUMBER_DELIMITER = ".";
    public static final int INT_PART_INDEX = 0;
    public static final int FRAC_PART_INDEX = 1;
    public static final char ZERO_CHAR = '0';
    public static final String ZERO_STRIP_CHAR = String.valueOf(ZERO_CHAR);

    public static void main(String[] args) {
        String numbers1 = "1234567.8901 2.345";
        String numbers2 = "12.34 2345678901.2";
        Main main = new Main();
        System.out.println("RESULT: " + main.addNumbers(numbers1, numbers2));
    }

    public String addNumbers(String numbers1, String numbers2) throws IllegalArgumentException {
        String[] numbersArr1 = numbers1.split(StringUtils.SPACE);
        String[] numbersArr2 = numbers2.split(StringUtils.SPACE);
        validateNumbersAmt(numbersArr1, numbersArr2);
        String[] resultNumbersArr = new String[numbersArr1.length];
        for (int i = 0; i < numbersArr1.length; i++) {
            resultNumbersArr[i] = alignLengthAndAdd(numbersArr1[i], numbersArr2[i]);
        }
        return String.join(StringUtils.SPACE, resultNumbersArr);
    }

    private void validateNumbersAmt(String[] numbersArr1, String[] numbersArr2) throws IllegalArgumentException {
        if (numbersArr1.length != numbersArr2.length) {
            throw new IllegalArgumentException("The amount of numbers is not equal");
        }
    }

    private String alignLengthAndAdd(String number1, String number2) throws NumberFormatException {
        validateNumbersFormat(number1, number2);
        String[] intAndFrac1 = number1.split(NUMBER_DELIMITER_PATTERN);
        String[] intAndFrac2 = number2.split(NUMBER_DELIMITER_PATTERN);
        int maxIntLength = Math.max(intAndFrac1[INT_PART_INDEX].length(), intAndFrac2[INT_PART_INDEX].length());
        int maxFracLength = Math.max(intAndFrac1.length > 1 ? intAndFrac1[FRAC_PART_INDEX].length() : 0, intAndFrac2.length > 1 ? intAndFrac2[FRAC_PART_INDEX].length() : 0);
        String int1WithLeadingZeroes = StringUtils.leftPad(intAndFrac1[INT_PART_INDEX], maxIntLength, ZERO_CHAR);
        String int2WithLeadingZeroes = StringUtils.leftPad(intAndFrac2[INT_PART_INDEX], maxIntLength, ZERO_CHAR);
        String frac1WithFollowingZeroes = StringUtils.rightPad(intAndFrac1.length > 1 ? intAndFrac1[FRAC_PART_INDEX] : "", maxFracLength, ZERO_CHAR);
        String frac2WithFollowingZeroes = StringUtils.rightPad(intAndFrac2.length > 1 ? intAndFrac2[FRAC_PART_INDEX] : "", maxFracLength, ZERO_CHAR);
        return addIntAndFrac(int1WithLeadingZeroes, int2WithLeadingZeroes, frac1WithFollowingZeroes, frac2WithFollowingZeroes);
    }

    private void validateNumbersFormat(String number1, String number2) throws NumberFormatException {
        if (StringUtils.isNotEmpty(number1) && !Pattern.compile(NUMBER_VALIDATION_PATTERN).matcher(number1).matches()
                || StringUtils.isNotEmpty(number2) && !Pattern.compile(NUMBER_VALIDATION_PATTERN).matcher(number2).matches()) {
            throw new NumberFormatException("Number contains special symbols");
        }
    }

    private String addIntAndFrac(String int1WithLeadingZeroes, String int2WithLeadingZeroes,
                                        String frac1WithFollowingZeroes, String frac2WithFollowingZeroes) {
        String fractionalSum = parseAndAdd(frac1WithFollowingZeroes, frac2WithFollowingZeroes, 0);
        int lessSignificantDigit = 0;
        if (fractionalSum.length() > frac1WithFollowingZeroes.length()) {
            lessSignificantDigit = 1;
            fractionalSum = StringUtils.substring(fractionalSum, 1);
        }
        String intSum = parseAndAdd(int1WithLeadingZeroes, int2WithLeadingZeroes, lessSignificantDigit);
        if (fractionalSum.length() > 0) {
            return StringUtils.stripStart(intSum, ZERO_STRIP_CHAR)
                    + NUMBER_DELIMITER
                    + StringUtils.stripEnd(fractionalSum, ZERO_STRIP_CHAR);
        }
        return StringUtils.stripStart(intSum, ZERO_STRIP_CHAR);
    }

    private String parseAndAdd(String number1, String number2, int lessSignificantDigit) {
        char[] chars1 = number1.toCharArray();
        char[] chars2 = number2.toCharArray();
        String[] resultDigits = new String[chars1.length];
        for (int i = number1.length() - 1; i >= 0; i--) {
            int digitSum = Integer.parseInt(String.valueOf(chars1[i])) + Integer.parseInt(String.valueOf(chars2[i])) + lessSignificantDigit;
            if (digitSum >= 10) {
                lessSignificantDigit = 1;
                digitSum -= 10;
            } else {
                lessSignificantDigit = 0;
            }
            resultDigits[i] = String.valueOf(digitSum);
        }
        String sum = String.join(StringUtils.EMPTY, resultDigits);
        if (lessSignificantDigit == 1) {
            sum = lessSignificantDigit + sum;
        }
        return sum;
    }
}
