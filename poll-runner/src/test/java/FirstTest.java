import com.scriptterror.ui_service.BurgerKingUIService;
import org.junit.Test;

import java.time.OffsetDateTime;

public class FirstTest {
    @Test
    public void openMainPageTest(){

        BurgerKingUIService service = new BurgerKingUIService();
        service.openPageOfPoll_step1();
        service.acceptTermsOfUse_step2();
        service.setRestaurantCode_step3("19641");
        service.setDateAndTimeOfVisit_step4(OffsetDateTime.now());
        service.selectTypeOfOrder_step5();
        service.setHowAreYouDidTheOrder_step6();
        service.setNumberOfPersons_step7();
        service.setWasThereOrderForChild_step8();
        service.startRandomPoll();
        service.setSexAgeSalary();
        System.out.println(service.getPromoCode());
        System.out.println("Тест Пройден");
    }
}
