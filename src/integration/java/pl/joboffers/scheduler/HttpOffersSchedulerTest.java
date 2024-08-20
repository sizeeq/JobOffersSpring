package pl.joboffers.scheduler;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;
import pl.joboffers.BaseIntegrationTest;
import pl.joboffers.JobOffersApplication;
import pl.joboffers.domain.offer.OfferFetchable;
import pl.joboffers.infrastructure.offer.http.OfferHttpClientConfig;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {JobOffersApplication.class, OfferHttpClientConfig.class}, properties = "scheduling.enabled=true")
public class HttpOffersSchedulerTest extends BaseIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(HttpOffersSchedulerTest.class);
    @SpyBean
    OfferFetchable offerFetchable;

    @Autowired
    ApplicationContext context;

    @BeforeEach
    public void setup() {
        OfferFetchable offerFetchable = context.getBean(OfferFetchable.class);
        Mockito.spy(offerFetchable);
    }

    @Test
    public void should_run_http_client_offers_fetching_exactly_given_times() {
        log.info("offerFetchable is: " + offerFetchable);
        await()
                .atMost(Duration.ofSeconds(2))
                .untilAsserted(() -> verify(offerFetchable, times(2)).fetchOffers());
    }
}
