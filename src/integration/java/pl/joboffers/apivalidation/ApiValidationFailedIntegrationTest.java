package pl.joboffers.apivalidation;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.joboffers.BaseIntegrationTest;
import pl.joboffers.infrastructure.apivalidation.ApiValidationErrorDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {

    @Test
    public void should_return_400_bad_request_validation_message_when_request_has_all_blank_and_null_save_request() throws Exception {
        //given
        //when
        ResultActions performPost = mockMvc.perform(post("/offers")
                .content(
                        """
                                {
                                "companyName": "",
                                "position": "",
                                "salary": ""
                                }
                                """)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        MvcResult mvcResult = performPost.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto result = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertThat(result.messages()).containsExactlyInAnyOrder(
                "companyName must not be empty",
                "position must not be empty",
                "salary must not be empty",
                "offerUrl must not be empty",
                "offerUrl must not be null"
                );
    }

//    public void should_return_409_conflict_when_there_is_already_added_offer_with_exact_same_url() throws Exception {
//        //given
//        //when
//        ResultActions performPost = mockMvc.perform(post("/offers")
//                .content(
//                        """
//                                {
//                                "companyName": "Javappa",
//                                "position": "Java Developer",
//                                "salary": 6000,
//                                "offerUrl": "https://nofluffjobs.com/pl/job/mid-java-developer-stackmine-poznan-1"
//                                }
//                                """)
//                .contentType(MediaType.APPLICATION_JSON)
//        );
//
//        ResultActions performPost2 = mockMvc.perform(post("/offers")
//                .content(
//                        """
//                                {
//                                "companyName": "Javappa",
//                                "position": "Java Developer",
//                                "salary": 6000,
//                                "offerUrl": "https://nofluffjobs.com/pl/job/mid-java-developer-stackmine-poznan-1"
//                                }
//                                """)
//                .contentType(MediaType.APPLICATION_JSON)
//        );
//
//        //then
//        performPost2.andExpect(status().isConflict())
//                .andExpect(content().json(
//                        """
//                                """.trim()
//                ));
//    }
}
