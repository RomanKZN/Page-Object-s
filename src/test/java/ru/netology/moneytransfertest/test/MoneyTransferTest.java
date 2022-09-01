package ru.netology.moneytransfertest.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import ru.netology.dashboardpage.page.LoginPage1;
import ru.netology.datahelper.data.DataHelper;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        open("http://localhost:9999/");
        Configuration.holdBrowserOpen = true;
        var authInfo = DataHelper.getAuthInfo();
        new LoginPage1()
                .validLogin(authInfo);
        //.validVerify(DataHelper.getVerificationCodeFor(authInfo));

    }
}
