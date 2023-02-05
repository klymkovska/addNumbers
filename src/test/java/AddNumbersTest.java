import org.junit.Assert;
import org.junit.Test;

public class AddNumbersTest {

    Main main = new Main();

    @Test
    public void checkBasicSum_shouldCalculateSum() {
        String result = main.addNumbers("123 456 789", "11 22 33");
        Assert.assertEquals(result, "134 478 822");
    }

    @Test
    public void checkLongSum_shouldCalculateSum() {
        String result = main.addNumbers("123456789012345678901 23456789", "12345678 234567890123456789012");
        Assert.assertEquals(result, "123456789012358024579 234567890123480245801");
    }

    @Test
    public void checkSumWithFloats_shouldCalculateSum() {
        String result = main.addNumbers("1234567.8901 2.345", "12.34 2345678901.2");
        Assert.assertEquals(result, "1234580.2301 2345678903.545");
    }

    @Test
    public void checkEmptySum_shouldThrowException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            main.addNumbers("", "");
        });
    }

    @Test
    public void checkSumNotEqualNumbersAmount_shouldThrowException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            main.addNumbers("123 456", "111");
        });
    }

    @Test
    public void checkSumNumberFormatValidationComma_shouldThrowException() {
        Assert.assertThrows(NumberFormatException.class, () -> {
            main.addNumbers("123.4", "111,1");
        });
    }

    @Test
    public void checkSumNumberFormatValidationMultipleDot_shouldThrowException() {
        Assert.assertThrows(NumberFormatException.class, () -> {
            main.addNumbers("123.4", "1.111.111");
        });
    }

    @Test
    public void checkIntPlusFloatSum_shouldReturnFloat() {
        String result = main.addNumbers("123.1", "123");
        Assert.assertEquals(result, "246.1");
    }

    @Test
    public void checkFractionalPartOfZero_shouldReturnInt() {
        String result = main.addNumbers("123.0", "123");
        Assert.assertEquals(result, "246");
    }

    @Test
    public void checkIntPartOfZero_shouldCalculateSum() {
        String result = main.addNumbers("0.1", "0.2");
        Assert.assertEquals(result, "0.3");
    }

    @Test
    public void checkZeroZero_shouldCalculateSum() {
        String result = main.addNumbers("0.0", "1");
        Assert.assertEquals(result, "1");
    }

    @Test
    public void checkSumWithSeveralConsecutiveSpaces_shouldThrowException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            main.addNumbers("12  34", "11 22");
        });
    }

    @Test
    public void checkSumWithLeadingSpace_shouldThrowException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            main.addNumbers(" 12  34", "11 22");
        });
    }

    @Test
    public void checkSumWithFollowingSpace_shouldThrowException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            main.addNumbers("12  34 ", "11 22");
        });
    }
}
