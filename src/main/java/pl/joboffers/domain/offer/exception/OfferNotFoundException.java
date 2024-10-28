package pl.joboffers.domain.offer.exception;

import lombok.Getter;

@Getter
public class OfferNotFoundException extends RuntimeException{

    public OfferNotFoundException(String id) {
        super(String.format("Offer with id: %s was not found", id));
    }
}
