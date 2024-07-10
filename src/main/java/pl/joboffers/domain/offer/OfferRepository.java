package pl.joboffers.domain.offer;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {

    boolean existsByUrl(String url);

    List<Offer> findAll();

    Optional<Offer> findById(String id);

    Optional<Offer> findByUrl(String url);

    List<Offer> saveAll(List<Offer> offers);

    Offer save(Offer offer);
}
