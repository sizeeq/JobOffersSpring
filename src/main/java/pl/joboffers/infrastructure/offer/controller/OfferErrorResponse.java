package pl.joboffers.infrastructure.offer.controller;

import org.springframework.http.HttpStatus;

public record OfferErrorResponse(
        String message,
        HttpStatus status
) {
}
