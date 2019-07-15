import com.scriptterror.pollrunner.service.BurgerKingUIService;
import org.junit.Test;

import java.time.OffsetDateTime;

public class FirstTest {
    @Test
    public void openMainPageTest(){

        BurgerKingUIService service = new BurgerKingUIService();
        service.openPageOfPollStep1();
        service.acceptTermsOfUseStep2();
        service.setRestaurantCodeStep3("19641");
        service.setDateAndTimeOfVisitStep4(OffsetDateTime.now());
        service.selectTypeOfOrderStep5();
        service.setHowAreYouDidTheOrderStep6();
        service.setNumberOfPersonsStep7();
        service.setWasThereOrderForChildStep8();
        service.startRandomPoll();
        service.setSexAgeSalary();
        System.out.println(service.getPromoCode());
        System.out.println("Тест Пройден");
    }
}
