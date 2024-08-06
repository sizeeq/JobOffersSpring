package pl.joboffers.domain.offer;

import pl.joboffers.domain.offer.exception.DuplicateOfferException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOfferRepositoryImpl implements OfferRepository {

    Map<String, Offer> database = new ConcurrentHashMap<>();

    @Override
    public boolean existsByUrl(String url) {
        long count = database.values()
                .stream()
                .filter(offer -> offer.offerUrl().equals(url))
                .count();
        return count == 1;
    }

    @Override
    public List<Offer> findAll() {
        return database.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Offer> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Optional<Offer> findByUrl(String url) {
        return Optional.of(database.get(url));
    }

    @Override
    public List<Offer> saveAll(List<Offer> offers) {
        return offers
                .stream()
                .map(this::save)
                .toList();
    }

    @Override
    public Offer save(Offer offer) {
        if (database.values()
                .stream()
                .anyMatch(offer1 -> offer1.offerUrl().equals(offer.offerUrl()))) {
            throw new DuplicateOfferException(offer.offerUrl());
        }
        UUID id = UUID.randomUUID();
        Offer offerToSave = new Offer(
                id.toString(),
                offer.companyName(),
                offer.position(),
                offer.salary(),
                offer.offerUrl()
        );
        database.put(id.toString(), offerToSave);
        return offerToSave;
    }
}
