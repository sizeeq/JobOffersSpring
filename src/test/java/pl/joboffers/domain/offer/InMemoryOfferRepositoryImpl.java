package pl.joboffers.domain.offer;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.StreamSupport;

public class InMemoryOfferRepositoryImpl implements OfferRepository {

    private final Map<String, Offer> database = new ConcurrentHashMap<>();

    @Override
    public boolean existsByUrl(String url) {
        return database.values()
                .stream()
                .anyMatch(offer -> offer.url().equals(url));
    }

    @Override
    public List<Offer> findAll() {
        return database.values()
                .stream()
                .toList();
    }

    @Override
    public List<Offer> findAllById(Iterable<String> strings) {
        return List.of(); // Implement if necessary
    }

    @Override
    public long count() {
        return database.size();
    }

    @Override
    public void deleteById(String id) {
        database.remove(id);
    }

    @Override
    public void delete(Offer entity) {
        database.remove(entity.id());
    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {
        strings.forEach(this::deleteById);
    }

    @Override
    public void deleteAll(Iterable<? extends Offer> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        database.clear();
    }

    @Override
    public Optional<Offer> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return database.containsKey(id);
    }

    @Override
    public Optional<Offer> findByUrl(String url) {
        return database.values()
                .stream()
                .filter(offer -> offer.url().equals(url))
                .findFirst();
    }

    @Override
    public <S extends Offer> List<S> saveAll(Iterable<S> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::save)
                .toList();
    }

    @Override
    public <S extends Offer> S save(@NotNull S entity) {
        if (existsByUrl(entity.url())) {
            throw new DuplicateKeyException(String.format("Offer with offerUrl [%s] already exists", entity.url()));
        }
        String id = UUID.randomUUID().toString();
        Offer offer = new Offer(
                id,
                entity.companyName(),
                entity.position(),
                entity.salary(),
                entity.url()
        );
        database.put(id, offer);
        return (S) offer;
    }

    @Override
    public <S extends Offer> S insert(S entity) {
        // Implement if necessary
        return null;
    }

    @Override
    public <S extends Offer> List<S> insert(Iterable<S> entities) {
        // Implement if necessary
        return List.of();
    }

    @Override
    public <S extends Offer> Optional<S> findOne(Example<S> example) {
        // Implement if necessary
        return Optional.empty();
    }

    @Override
    public <S extends Offer> List<S> findAll(Example<S> example) {
        // Implement if necessary
        return List.of();
    }

    @Override
    public <S extends Offer> List<S> findAll(Example<S> example, Sort sort) {
        // Implement if necessary
        return List.of();
    }

    @Override
    public <S extends Offer> Page<S> findAll(Example<S> example, Pageable pageable) {
        // Implement if necessary
        return null;
    }

    @Override
    public <S extends Offer> long count(Example<S> example) {
        // Implement if necessary
        return 0;
    }

    @Override
    public <S extends Offer> boolean exists(Example<S> example) {
        // Implement if necessary
        return false;
    }

    @Override
    public <S extends Offer, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        // Implement if necessary
        return null;
    }

    @Override
    public List<Offer> findAll(Sort sort) {
        // Implement if necessary
        return List.of();
    }

    @Override
    public Page<Offer> findAll(Pageable pageable) {
        // Implement if necessary
        return null;
    }
}
