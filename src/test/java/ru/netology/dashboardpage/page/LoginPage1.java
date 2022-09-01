package ru.netology.dashboardpage.page;

import org.openqa.selenium.By;
import ru.netology.datahelper.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage1 {

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=password] input").setValue(info.getPassword());
        $(By.className("button__text")).click();
        return new VerificationPage();
    }
}
