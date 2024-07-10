package pl.joboffers.domain.offer.dto;

import lombok.Builder;

@Builder
public record JobOfferResponseDto(
        String position,
        String companyName,
        String salary,
        String URL) {
}
