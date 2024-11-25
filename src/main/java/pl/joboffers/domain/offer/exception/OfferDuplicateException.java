package pl.joboffers.domain.offer.exception;

import lombok.Getter;

@Getter
public class OfferDuplicateException extends RuntimeException {

    public OfferDuplicateException(String url) {
        super(String.format("Offer with url %s already exists", url));
    }
}
