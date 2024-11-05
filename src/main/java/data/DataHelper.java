package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

import static java.lang.String.format;

public class DataHelper {
    public static final Faker faker = new Faker(new Locale("en"));
    private static Faker fakerRus = new Faker(new Locale("ru"));
    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String holder;
        String codCvcCvv;

    public static String getApprovedCardNumber() {
        return ("4444 4444 4444 4441");
    }

    public static String etDeclinedCardNumber() {
        return ("4444 4444 4444 4442");
    }

    // "Номер карты" не валидными значениями
    public static CardInfo getCardNumberZero() {
        return new CardInfo("0000000000000000", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardNumberEmpty() {
        return new CardInfo("", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getCardNumberLess() {
        return new CardInfo("2222222222", getValidMonth(), getValidYear(), getValidHolder(), getValidCVC());
    }

    // Поле месяц
    public static String getValidMonth() {
        LocalDate localDate = LocalDate.now();
        return format("%02d", localDate.getMonthValue());
    }

    public static CardInfo getMonthZero() {
        return new CardInfo(getApprovedCardNumber(), "00", getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getMonthEmpty() {
        return new CardInfo(getApprovedCardNumber(), "", getValidYear(), getValidHolder(), getValidCVC());
    }

    public static CardInfo getMonthOneDigit() {
        return new CardInfo(getApprovedCardNumber(), "2", getValidYear(), getValidHolder(), getValidCVC());
    }

    public static String getValidYear() {
        Random random = new Random();
        int year = random.nextInt(3);
        return LocalDate.now().plusYears(year).format(DateTimeFormatter.ofPattern("yy"));

    }
// Поле год

    public static String getPastYear() {
        LocalDate localDate = LocalDate.now();
        return String.format("%d", localDate.minusYears(1));

    }

    public static CardInfo getYearEmpty() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "", getValidHolder(), getValidCVC());
    }

    public static CardInfo getYearOneDigit() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "2", getValidHolder(), getValidCVC());
    }

    // Поле Владелец
    public static String getValidHolder() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().fullName();

    }

    public static CardInfo getHolderEmpty() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "", getValidCVC());
    }

    public static CardInfo getHolderCyrillic() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "Мария", getValidCVC());
    }

    public static CardInfo getHolderDigits() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "8776666", getValidCVC());
    }

    public static CardInfo getHolderSymbol() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "Maria**", getValidCVC());
    }

    //// Заполнение поля "CVC/CVV"
    public static String getValidCVC() {
        return ("123");
    }

    public static CardInfo getCVCCempty() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "");
    }

    public static CardInfo getCVCTwoDigit() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "44");
    }

    public static CardInfo getCVCOneDigit() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "5");
    }


    }
}