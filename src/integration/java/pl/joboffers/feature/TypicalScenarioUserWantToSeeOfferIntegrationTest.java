package pl.joboffers.feature;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.joboffers.BaseIntegrationTest;
import pl.joboffers.SampleJobOfferResponse;
import pl.joboffers.domain.offer.OfferFetchable;
import pl.joboffers.domain.offer.dto.JobOfferResponseDto;
import pl.joboffers.domain.offer.dto.OfferResponseDto;
import pl.joboffers.infrastructure.offer.scheduler.OfferHttpClientScheduler;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TypicalScenarioUserWantToSeeOfferIntegrationTest extends BaseIntegrationTest implements SampleJobOfferResponse {

    @Autowired
    OfferFetchable offerHttpClient;

    @Autowired
    OfferHttpClientScheduler scheduler;

    @Test
    public void user_want_to_see_offers_but_have_to_be_logged_in_and_external_server_should_have_some_offers() throws Exception {
        //step 1: there are no offers in external HTTP server
        //given & when & then
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithZeroOffersJson())));

        List<JobOfferResponseDto> jobOfferResponse = offerHttpClient.fetchOffers();


        //step 2: scheduler ran 1st time and made GET to external server and system added 0 offers to database
        //given
        //when
        List<OfferResponseDto> offerResponseDtos = scheduler.fetchAllOffersAndSaveIfNotExist();

        //then
        assertThat(offerResponseDtos).isEmpty();


        //step 3: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
        //step 4: user made GET //offers with no jwt token and system returned UNAUTHORIZED(401)
        //step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status OK(200)
        //step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jtwtoken=AAAA.BBBB.CCC
        //step 7: user made GET /offers with header "Authorization: Bearer AAAA.BBBB.CCC" and system returned OK(200) with 0 offers
        //given & when
        ResultActions perform = mockMvc.perform(get("/offers")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        MvcResult mvcResult = perform.andExpect(status().isOk()).andReturn();
        String jsonWithOffers = mvcResult.getResponse().getContentAsString();
        List<OfferResponseDto> offers = objectMapper.readValue(jsonWithOffers, new TypeReference<List<OfferResponseDto>>() {
        });
        assertThat(offers).isEmpty();


        //step 8: there are 2 new offers in external HTTP server
        //step 9: scheduler ran 2nd time and made GET to external server and system added 2 new offers with id: 1000 and 2000 to database
        //step 10: user made GET //offers with header "Authorization: Bearer AAAA.BBBB.CCC" and system returned OK(200) with 2 offers with ids: 1000 and 2000


        //step 11: user made GET /offers/9999 and system returned NOT_FOUND(404) with message "Offer with id: 9999 was not found"
        //given
        //when
        ResultActions performGetNotExistingId = mockMvc.perform(get("/offers/" + "9999"));

        //then
        performGetNotExistingId.andExpect(status().isNotFound())
                .andExpect(content().json(
                        """
                                {
                                "message": "Offer with id: 9999 was not found",
                                "status": "NOT_FOUND"
                                }
                                """.trim()
                ));


        //step 12: user made GET /offers/1000 and system returned OK(200) with an offer
        //step 13: there are 2 new offers in external HTTP server
        //step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000 to database
        //step 15: user made GET /offers with header "Authorization: Bearer AAAA.BBBB.CCC" and system returned OK(200) with 4 offers with ids: 1000,2000,3000 and 4000


        //step 16: user made POST /offers with header "Authorization: Bearer AAAA.BBBB.CCC" and offer as body and system returned CREATED(201) with saved offer
        //given
        //when
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
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        MvcResult postResult = performPostOffer.andExpect(status().isCreated()).andReturn();
        String json = postResult.getResponse().getContentAsString();
        OfferResponseDto offerResponseDto = objectMapper.readValue(json, OfferResponseDto.class);

        assertAll(
                () -> assertThat(offerResponseDto.companyName()).isEqualTo("JavAPPa")
        );


        //step 17: user made GET /offers with header "Authorization: Bearer AAAA.BBBB.CCC" and system returned OK(200) with 1 offer
        //given
        //when
        ResultActions performGetOffers = mockMvc.perform(get("/offers"));

        //then
        MvcResult mvcGetResult = performGetOffers.andExpect(status().isOk()).andReturn();
        String jsonGetOffers = mvcGetResult.getResponse().getContentAsString();
        List<OfferResponseDto> offersResponseDto = objectMapper.readValue(jsonGetOffers, new TypeReference<List<OfferResponseDto>>() {
        });
        assertThat(offersResponseDto).hasSize(1);
    }
}