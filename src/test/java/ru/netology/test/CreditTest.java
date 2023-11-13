package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.TourPage;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class CreditTest {
    TourPage tourPage;

    @BeforeEach
    void setup() {
        tourPage = open("http://localhost:8080", TourPage.class);
    }

    @AfterAll
    static void clean() {
        cleanDatabase();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    public void testValidPayment() { //плата одобренной картой с валидными данными единым платежем
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(4);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.positiveNotification();
    }

    @Test
    public void testDeclinedPayment() { //плата отклоненной картой
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getDeclinedCardNumber();
        var month = DataHelper.generateMonth(-4);
        var year = DataHelper.generateYear(2);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.negativeNotification();
    }

    @Test
    public void testZeroCardNumberPayment() { //плата с номером карты, состоящим из нулей
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getInvalidCardNumber();
        var month = DataHelper.generateMonth(-10);
        var year = DataHelper.generateYear(3);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.negativeNotification();
    }

    @Test
    public void testOneDigitCardNumberPayment() { //плата с номером карты, состоящим из одной цифры
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.generateOneDigit();
        var month = DataHelper.generateMonth(-3);
        var year = DataHelper.generateYear(2);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Неверный формат");
    }

    @Test
    public void testFifteenDigitCardNumberPayment() { //плата с номером карты, состоящим из 15 цифр
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.generateCardNumberFifteenDigit();
        var month = DataHelper.generateMonth(-5);
        var year = DataHelper.generateYear(3);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Неверный формат");
    }

    @Test
    public void testNoCardNumber() { //не заполнено поле НОМЕР КАРТЫ
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.returnNull();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(4);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Поле обязательно для заполнения");
    }

    @Test
    public void testZeroMonthPayment() { //в поле МЕСЯЦ введены нули
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        String month = "00";
        var year = DataHelper.generateYear(1);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Неверно указан срок действия карты");
    }

    @Test
    public void testThirteenMonthPayment() { //в поле МЕСЯЦ введено значение 13
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        String month = "13";
        var year = DataHelper.generateYear(2);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Неверно указан срок действия карты");
    }

    @Test
    public void testInvalidMonthPayment() { //в поле МЕСЯЦ введено слишком большое значение
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        String month = "28";
        var year = DataHelper.generateYear(5);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Неверно указан срок действия карты");
    }

    @Test
    public void testOneDigitMonthPayment() { //в поле МЕСЯЦ введена одна цифра
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateOneDigit();
        var year = DataHelper.generateYear(4);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Неверный формат");
    }

    @Test
    public void testNoMonthPayment() { //не заполнено поле МЕСЯЦ
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.returnNull();
        var year = DataHelper.generateYear(3);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Поле обязательно для заполнения");
    }

    @Test
    public void testExpiredYearPayment() { //введено значение истекшего срока действия карты
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(-2);
        var year = DataHelper.generateYear(-18);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Истёк срок действия карты");
    }

    @Test
    public void testLastYearPayment() { //в поле ГОД введено значение предыдущего года
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(-3);
        var year = DataHelper.generateYear(-1);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Истёк срок действия карты");
    }
    @Test
    public void testInvalidYearPayment() { //в поле ГОД введено значение? ольше значения текущего года на 6
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(6);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Неверно указан срок действия карты");
    }

    @Test
    public void testFurtherYearPayment() { //в поле ГОД введено слишком большое значение
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(54);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Неверно указан срок действия карты");
    }

    @Test
    public void testLastMonthThisYearPayment() { //в поля МЕСЯЦ и ГОД введены значения предыдущего месяца этого года
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(-1);
        var year = DataHelper.generateYear(0);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Истёк срок действия карты");
    }

    @Test
    public void testOneDigitYearPayment() { //в поле ГОД введена одна цифра
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(-9);
        var year = DataHelper.generateOneDigit();
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Неверный формат");
    }

    @Test
    public void testNoYearPayment() { //не заполнено поле ГОД
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(-2);
        var year = DataHelper.returnNull();
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Поле обязательно для заполнения");
    }

    @Test
    public void testOneDigitCVCPayment() { //в поле CVC/CVV введена одна цифра
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(-7);
        var year = DataHelper.generateYear(4);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateOneDigit();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Неверный формат");
    }

    @Test
    public void testTwoDigitsCVCPayment() { //в поле CVC/CVV введен две цифры
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(-8);
        var year = DataHelper.generateYear(2);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateTwoDigits();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Неверный формат");
    }

    @Test
    public void testNoCVCPayment() { //не заполнено CVC/CVV
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(-8);
        var year = DataHelper.generateYear(3);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.returnNull();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Поле обязательно для заполнения");
    }

    @Test
    public void testOneWordOwnerPayment() { //в поле ВЛАДЕЛЕЦ введено одно слово
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(1);
        var owner = DataHelper.generateOwnerUsingOnlyFirstName("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Неверный формат");
    }

    @Test
    public void testCyrillicOwnerPayment() { //в поле ВЛАДЕЛЕЦ введено значение, написанное кириллицей
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(-7);
        var year = DataHelper.generateYear(2);
        var owner = DataHelper.generateOwner("ru");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Неверный формат");
    }

    @Test
    public void testSpecialSymbolsOwnerPayment() { //в поле ВЛАДЕЛЕЦ введено значение, содержащее спецсимволы
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(0);
        var year = DataHelper.generateYear(3);
        var owner = DataHelper.generateOwnerWithSpecialSymbols("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Неверный формат");
    }

    @Test
    public void testDigitsOwnerPayment() { //в поле ВЛАДЕЛЕЦ введено значение из цифр
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(-2);
        var year = DataHelper.generateYear(4);
        var owner = DataHelper.generateOwnerOfNumbers();
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Неверный формат");
    }

    @Test
    public void testNoOwnerPayment() { //не заполнено поле ВЛАДЕЛЕЦ
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(-8);
        var year = DataHelper.generateYear(1);
        var owner = DataHelper.returnNull();
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.invalidInput("Поле обязательно для заполнения");
    }
}
