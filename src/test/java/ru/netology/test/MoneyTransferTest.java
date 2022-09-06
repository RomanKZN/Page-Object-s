package ru.netology.test;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;

public class MoneyTransferTest {


    @Test
    void shouldTransferFromFirstToSecond(){
        var LoginPage1 = open("http://localhost:9999",ru.netology.page.LoginPage1.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = LoginPage1.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var amount = generateValidAmount(firstCardBalance);
        var expectedBalanceFirstCard = firstCardBalance- amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var moneyTransfer = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = moneyTransfer.makeValidTransfer(String.valueOf(amount),firstCardInfo);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedBalanceFirstCard,actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard,actualBalanceSecondCard);
    }

    @Test
    void shouldGetErrorMessageIfAmountMoreBalance(){
        var LoginPage1 = open("http://localhost:9999",ru.netology.page.LoginPage1.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = LoginPage1.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var amount = generateInvalidAmount(secondCardBalance);
        var moneyTransfer = dashboardPage.selectCardToTransfer(firstCardInfo);
        moneyTransfer.makeTransfer(String.valueOf(amount),secondCardInfo);
        moneyTransfer.findErrorMessage("Выполнена попытка перевода суммы, превышающий остаток на карте списания");
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(firstCardBalance,actualBalanceFirstCard);
        assertEquals(secondCardBalance,actualBalanceSecondCard);

    }

}
