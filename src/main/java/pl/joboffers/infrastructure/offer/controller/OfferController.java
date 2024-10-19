package pl.joboffers.infrastructure.offer.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.joboffers.domain.offer.OfferFacade;
import pl.joboffers.domain.offer.dto.OfferResponseDto;

import java.util.List;

@RestController
@AllArgsConstructor
@Log4j2
public class OfferController {

    private final OfferFacade offerFacade;

    @PostMapping("/token")
    public ResponseEntity<String> sample() {
        return ResponseEntity.ok("Sample token");
    }

    @GetMapping("/offers")
    public ResponseEntity<List<OfferResponseDto>> getOffers() {
        log.info("Fetching all offers");
        List<OfferResponseDto> allOffers = offerFacade.findAllOffers();
        return ResponseEntity.ok(allOffers);
    }
}
