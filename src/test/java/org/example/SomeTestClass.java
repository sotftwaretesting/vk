package org.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.example.step.Step;
import org.example.testng.RetryAnalyzer;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import static com.codeborne.selenide.Selenide.open;

public class SomeTestClass {

    private static final String FIRST_LOGIN = "SomeLogin";
    private static final String FIRST_PASSWORD = "SomePassword";

    private static final String SECOND_LOGIN = "SomeLogin";
    private static final String SECOND_PASSWORD = "SomePassword";

    @BeforeClass
    public void maximizeWindow() {
        Configuration.browserSize = "1920x1080";
    }

    @BeforeMethod
    public void openBrowser() {
        String targetUrl = "http://vk.com";
        open(targetUrl);
    }

    @AfterMethod
    public void tearDown(){
        Selenide.closeWebDriver();
    }

    //all action sequence should be: page object action -> step action-> test
    //was created only one sequence, for pet project is appropriate
    //all locators should be stored in variables. avoid magic variables
    //should be created several step classes
    //tests doesn't work stable.
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
    public void second() {
        String resourcePath = "1656934826787.0.png";
        String chatParticipation = "Ольга Лебедева";
        String chatName = "123";
        Step step = new Step();

        step.doAuthorization(SECOND_LOGIN, SECOND_PASSWORD);
        step.chatCreation(chatParticipation, chatName);
        step.putAttachmentInChat(Step.AttachmentType.PHOTO, resourcePath);

        step.assertAttachment();
    }
}
