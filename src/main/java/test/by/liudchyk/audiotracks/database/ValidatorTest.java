package test.by.liudchyk.audiotracks.database;

import by.liudchyk.audiotracks.validator.Validator;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Admin on 21.01.2017.
 */
public class ValidatorTest {
    private static Validator validator;

    @BeforeClass
    public static void initValidator(){
        validator = new Validator();
    }

    @AfterClass
    public static void destroyValidator(){
        validator = null;
    }

    @Test
    public void checkIsLengthValidFirst(){
        String length = "Length";
        boolean actual = validator.isLengthValid(length);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkIsLengthValidSecond(){
        String length = "12345678";
        boolean actual = validator.isLengthValid(length);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkIsLengthValidThird(){
        String length = "22";
        boolean actual = validator.isLengthValid(length);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkIsGenreLengthValid(){
        String genre = "";
        boolean actual = validator.isGenreLengthValid(genre);
        Assert.assertTrue(actual);
    }

    @Test
    public void  checkIsTitleLengthValid(){
        String title = "";
        boolean actual = validator.isTitleLengthValid(title);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkIsEmailValidFirst(){
        String email = "";
        boolean actual = validator.isEmailValid(email);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkIsEmailValidSecond(){
        String email = "aaaaa@mailru";
        boolean actual = validator.isEmailValid(email);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkIsEmailValidThird(){
        String email = "aaaaamail.ru";
        boolean actual = validator.isEmailValid(email);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkIsEmailValidFourth(){
        String email = "aaaaa@mail.ru";
        boolean actual = validator.isEmailValid(email);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkIsCardValidFirst(){
        String card = "";
        boolean actual = validator.isCardValid(card);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkIsCardValidSecond(){
        String card = "112222221222333.0";
        boolean actual = validator.isCardValid(card);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkIsCardValidThird(){
        String card = "111111111111111111";
        boolean actual = validator.isCardValid(card);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkIsCardValidFourth(){
        String card = "-111222333333222";
        boolean actual = validator.isCardValid(card);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkIsBonusValidFirst(){
        String bonus = "";
        boolean actual = validator.isBonusValid(bonus);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkIsBonusValidSecond(){
        String bonus = "101";
        boolean actual = validator.isBonusValid(bonus);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkIsBonusValidThird(){
        String bonus = "-12";
        boolean actual = validator.isBonusValid(bonus);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkIsBonusValidFourth(){
        String bonus = "11";
        boolean actual = validator.isBonusValid(bonus);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkIsMoneyChangeValidFirst(){
        String card = "1111111111111111";
        double money = 12;
        String actual = validator.isMoneyChangeValid(money,card);
        String expected = "";
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void checkIsMoneyChangeValidSecond(){
        String card = "1111111111111111";
        double money = -12.0;
        String actual = validator.isMoneyChangeValid(money,card);
        String expected = "";
        Assert.assertNotEquals(actual,expected);
    }

    @Test
    public void checkIsPriceChangeValidFirst(){
        String money = "-12.0";
        String actual = validator.isPriceChangeValid(money);
        String expected = "";
        Assert.assertNotEquals(actual,expected);
    }

    @Test
    public void checkIsPriceChangeValidSecond(){
        String money = "12O";
        String actual = validator.isPriceChangeValid(money);
        String expected = "";
        Assert.assertNotEquals(actual,expected);
    }

    @Test
    public void checkIsPriceChangeValidThird(){
        String money = "12";
        String actual = validator.isPriceChangeValid(money);
        String expected = "";
        Assert.assertEquals(actual,expected);
    }

}
