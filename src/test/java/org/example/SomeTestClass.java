package org.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.example.step.Step;
import org.example.testng.RetryAnalyzer;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;

import static com.codeborne.selenide.Selenide.open;

public class SomeTestClass {

    private static final String FIRST_LOGIN = "Login";
    private static final String FIRST_PASSWORD = "Password";

    private static final String SECOND_LOGIN = "Login";
    private static final String SECOND_PASSWORD = "Password";

    @BeforeClass
    public void maximizeWindow() {
        Configuration.browserSize = "1920x1080";
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }

    @BeforeMethod
    public void openBrowser() {
        String targetUrl = "http://vk.com";
        open(targetUrl);
    }

    @AfterMethod
    public void tearDown() {
        Selenide.closeWebDriver();
    }

    //all action sequence should be: page object action -> step action-> test
    //was created only one sequence, for pet project is appropriate
    //all locators should be stored in variables. avoid magic variables
    //should be created several step classes
    //wait key element visibility after moving to new page
    //selenide attachment to allure wasn't tested
    //improve xpath locators
    //parallel execution was done by params in testng.xml but doesn't work properly
    //it can be done by executorservice and new Testng("suitename")
    //need to resolve problem with captcha
    @org.testng.annotations.Test(retryAnalyzer = RetryAnalyzer.class)
    public void testChatCreation() {
        String chatParticipation = "Ольга Лебедева";
        String chatName = "123";

        Step step = new Step();
        step.doAuthorization(FIRST_LOGIN, FIRST_PASSWORD);
        step.chatCreation(chatParticipation, chatName);

        step.assertChatCreation(chatName);
    }

    @org.testng.annotations.Test(retryAnalyzer = RetryAnalyzer.class)
    public void testAttachmentCreationInChat() {
        File attachment = new File("src/test/java/resources/1656934826787.0.png");
        String chatParticipation = "Ольга Лебедева";
        String chatName = "123";

        Step step = new Step();
        step.doAuthorization(SECOND_LOGIN, SECOND_PASSWORD);
        step.chatCreation(chatParticipation, chatName);
        step.putAttachmentInChat(Step.AttachmentType.PHOTO, attachment);

        step.assertAttachment();
    }
}
