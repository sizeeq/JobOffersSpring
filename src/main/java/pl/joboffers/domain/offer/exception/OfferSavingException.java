package pl.joboffers.domain.offer.exception;

import lombok.Getter;
import pl.joboffers.domain.offer.Offer;

import java.util.List;

@Getter
public class OfferSavingException extends RuntimeException {

    public OfferSavingException(String url) {
        super(String.format("Offer with url %s already exists", url));
    }

    public OfferSavingException(String message, List<Offer> filteredOffers) {
        super(String.format(message + filteredOffers.toString() + " ERROR!"));
    }
}
