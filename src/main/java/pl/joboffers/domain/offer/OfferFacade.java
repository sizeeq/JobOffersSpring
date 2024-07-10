package pl.joboffers.domain.offer;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.offer.dto.OfferRequestDto;
import pl.joboffers.domain.offer.dto.OfferResponseDto;

import java.util.List;

@AllArgsConstructor
public class OfferFacade {

    private final OfferRepository offerRepository;
    private static final OfferMapper offerMapper = new OfferMapper();
    private final OfferService offerService;

    public List<OfferResponseDto> findAllOffers() {
        return offerRepository.findAll()
                .stream()
                .map(offerMapper::mapFromOfferToOfferResponseDto)
                .toList();
    }

    public OfferResponseDto findOfferById(String id) {
        return offerRepository.findById(id)
                .map(offerMapper::mapFromOfferToOfferResponseDto)
                .orElseThrow(() -> new OfferNotFoundException(id));
    }

    public List<OfferResponseDto> fetchAndSaveOffersIfNotExist() {
        return offerService.fetchAndSaveAllOffersIfNotExist()
                .stream()
                .map(offerMapper::mapFromOfferToOfferResponseDto)
                .toList();
    }

    public OfferResponseDto saveOffer(OfferRequestDto offerRequestDto) {
        final Offer offer = offerMapper.mapFromOfferRequestDtoToOffer(offerRequestDto);
        final Offer save = offerRepository.save(offer);
        return offerMapper.mapFromOfferToOfferResponseDto(save);
    }
}
