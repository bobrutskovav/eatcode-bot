package com.scriptterror.ui_service;

import com.codeborne.selenide.Condition;

import java.time.OffsetDateTime;
import java.util.Date;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class BurgerKingUIService {

    public static void openPageOfPoll(){
        open("https://www.bkguestfeedbackrussia.com/");
    }

    public static void acceptTermsOfUse(){
        $("#NextButton").click();
    }

    public static void setRestaurantCode(String restaurantCode){
        $("#SurveyCode").waitUntil(Condition.appear, 2).sendKeys(restaurantCode);
    }

    public static void setDateAndTimeOfVisit(OffsetDateTime dateTime){
        setDate(dateTime);
        setTime(dateTime);
        clickNextButton();
    }

    private static void clickNextButton(){
        $("#NextButton").click();
    }

    private static void setTime(OffsetDateTime dateTime) {
        int tempHour = dateTime.getHour()%12 ==0? 12: dateTime.getHour()%12;
        String hour = tempHour<10 ? "0"+tempHour: String.valueOf(tempHour);
        $("#InputHour").selectOptionByValue(hour);

        String inputMinute = dateTime.getMinute() <10? "0"+dateTime.getMinute(): ""+dateTime.getMinute();
        $("#InputMinute").selectOptionByValue(inputMinute);

        String inputMeridian = dateTime.getHour()<12 ? "AM": "PM";
        $("#InputMeridian").selectOptionByValue(inputMeridian);
    }

    private static void setDate(OffsetDateTime dateTime) {
        int day =dateTime.getDayOfMonth();
        String inputDay = day <10? "0"+day: ""+day;
        $("#InputDay").selectOptionByValue(inputDay);

        int month = dateTime.getMonthValue();
        String inputMonth = month <10? "0"+ month: ""+month;
        $("#InputMonth").selectOptionByValue(inputMonth);

        $("#InputYear").selectOptionByValue(String.valueOf(dateTime.getYear()).replace("20",""));
    }



}
