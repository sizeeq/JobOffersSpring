package pl.joboffers.domain.offer;

import pl.joboffers.domain.offer.dto.JobOfferResponseDto;

import java.util.List;

public class OfferFacadeTestConfiguration {

    private final InMemoryFetcherTestImpl inMemoryFetcherTest;
    private final InMemoryOfferRepositoryImpl inMemoryOfferRepositoryTest;

    OfferFacadeTestConfiguration() {
        this.inMemoryFetcherTest = new InMemoryFetcherTestImpl(
                List.of(
                        new JobOfferResponseDto("Tester", "xxx", "undisclosed", "URL1"),
                        new JobOfferResponseDto("Tester2", "xxx", "undisclosed", "URL2"),
                        new JobOfferResponseDto("Tester3", "xxx", "undisclosed", "URL3"),
                        new JobOfferResponseDto("Tester4", "xxx", "undisclosed", "URL4"),
                        new JobOfferResponseDto("Tester5", "xxx", "undisclosed", "URL5"),
                        new JobOfferResponseDto("Tester6", "xxx", "undisclosed", "URL6")
                )
        );
        this.inMemoryOfferRepositoryTest = new InMemoryOfferRepositoryImpl();
    }

    OfferFacadeTestConfiguration(List<JobOfferResponseDto> jobOfferResponseDto) {
        this.inMemoryFetcherTest = new InMemoryFetcherTestImpl(jobOfferResponseDto);
        this.inMemoryOfferRepositoryTest = new InMemoryOfferRepositoryImpl();
    }

    OfferFacade offerFacadeForTests() {
        return new OfferFacade(inMemoryOfferRepositoryTest, new OfferService(inMemoryOfferRepositoryTest, inMemoryFetcherTest));
    }
}
