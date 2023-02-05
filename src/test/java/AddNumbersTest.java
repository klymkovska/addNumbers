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
    public void checkEmptySum_shouldReturnEmptiness() {
        String result = main.addNumbers("", "");
        Assert.assertEquals(result, "");
    }

    @Test
    public void checkSumNotEqualNumbersAmount_shouldThrowException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            main.addNumbers("123 456", "111");
        });
    }

    @Test
    public void checkSumNumberFormatValidation_shouldThrowException() {
        Assert.assertThrows(NumberFormatException.class, () -> {
            main.addNumbers("123.4", "111,1");
        });
    }
}
