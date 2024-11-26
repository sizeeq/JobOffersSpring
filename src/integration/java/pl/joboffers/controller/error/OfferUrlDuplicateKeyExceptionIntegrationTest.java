package pl.joboffers.controller.error;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import pl.joboffers.BaseIntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OfferUrlDuplicateKeyExceptionIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_return_409_conflict_when_user_added_second_offer_with_same_offer_url() throws Exception {
        //step 1
        //given && when
        ResultActions performPostOffer = mockMvc.perform(post("/offers")
                .content(
                        """
                                {
                                "companyName": "JavAPPa",
                                "position": "Java Developer",
                                "salary": 6000,
                                "offerUrl": "https://nofluffjobs.com/pl/job/mid-java-developer-stackmine-poznan-1"
                                }
                                """
                )
                .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
        );
        //then
        performPostOffer.andExpect(status().isCreated());

        //step 2
        //given && when
        ResultActions performPostOffer2 = mockMvc.perform(post("/offers")
                .content(
                        """
                                {
                                "companyName": "JavAPPa",
                                "position": "Java Developer",
                                "salary": 6000,
                                "offerUrl": "https://nofluffjobs.com/pl/job/mid-java-developer-stackmine-poznan-1"
                                }
                                """
                )
                .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
        );
        //then
        performPostOffer2.andExpect(status().isConflict());
    }
}
