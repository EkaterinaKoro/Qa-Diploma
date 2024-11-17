package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;

public class CreditTest {
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
    public void shouldCreditPaymentApproved() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var creditPage = page.creditPayment();
        creditPage.cleanFields();
        creditPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, validCode);
        creditPage.bankApprovedOperation();
        Assertions.assertEquals("APPROVED", SQLHelper.getCreditPayment());

    }

    @Test
    public void shouldDeclinedCardPayment() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var creditPage = page.creditPayment();
        creditPage.cleanFields();
        creditPage.fillCardPaymentForm(declinedCardNumber, validMonth, validYear, validOwnerName, validCode);
        creditPage.bankDeclinedOperation();
        Assertions.assertEquals("Declined", SQLHelper.getCreditPayment());
    }


    @Test
    public void shouldIncorrectCreditPaymenNumber() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var creditPage = page.creditPayment();
        var invalidCardNumber = DataHelper.GetAShortNumber();
        creditPage.cleanFields();
        creditPage.fillCardPaymentForm(invalidCardNumber, validMonth, validYear, validOwnerName, validCode);
        creditPage.errorFormat();
    }

    @Test
    public void shouldMustBlankFieldCardNumberCreditPayment() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var creditPage = page.creditPayment();
        var emptyCardNumber = DataHelper.getEmptyField();
        creditPage.cleanFields();
        creditPage.fillCardPaymentForm(emptyCardNumber, validMonth, validYear, validOwnerName, validCode);
        creditPage.errorFormat();

    }

    @Test
    public void creditPaymentByCardWithExpiredMonthlyValidity() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var creditPage = page.creditPayment();
        var currentYear = DataHelper.getRandomYear(0);
        var monthExpired = DataHelper.getRandomMonth(-2);
        creditPage.cleanFields();
        creditPage.fillCardPaymentForm(approvedCardNumber, monthExpired, currentYear, validOwnerName, validCode);
        creditPage.errorCardTermValidity();

    }

    @Test
    public void payWithACardWithTheWrongMonth() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var creditPage = page.creditPayment();
        var invalidMonth = DataHelper.getInvalidMonth();
        creditPage.cleanFields();
        creditPage.fillCardPaymentForm(approvedCardNumber, invalidMonth, validYear, validOwnerName, validCode);
        creditPage.errorCardTermValidity();

    }

    @Test
    public void payWithACreditCardWithAnEmptyMonth() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var creditPage = page.creditPayment();
        var emptyMonth = DataHelper.getEmptyField();
        creditPage.cleanFields();
        creditPage.fillCardPaymentForm(approvedCardNumber, emptyMonth, validYear, validOwnerName, validCode);
        creditPage.errorFormat();
    }

    @Test
    public void creditPaymentByCardWithExpiredAnnualValidity() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var creditPage = page.creditPayment();
        var AnnualValidity = DataHelper.getRandomYear(-5);
        creditPage.cleanFields();
        creditPage.fillCardPaymentForm(approvedCardNumber, validMonth, AnnualValidity, validOwnerName, validCode);
        creditPage.termValidityExpired();

    }

    @Test
    public void creditCardPaymentWithEmptyYear() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var creditPage = page.creditPayment();
        var emptyYear = DataHelper.getEmptyField();
        creditPage.cleanFields();
        creditPage.fillCardPaymentForm(approvedCardNumber, validMonth, emptyYear, validOwnerName, validCode);
        creditPage.errorFormat();
    }

    @Test
    public void rusLanguageNamePaymentByCreditCard() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var creditPage = page.creditPayment();
        var rusLanguageName = DataHelper.getRandomNameRus();
        creditPage.cleanFields();
        creditPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, rusLanguageName, validCode);
        creditPage.errorFormat();
    }

    @Test
    public void digitsNameCardCreditPayment() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var creditPage = page.creditPayment();
        var digitsName = DataHelper.getNumberName();
        creditPage.cleanFields();
        creditPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, digitsName, validCode);
        creditPage.errorFormat();
    }


    @Test
    public void specSymbolsNameCreditPayment() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var creditPage = page.creditPayment();
        var specSymbolsName = DataHelper.getSpecialCharactersName();
        creditPage.cleanFields();
        creditPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, specSymbolsName, validCode);
        creditPage.errorFormat();
    }

    @Test
    public void emptyNameCreditPayment() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var creditPage = page.creditPayment();
        var emptyName = DataHelper.getEmptyField();
        creditPage.cleanFields();
        creditPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, emptyName, validCode);
        creditPage.emptyField();
    }

    @Test
    public void twoDigitCreditPaymentCode() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var creditPage = page.creditPayment();
        var twoDigitCard = DataHelper.getNumberCVC(2);
        creditPage.cleanFields();
        creditPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, twoDigitCard);
        creditPage.errorFormat();
    }

    @Test
    public void oneDigitInTheCreditPaymentCode() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var creditPage = page.creditPayment();
        var oneDigitInTheCard = DataHelper.getNumberCVC(1);
        creditPage.cleanFields();
        creditPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, oneDigitInTheCard);
        creditPage.errorFormat();
    }

    @Test
    public void emptyFieldInTheCreditCardCode() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var creditPage = page.creditPayment();
        var emptyField = DataHelper.getEmptyField();
        creditPage.cleanFields();
        creditPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, emptyField);
        creditPage.errorFormat();

    }

    @Test
    public void  specSymbolsCodeCreditCardPayment() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var creditPage = page.creditPayment();
        var specSymbolsCode = DataHelper.getSpecialCharactersName();
        creditPage.cleanFields();
        creditPage.fillCardPaymentForm(approvedCardNumber, validMonth, validYear, validOwnerName, specSymbolsCode);
        creditPage.errorFormat();
    }

    @Test
    public void emptyAllFieldsCreditCardPayment() {
        PaymentPage page = new PaymentPage();
        page.paymentPage();
        var creditPage = page.creditPayment();
        var emptyCardNumber = DataHelper.getEmptyField();
        var emptyMonth = DataHelper.getEmptyField();
        var emptyYear = DataHelper.getEmptyField();
        var emptyOwnerName = DataHelper.getEmptyField();
        var emptyCode = DataHelper.getEmptyField();
        creditPage.cleanFields();
        creditPage.fillCardPaymentForm(emptyCardNumber, emptyMonth, emptyYear, emptyOwnerName, emptyCode);
        creditPage.errorFormat();


    }

}



