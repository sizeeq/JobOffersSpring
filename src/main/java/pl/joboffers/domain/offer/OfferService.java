package pl.joboffers.domain.offer;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.offer.dto.OfferFetchable;

import java.util.List;

@AllArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final OfferFetchable offerFetcher;
    private static final OfferMapper offerMapper = new OfferMapper();

    List<Offer> fetchAndSaveAllOffersIfNotExist() {
        List<Offer> offers = fetchOffers();
        final List<Offer> filteredOffers = filterNotExistingOffers(offers);
        try {
            return offerRepository.saveAll(filteredOffers);
        } catch (DuplicateOfferException duplicateOfferException) {
            throw new OfferSavingException(duplicateOfferException.getMessage(), filteredOffers);
        }
    }

    private List<Offer> filterNotExistingOffers(List<Offer> offers) {
        return offers
                .stream()
                .filter(offer -> !offer.URL().isEmpty())
                .filter(offer -> !offerRepository.existsByUrl(offer.URL()))
                .toList();
    }

    private List<Offer> fetchOffers() {
        return offerFetcher.fetchOffers()
                .stream()
                .map(offerMapper::mapFromJobOfferResponseDtoToOffer)
                .toList();
    }
}
