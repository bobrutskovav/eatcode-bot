package com.scriptterror.pollrunner.service;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.codeborne.selenide.Selenide.*;


@Service
public class BurgerKingUIService implements CanGetCodeFromPoll {

    private final Random random = new Random();

    public void openPageOfPollStep1() {
        open("https://www.bkguestfeedbackrussia.com/");
    }

    public void acceptTermsOfUseStep2() {
        $("#NextButton").click();
    }

    public void setRestaurantCodeStep3(String restaurantCode) {
        $("#SurveyCode").waitUntil(Condition.appear, 2).sendKeys(restaurantCode);
    }

    public void setDateAndTimeOfVisitStep4(OffsetDateTime dateTime) {
        setDate(dateTime);
        setTime(dateTime);
        clickNextButton();
    }

    public void selectTypeOfOrderStep5() {
        int numberOfType = random.nextInt(3);
        $$(".radioSimpleInput").get(numberOfType).click();
        clickNextButton();
    }

    //Выбирается только "На месте" или "С собой"
    public void setHowAreYouDidTheOrderStep6() {
        int numberOfVersion = random.nextInt(2);
        $$(".radioSimpleInput").get(numberOfVersion).click();
        clickNextButton();
    }

    public void setNumberOfPersonsStep7() {
        int numberOfPersons = random.nextInt(3) + 1;
        $$(".radioSimpleInput").get(numberOfPersons).click();
        clickNextButton();
    }

    //появляется только если количество персон > 1
    public void setWasThereOrderForChildStep8() {
        int numberOfPersons = random.nextInt(2);
        $$(".radioSimpleInput").get(numberOfPersons).click();
        clickNextButton();
    }

    public void startRandomPoll() {
        SelenideElement textArea = $(By.xpath("//textarea"));
        SelenideElement table = $(By.xpath("//tbody"));
        SelenideElement endOfRandomPoll = $(By.xpath("//*[.='Последний блок вопросов необходим только в целях классификации полученной информации.']"));
        List<SelenideElement> checkBoxes = $$(".checkboxSimpleInput");
        List<SelenideElement> radioButtons = $$(".radioSimpleInput");
        do {
            if (table.exists()) {
                setTableRadioButtons();
            } else if (!textArea.exists()){
                if (checkBoxes.size() > 0) {
                    setCheckboxes();
                } else if (radioButtons.size() > 0) {
                    setRadioButtons();
                }
            } else {
                textArea.sendKeys("Не хочу про это говорить");
            }
            clickNextButton();
        } while (!endOfRandomPoll.exists());

    }

    public void setSexAgeSalary(){
        List<SelenideElement> selectsOnPage = $$(By.xpath("//select"));
        for (SelenideElement selenideElement : selectsOnPage) {
            List<SelenideElement> options = selenideElement.$$(By.xpath("./option"));
            int numberOfOption = random.nextInt(options.size());
            if (numberOfOption == 0) numberOfOption++;
            selenideElement.selectOption(numberOfOption);
        }
        clickNextButton();
    }

    public String getPromoCode(){
        clickNextButton();
        return $(".ValCode").getText();
    }

    private void setTableRadioButtons() {
        List<SelenideElement> tableRows = $$(By.xpath("//tbody/tr"));
        for (int i = 1; i < tableRows.size(); i++) {
            List<SelenideElement> radioButtons = tableRows.get(i).$$(".radioSimpleInput");
            int numberOfRadio = random.nextInt(radioButtons.size());
            radioButtons.get(numberOfRadio).click();
        }
    }

    private void setRadioButtons() {
        List<SelenideElement> radioButtons = $$(".radioSimpleInput");
        int numberOfRadio = random.nextInt(radioButtons.size());
        radioButtons.get(numberOfRadio).click();
    }

    private void setCheckboxes() {
        List<SelenideElement> checkBoxes = $$(".checkboxSimpleInput");
        int numberOfCheckBox = random.nextInt(checkBoxes.size());
        checkBoxes.get(numberOfCheckBox).click();
    }

    private void clickNextButton() {
        $("#NextButton").click();
    }

    private void setTime(OffsetDateTime dateTime) {
        int tempHour = dateTime.getHour() % 12 == 0 ? 12 : dateTime.getHour() % 12;
        String hour = tempHour < 10 ? "0" + tempHour : String.valueOf(tempHour);
        $("#InputHour").selectOptionByValue(hour);

        String inputMinute = dateTime.getMinute() < 10 ? "0" + dateTime.getMinute() : "" + dateTime.getMinute();
        $("#InputMinute").selectOptionByValue(inputMinute);

        String inputMeridian = dateTime.getHour() < 12 ? "AM" : "PM";
        $("#InputMeridian").selectOptionByValue(inputMeridian);
    }

    private void setDate(OffsetDateTime dateTime) {
        int day = dateTime.getDayOfMonth();
        String inputDay = day < 10 ? "0" + day : "" + day;
        $("#InputDay").selectOptionByValue(inputDay);

        int month = dateTime.getMonthValue();
        String inputMonth = month < 10 ? "0" + month : "" + month;
        $("#InputMonth").selectOptionByValue(inputMonth);

        $("#InputYear").selectOptionByValue(String.valueOf(dateTime.getYear()).replace("20", ""));
    }


    @Override
    public String makePoll(Map<String, String> paramsForPoll) {
        return null;//ToDo сделать тут все нужные шаги.
    }
}
