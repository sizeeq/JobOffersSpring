package pl.joboffers.domain.offer;

public class OfferNotFoundException extends RuntimeException{

    public OfferNotFoundException(String id) {
        super(String.format("Offer with id = %s was not found", id));
    }
}
