package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {

    String approvedCardNumber = DataHelper.getCardApproved().getCardNumber();
    String declinedCardNumber = DataHelper.getCardDeclined().getCardNumber();
    String randomCardNumber = DataHelper.getRandomCardNumber();
    String validMonth = DataHelper.getRandomMonth(1);
    String validYear = DataHelper.getRandomYear(1);
    String validOwnerName = DataHelper.getRandomName();
    String validCode = DataHelper.getNumberCVC(3);

    @AfterAll
    public static void shouldCleanBase() {
        SQLHelper.cleanBase();

    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");

    }

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    public void shouldCardPaymentApproved() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, validCode);
        cardPage.bankApprovedOperation();
        Assertions.assertEquals("Approved", SQLHelper.getCardPayment());

    }

    @Test
    public void shouldDeclinedCardPayment() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(declinedCardNumber, validMonth, validYear, validOwnerName, validCode);
        cardPage.bankDeclinedOperation();
        Assertions.assertEquals("DECLINED", SQLHelper.getCardPayment());
    }


@Test
public void shouldIncorrectPaymentNumber() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        var invalidCardNumber = DataHelper.GetAShortNumber();
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(invalidCardNumber, validMonth, validYear, validOwnerName, validCode);
        cardPage.errorFormat();
}

@Test
public void shouldMustBlankFieldCardNumberPayment() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        var emptyCardNumber = DataHelper.getEmptyField();
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(emptyCardNumber, validMonth, validYear, validOwnerName, validCode);
        cardPage.errorFormat();

}
@Test
    public void paymentByCardWithExpiredMonthlyValidityt() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        var currentYear = DataHelper.getRandomYear(0);
        var monthExpired = DataHelper.getRandomMonth(5);
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(approvedCardNumber,monthExpired,currentYear,validOwnerName,validCode);


}

}


