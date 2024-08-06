package pl.joboffers.infrastructure.offer.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.joboffers.domain.offer.OfferFacade;
import pl.joboffers.domain.offer.dto.OfferResponseDto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
public class OfferHttpClientScheduler {

    private final OfferFacade offerFacade;
    private static final String STARTED_FETCHING_OFFERS_MESSAGE = "Started fetching offers {}";
    private static final String FINISHED_FETCHING_OFFERS_MESSAGE = "Finished fetching offers {}";
    private static final String ADDED_NEW_OFFERS_MESSAGE = "Added {} new offers";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedDelayString = "${job-offers.scheduler.request.delay}")
    public List<OfferResponseDto> fetchAndSaveOffers() {
        log.info(STARTED_FETCHING_OFFERS_MESSAGE, dateFormat.format(new Date()));
        final List<OfferResponseDto> savedOffers = offerFacade.fetchAndSaveOffersIfNotExist();
        log.info(FINISHED_FETCHING_OFFERS_MESSAGE, savedOffers);
        log.info(ADDED_NEW_OFFERS_MESSAGE, savedOffers.size());
        return savedOffers;
    }
}
