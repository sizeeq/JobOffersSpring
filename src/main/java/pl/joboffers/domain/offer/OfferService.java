package pl.joboffers.domain.offer;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final OfferFetchable offerFetcher;

    List<Offer> fetchAndSaveAllOffersIfNotExist() {
        List<Offer> offers = fetchOffers();
        final List<Offer> filteredOffers = filterNotExistingOffers(offers);
        return offerRepository.saveAll(filteredOffers);
    }

    private List<Offer> fetchOffers() {
        return offerFetcher.fetchOffers()
                .stream()
                .map(OfferMapper::mapFromJobOfferResponseDtoToOffer)
                .toList();
    }

    private List<Offer> filterNotExistingOffers(List<Offer> offers) {
        return offers
                .stream()
                .filter(offer -> !offer.url().isEmpty())
                .filter(offer -> !offerRepository.existsByUrl(offer.url()))
                .toList();
    }
}
