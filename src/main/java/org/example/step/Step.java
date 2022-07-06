package org.example.step;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.example.pageobject.Authorization;
import org.openqa.selenium.By;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.actions;

public class Step {

    @io.qameta.allure.Step("Войти под: {login}/{password}")
    public void doAuthorization(String login, String password) {
        new Authorization().authorization(login, password);
    }

    @io.qameta.allure.Step("Проверка создания чата")
    public void chatCreation(String addedUserName, String chatName) {
        Selenide.$(By.xpath("//li[@id='l_msg']/a")).click();
        SelenideElement element = Selenide.element(By.xpath("(//div[@class='ui_search_custom_ctrl']//button)[3]"));
        actions().moveToElement(element).click(element).perform();
        Selenide.$(By.xpath(String.format("//div[text()='%s']", addedUserName))).click();
        Selenide.$(By.xpath("//div[@class='im-create--footer ui_grey_block']//input[@type='text']")).val(chatName);
        Selenide.$(By.xpath("//div[@class='im-create--footer ui_grey_block']//button")).click();
    }

    public void assertChatCreation(String chatName) {
        Selenide.$(By.xpath(
                        String.format("//div[@class='im-page--chat-header _im_dialog_actions im-page--chat-header_chat']//a[text()='%s']", chatName)))
                .shouldBe(Condition.visible, Duration.ofSeconds(2));
        Selenide.$(By.xpath(String.format("//div[@class='im-right-menu']//div[@class='_im_ui_peers_list']/a[@title='%s']", chatName)))
                .shouldBe(Condition.visible, Duration.ofSeconds(2));
    }

    @io.qameta.allure.Step
    public void putAttachmentInChat(AttachmentType attachmentType, File file) {
        Selenide.$(By.xpath("//div[@class='_im_media_selector im-chat-input--selector']//a[@class='ms_item_more']")).click();
        Selenide.$(By.xpath(String.format("//div[@class='ms_items_more_helper']//a[text()='%s']", attachmentType.getValue()))).click();
        switch (attachmentType) {
            case PHOTO:
                attachPhoto(file);
                break;
            default:
                throw new IllegalArgumentException("Behaviour not define for:" + attachmentType);
        }
    }

    public void assertAttachment() {
        Selenide.$(By.xpath("//div[@class='im-mess--text wall_module _im_log_body']//a[@aria-label='фотография']"))
                .shouldBe(Condition.visible, Duration.ofSeconds(2));
    }

    private void attachPhoto(File file) {
        Selenide.$(By.xpath("//span[@class='photos_choose_upload_area_label']")).shouldBe(Condition.interactable);
        Selenide.$(By.xpath("//input[@type='file' and @name='photo']")).uploadFile(file);
        Selenide.$(By.xpath("//img[@class='preview _preview']")).shouldBe(Condition.visible, Duration.ofSeconds(5));
        Selenide.$(By.xpath("(//span[@class='im-send-btn__icon im-send-btn__icon--send'])[2]")).click();
    }

    public enum AttachmentType {
        PHOTO("Фотография");
        final String value;

        AttachmentType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
