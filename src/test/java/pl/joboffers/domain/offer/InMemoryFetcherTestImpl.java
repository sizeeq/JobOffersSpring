package pl.joboffers.domain.offer;

import pl.joboffers.domain.offer.dto.JobOfferResponseDto;

import java.util.List;

public class InMemoryFetcherTestImpl implements OfferFetchable {

    List<JobOfferResponseDto> offers;

    public InMemoryFetcherTestImpl(List<JobOfferResponseDto> offers) {
        this.offers = offers;
    }

    @Override
    public List<JobOfferResponseDto> fetchOffers() {
        return offers;
    }
}
