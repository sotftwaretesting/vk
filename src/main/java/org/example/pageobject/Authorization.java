package org.example.pageobject;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;

public class Authorization {

    private final By enterButton = By.xpath("//form[@class='VkIdForm__form']//button[1]");
    private final By submitButton = By.xpath("//button[@type='submit']");
    private final By loginField = By.xpath("//input[@name='login']");
    private final By passwordField = By.xpath("//input[@type='password']");

    public void authorization(String login, String password) {
        Selenide.$(enterButton).click();
        Selenide.$(loginField).val(login);
        Selenide.$(submitButton).click();
        Selenide.$(passwordField).val(password);
        Selenide.$(submitButton).click();
    }


}
