package pl.joboffers.domain.offer;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.offer.dto.OfferRequestDto;
import pl.joboffers.domain.offer.dto.OfferResponseDto;
import pl.joboffers.domain.offer.exception.OfferNotFoundException;

import java.util.List;

@AllArgsConstructor
public class OfferFacade {

    private final OfferRepository offerRepository;
    private final OfferService offerService;

    public List<OfferResponseDto> findAllOffers() {
        return offerRepository.findAll()
                .stream()
                .map(OfferMapper::mapFromOfferToOfferResponseDto)
                .toList();
    }

    public OfferResponseDto findOfferById(String id) {
        return offerRepository.findById(id)
                .map(OfferMapper::mapFromOfferToOfferResponseDto)
                .orElseThrow(() -> new OfferNotFoundException(id));
    }

    public List<OfferResponseDto> fetchAndSaveOffersIfNotExist() {
        return offerService.fetchAndSaveAllOffersIfNotExist()
                .stream()
                .map(OfferMapper::mapFromOfferToOfferResponseDto)
                .toList();
    }

    public OfferResponseDto saveOffer(OfferRequestDto offerRequestDto) {
        final Offer offer = OfferMapper.mapFromOfferRequestDtoToOffer(offerRequestDto);
        final Offer save = offerRepository.save(offer);
        return OfferMapper.mapFromOfferToOfferResponseDto(save);
    }
}
