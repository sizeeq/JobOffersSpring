package pl.joboffers.domain.offer;

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
                .filter(offer -> offer.URL().equals(url))
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
                .anyMatch(offer1 -> offer1.URL().equals(offer.URL()))) {
            throw new DuplicateOfferException(offer.URL());
        }
        UUID id = UUID.randomUUID();
        Offer offerToSave = new Offer(
                id.toString(),
                offer.companyName(),
                offer.position(),
                offer.salary(),
                offer.URL()
        );
        database.put(id.toString(), offerToSave);
        return offerToSave;
    }
}
