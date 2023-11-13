package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {
    private final SelenideElement header = $(withText("Кредит по данным карты"));
    private final SelenideElement cardNumberField = $(byText("Номер карты")).parent().find(".input__control");
    private final SelenideElement monthField = $(withText("Месяц")).parent().find(".input__control");
    private final SelenideElement yearField = $(withText("Год")).parent().find(".input__control");
    private final SelenideElement ownerField = $(withText("Владелец")).parent().find(".input__control");
    private final SelenideElement cvcField = $(withText("CVC/CVV")).parent().find(".input__control");
    private final SelenideElement button = $(withText("Продолжить"));
    private final SelenideElement positiveNotification = $(withText("Операция одобрена Банком"));
    private final SelenideElement negativeNotification = $(withText("Банк отказал в проведении операции"));
    private final SelenideElement invalidInput = $(".input__sub");

    public CreditPage() {
        header.shouldBe(Condition.visible);
    }

    public void payment(DataHelper.AuthInfo info) {
        cardNumberField.setValue(info.getCardNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        ownerField.setValue(info.getOwner());
        cvcField.setValue(info.getCvc());
        button.click();
    }

    public void positiveNotification() {
        positiveNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    public void negativeNotification() {
        negativeNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    public void invalidInput(String text) {
        invalidInput.shouldBe(Condition.visible).shouldHave(Condition.text(text));
    }
}
