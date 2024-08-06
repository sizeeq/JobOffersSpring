package pl.joboffers.domain.offer;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.offer.exception.DuplicateOfferException;
import pl.joboffers.domain.offer.exception.OfferSavingException;

import java.util.List;

@AllArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final OfferFetchable offerFetcher;

    List<Offer> fetchAndSaveAllOffersIfNotExist() {
        List<Offer> offers = fetchOffers();
        final List<Offer> filteredOffers = filterNotExistingOffers(offers);
        try {
            return filteredOffers;
//            return offerRepository.saveAll(filteredOffers);
        } catch (DuplicateOfferException duplicateOfferException) {
            throw new OfferSavingException(duplicateOfferException.getMessage(), filteredOffers);
        }
    }

    private List<Offer> filterNotExistingOffers(List<Offer> offers) {
        return offers
                .stream()
                .filter(offer -> !offer.offerUrl().isEmpty())
                .filter(offer -> !offerRepository.existsByUrl(offer.offerUrl()))
                .toList();
    }

    private List<Offer> fetchOffers() {
        return offerFetcher.fetchOffers()
                .stream()
                .map(OfferMapper::mapFromJobOfferResponseDtoToOffer)
                .toList();
    }
}
