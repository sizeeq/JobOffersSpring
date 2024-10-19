package pl.joboffers.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OfferFacadeConfiguration {

    @Bean
    OfferFacade offerFacade(OfferFetchable offerFetchable, OfferRepository offerRepository) {
        OfferService offerService = new OfferService(offerRepository, offerFetchable);
        return new OfferFacade(offerRepository, offerService);
    }
}
