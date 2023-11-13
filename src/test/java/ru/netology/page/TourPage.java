package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import java.time.Duration;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TourPage {
    private final SelenideElement header = $(withText("Путешествие дня"));
    private final SelenideElement paymentButton = $(withText("Купить"));
    private final SelenideElement creditButton = $(withText("Купить в кредит"));

    public TourPage() {
        header.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public PaymentPage selectToPayWithOneSum() {
        paymentButton.click();
        return new PaymentPage();
    }

    public CreditPage selectToPayWithCredit() {
        creditButton.click();
        return new CreditPage();
    }

}
