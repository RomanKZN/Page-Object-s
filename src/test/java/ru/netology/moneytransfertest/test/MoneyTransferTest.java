package ru.netology.moneytransfertest.test;

import com.codeborne.selenide.Configuration;
import lombok.var;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.dashboardpage.page.DashboardPage;
import ru.netology.dashboardpage.page.LoginPage1;
import ru.netology.dashboardpage.page.MoneyTransfer;
import ru.netology.datahelper.data.DataHelper;

import static com.codeborne.selenide.Selenide.*;

public class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        Configuration.holdBrowserOpen = true;
    }

    @AfterEach
    void memoryClear() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    @Test
    void shouldTransferMoneyFromFirstToSecond() {
        var dashboardPage = new DashboardPage();
        int expected = dashboardPage.getCardBalance("2")+1000;
        dashboardPage.depositToSecond();
        var transferPage = new MoneyTransfer();
        transferPage.moneyTransfer(DataHelper.CardInfo.getCardOne(), "1000");
        int actual = dashboardPage.getCardBalance("2");

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void shouldGetErrorMessageIfAmountMoreBalance (){
        var LoginPage1 = open("http://localhost:9999",ru.netology.dashboardpage.page.LoginPage1.class);
        var authInfo = DataHelper.getAuthInfo();
    }
//    @Test
//    void mustNotTransferAnAmountGreaterThanTheBalance() {
//        var dashboardPage = new DashboardPage();
//        int expected = dashboardPage.getCardBalance("2")+1000;
//        dashboardPage.depositToSecond();
//        var transferPage = new MoneyTransfer();
//        transferPage.moneyTransfer(DataHelper.CardInfo.getCardOne(), "50000");
//        int actual = dashboardPage.getCardBalance("2");
//
//        Assertions.assertEquals(expected, actual);
//
//    }

}
