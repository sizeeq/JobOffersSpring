package pl.joboffers.domain.offer.exception;

import lombok.Getter;

@Getter
public class DuplicateOfferException extends RuntimeException {

    public DuplicateOfferException(String url) {
        super(String.format("Offer with url %s already exists", url));
    }


}
