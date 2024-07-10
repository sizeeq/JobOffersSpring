package pl.joboffers.domain.offer;

import java.util.List;

public class OfferSavingException extends RuntimeException {

    public OfferSavingException(String url) {
        super(String.format("Offer with url %s already exists", url));
    }

    public OfferSavingException(String message, List<Offer> filteredOffers) {
        super(String.format(message + filteredOffers.toString() + " ERROR!"));
    }
}
