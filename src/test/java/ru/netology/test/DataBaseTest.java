package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.TourPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class DataBaseTest {
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
    public void approvedIfApprovedCardPayment() {
        var paymentPage = tourPage.selectToPayWithOneSum();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(4);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        paymentPage.payment(info);
        paymentPage.positiveNotification();
        Assertions.assertEquals("APPROVED", SQLHelper.getPaymentStatus());
        Assertions.assertEquals(4500000, SQLHelper.getPaymentAmount());
    }

    @Test
    public void declinedIfDeclinedCardPayment() {
        var paymentPage = tourPage.selectToPayWithOneSum();
        var cardNumber = DataHelper.getDeclinedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(4);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        paymentPage.payment(info);
        paymentPage.negativeNotification();
        Assertions.assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }

    @Test
    public void approvedIfApprovedCardCredit() {
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(4);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.positiveNotification();
        Assertions.assertEquals("APPROVED", SQLHelper.getCreditRequestStatus());
    }

    @Test
    public void declinedIfDeclinedCardCredit() {
        var creditPage = tourPage.selectToPayWithCredit();
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.generateMonth(1);
        var year = DataHelper.generateYear(4);
        var owner = DataHelper.generateOwner("en");
        var cvc = DataHelper.generateCVC();
        DataHelper.AuthInfo info = new DataHelper.AuthInfo(cardNumber, month, year, owner, cvc);
        creditPage.payment(info);
        creditPage.negativeNotification();
        Assertions.assertEquals("DECLINED", SQLHelper.getCreditRequestStatus());
    }


}
