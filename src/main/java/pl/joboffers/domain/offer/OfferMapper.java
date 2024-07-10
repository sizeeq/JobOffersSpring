package pl.joboffers.domain.offer;

import pl.joboffers.domain.offer.dto.JobOfferResponseDto;
import pl.joboffers.domain.offer.dto.OfferRequestDto;
import pl.joboffers.domain.offer.dto.OfferResponseDto;

public class OfferMapper {

    public OfferResponseDto mapFromOfferToOfferResponseDto(Offer offer) {
        return OfferResponseDto.builder()
                .id(offer.id())
                .companyName(offer.companyName())
                .URL(offer.URL())
                .position(offer.position())
                .salary(offer.salary())
                .build();
    }

    public Offer mapFromOfferRequestDtoToOffer(OfferRequestDto offerRequestDto) {
        return Offer.builder()
                .companyName(offerRequestDto.companyName())
                .position(offerRequestDto.position())
                .salary(offerRequestDto.salary())
                .URL(offerRequestDto.URL())
                .build();
    }

    public Offer mapFromJobOfferResponseDtoToOffer(JobOfferResponseDto jobOfferResponseDto) {
        return Offer.builder()
                .companyName(jobOfferResponseDto.companyName())
                .URL(jobOfferResponseDto.URL())
                .salary(jobOfferResponseDto.salary())
                .position(jobOfferResponseDto.position())
                .build();
    }
}
