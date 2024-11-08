package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardPage {

    // Поля
    private final SelenideElement heading = $(withText("Оплата по карте"));
    private final SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $("[placeholder='08']");
    private final SelenideElement yearField = $("[placeholder='22']");
    private final SelenideElement HolderField = $("fieldset > div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private final SelenideElement codeField = $("[placeholder='999']");
    private final SelenideElement contButton = $$(".button__content").find(Condition.exactText("Продолжить"));

    //Ошибки

    private final SelenideElement errorFormat = $$(".input__inner").findBy(text("Неверный формат"));
    private final SelenideElement emptyField = $$("span.input__top span.input__box .input__inner").findBy(text("Поле обязательно для заполнения"));
    private final SelenideElement invalidCardExpirationMonth = $$(".input__inner").findBy(text("Неверно указан срок действия карты"));
    private final SelenideElement cardExpiredYear = $$(".input__inner").findBy(text("Истёк срок действия карты"));
    private final SelenideElement bankRefusedOperation = $$(".notification__content").findBy(text("Ошибка! Банк отказал в проведении операции."));
    private final SelenideElement bankApprovedOperation = $$(".notification__content").findBy(text("Операция одобрена Банком."));


    public CardPage() {

        heading.shouldBe(visible);
    }

    public void fillCardPaymentForm(String cardNumber, String month, String year, String owner, String code) {
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        HolderField.setValue(owner);
        codeField.setValue(code);
        contButton.click();
    }


    public void bankApprovedOperation() {

        bankApprovedOperation.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void bankDeclinedOperation() {

        bankRefusedOperation.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void errorFormat() {

        errorFormat.shouldBe(visible);
    }
    public void emptyField() {
        emptyField.shouldBe(visible);
}

public void invalidCardExpirationMonth() {

        invalidCardExpirationMonth.shouldBe(visible);
}

public void cardExpiredYear() {

        cardExpiredYear.shouldBe(visible);
}

public void emptyFieldOwner() {

}

public void cleanFields() {
    cardNumberField.doubleClick().sendKeys(Keys.BACK_SPACE);
    monthField.doubleClick().sendKeys(Keys.BACK_SPACE);
    yearField.doubleClick().sendKeys(Keys.BACK_SPACE);
    HolderField.doubleClick().sendKeys(Keys.BACK_SPACE);
    codeField.doubleClick().sendKeys(Keys.BACK_SPACE);
}


    }

