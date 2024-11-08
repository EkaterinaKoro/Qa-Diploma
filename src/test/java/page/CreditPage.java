package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {
    private SelenideElement heading = $(withText("Кредит по данным карты"));
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    public SelenideElement cardHolderField = $("fieldset > div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    public SelenideElement cvcField = $("[placeholder='999']");
    public SelenideElement contButton = $$(".button__content").find(Condition.exactText("Продолжить"));;
    //Ошибки

    private SelenideElement formatCardErrorMap = $$(".input__sub").findBy(text("Неверный формат"));
    private SelenideElement invalidCardExpirationMonth = $$(".input__sub").findBy(text("Неверно указан срок действия карты"));
    private SelenideElement cardExpiredYear = $$(".input__sub").findBy(text("Истёк срок действия карты"));
    private SelenideElement emptyFieldOwner = $$(".input__sub").findBy((text("Поле обязательно для заполнения")));
    private SelenideElement wrongFormatCVC = $$(".input__sub").findBy((text("Неверный формат")));
    private SelenideElement bankRefusedOperation = $$(".notification__content").findBy(text("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement bankApprovedOperation = $$(".notification__content").findBy(text("Операция одобрена Банком."));

    public CreditPage() {
        heading.shouldBe(visible);
    }

    public void fillCardPaymentForm(String cardNumber, String month, String year, String owner ,String code) {
            cardNumberField.setValue(cardNumber);
            monthField.setValue(month);
            yearField.setValue(year);
            cardHolderField.setValue(owner);
            cvcField.setValue(code);
            contButton.click();

    }


    public void bankApprovedOperation() {
        bankApprovedOperation.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void bankRefusedOperation() {
        bankRefusedOperation.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void formatCardErrorMap() {
        formatCardErrorMap.shouldBe(visible);
    }

    public void invalidCardExpirationMonth() {
        invalidCardExpirationMonth.shouldBe(visible);
    }

    public void cardExpiredYear() {
        cardExpiredYear.shouldBe(visible);
    }

    public void emptyFieldOwner() {
        emptyFieldOwner.shouldBe(visible);
    }

    public void wrongFormatCVC() {
        wrongFormatCVC.shouldBe(visible);
    }

    public void cleanFields() {
        cardNumberField.doubleClick().sendKeys(Keys.BACK_SPACE);
        monthField.doubleClick().sendKeys(Keys.BACK_SPACE);
        yearField.doubleClick().sendKeys(Keys.BACK_SPACE);
        cardHolderField.doubleClick().sendKeys(Keys.BACK_SPACE);
        cvcField.doubleClick().sendKeys(Keys.BACK_SPACE);
    }

}


