package pl.joboffers.domain.offer;

import pl.joboffers.domain.offer.dto.JobOfferResponseDto;
import pl.joboffers.domain.offer.dto.OfferRequestDto;
import pl.joboffers.domain.offer.dto.OfferResponseDto;

public class OfferMapper {

    public static OfferResponseDto mapFromOfferToOfferResponseDto(Offer offer) {
        return OfferResponseDto.builder()
                .id(offer.id())
                .companyName(offer.companyName())
                .offerUrl(offer.url())
                .position(offer.position())
                .salary(offer.salary())
                .build();
    }

    public static Offer mapFromOfferRequestDtoToOffer(OfferRequestDto offerRequestDto) {
        return Offer.builder()
                .companyName(offerRequestDto.companyName())
                .position(offerRequestDto.position())
                .salary(offerRequestDto.salary())
                .url(offerRequestDto.offerUrl())
                .build();
    }

    public static Offer mapFromJobOfferResponseDtoToOffer(JobOfferResponseDto jobOfferResponseDto) {
        return Offer.builder()
                .companyName(jobOfferResponseDto.company())
                .url(jobOfferResponseDto.offerUrl())
                .salary(jobOfferResponseDto.salary())
                .position(jobOfferResponseDto.title())
                .build();
    }
}
