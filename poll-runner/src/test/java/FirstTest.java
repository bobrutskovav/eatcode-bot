import org.junit.Test;

import java.time.OffsetDateTime;

import static com.scriptterror.ui_service.BurgerKingUIService.*;

public class FirstTest {
    @Test
    public void openMainPageTest(){
        openPageOfPoll();
        acceptTermsOfUse();
        setRestaurantCode("20328");
        setDateAndTimeOfVisit(OffsetDateTime.now());
        System.out.println("Тест Пройден");
    }
}
