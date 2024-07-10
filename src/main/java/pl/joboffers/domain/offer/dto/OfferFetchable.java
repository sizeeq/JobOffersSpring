package pl.joboffers.domain.offer.dto;

import java.util.List;

public interface OfferFetchable {

    List<JobOfferResponseDto> fetchOffers();
}
