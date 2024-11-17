package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;


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

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");

    }

    @Test
    public void shouldCardPaymentApproved() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, validCode);
        cardPage.bankApprovedOperation();
        Assertions.assertEquals("APPROVED", SQLHelper.getCardPayment());

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
    public void paymentByCardWithExpiredMonthlyValidity() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        var currentYear = DataHelper.getRandomYear(0);
        var monthExpired = DataHelper.getRandomMonth(-2);
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(approvedCardNumber, monthExpired, currentYear, validOwnerName, validCode);
        cardPage.errorCardTermValidity();

    }

    @Test
    public void payWithACardWithTheWrongMonth() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        var invalidMonth = DataHelper.getInvalidMonth();
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(approvedCardNumber, invalidMonth, validYear, validOwnerName, validCode);
        cardPage.errorCardTermValidity();

    }

    @Test
    public void payWithACardWithAnEmptyMonth() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        var emptyMonth = DataHelper.getEmptyField();
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(approvedCardNumber, emptyMonth, validYear, validOwnerName, validCode);
        cardPage.errorFormat();
    }

    @Test
    public void paymentByCardWithExpiredAnnualValidity() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        var AnnualValidity = DataHelper.getRandomYear(-5);
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(approvedCardNumber, validMonth, AnnualValidity, validOwnerName, validCode);
        cardPage.termValidityExpired();

    }

    @Test
    public void cardPaymentWithEmptyYear() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        var emptyYear = DataHelper.getEmptyField();
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(approvedCardNumber, validMonth, emptyYear, validOwnerName, validCode);
        cardPage.errorFormat();
    }

    @Test
    public void rusLanguageNamePaymentByCard() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        var rusLanguageName = DataHelper.getRandomNameRus();
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, rusLanguageName, validCode);
        cardPage.errorFormat();
    }

    @Test
    public void digitsNameCardPayment() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        var digitsName = DataHelper.getNumberName();
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, digitsName, validCode);
        cardPage.errorFormat();
    }


    @Test
    public void specSymbolsNameCardPayment() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        var specSymbolsName = DataHelper.getSpecialCharactersName();
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, specSymbolsName, validCode);
        cardPage.errorFormat();
    }

    @Test
    public void emptyNameCardPayment() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        var emptyName = DataHelper.getEmptyField();
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, emptyName, validCode);
        cardPage.emptyField();
    }

    @Test
    public void twoDigitCardPaymentCode() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        var twoDigitCard = DataHelper.getNumberCVC(2);
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, twoDigitCard);
        cardPage.errorFormat();
    }

    @Test
    public void oneDigitInTheCardPaymentCode() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        var oneDigitInTheCard = DataHelper.getNumberCVC(1);
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, oneDigitInTheCard);
        cardPage.errorFormat();
    }

    @Test
    public void emptyPaymentFieldInTheCardCode() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        var emptyField = DataHelper.getEmptyField();
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, emptyField);
        cardPage.errorFormat();

    }

    @Test
    public void  specSymbolsCodeCardPayment() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        var specSymbolsCode = DataHelper.getSpecialCharactersName();
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, specSymbolsCode);
        cardPage.errorFormat();
    }

    @Test
    public void emptyAllFieldsCardPayment() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var cardPage = page.cardPayment();
        var emptyCardNumber = DataHelper.getEmptyField();
        var emptyMonth = DataHelper.getEmptyField();
        var emptyYear = DataHelper.getEmptyField();
        var emptyOwnerName = DataHelper.getEmptyField();
        var emptyCode = DataHelper.getEmptyField();
        cardPage.cleanFields();
        cardPage.fillCardPaymentForm(emptyCardNumber, emptyMonth, emptyYear, emptyOwnerName, emptyCode);
        cardPage.errorFormat();


    }

}




