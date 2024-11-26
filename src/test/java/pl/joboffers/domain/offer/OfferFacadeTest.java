package pl.joboffers.domain.offer;

import org.junit.Test;
import org.springframework.dao.DuplicateKeyException;
import pl.joboffers.domain.offer.dto.JobOfferResponseDto;
import pl.joboffers.domain.offer.dto.OfferRequestDto;
import pl.joboffers.domain.offer.dto.OfferResponseDto;
import pl.joboffers.domain.offer.exception.OfferNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


public class OfferFacadeTest {

    @Test
    public void shouldFetchOffersFromRemoteServerAndSaveThemAllIfRepositoryIsEmpty() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();

        //when
        List<OfferResponseDto> offerResponseDtos = offerFacade.fetchAndSaveOffersIfNotExist();

        //then
        assertThat(offerResponseDtos).hasSize(6);
    }

    @Test
    public void shouldSaveOnly2OffersWhenThereIsAlready4AddedInRepositoryWithMatchingUrls() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration(
                List.of(
                        new JobOfferResponseDto("Tester", "xxx", "undisclosed", "URL1"),
                        new JobOfferResponseDto("Tester2", "xxx", "undisclosed", "URL2"),
                        new JobOfferResponseDto("Tester3", "xxx", "undisclosed", "URL3"),
                        new JobOfferResponseDto("Tester4", "xxx", "undisclosed", "URL4"),
                        new JobOfferResponseDto("Tester5", "xxx", "undisclosed", "https://google.com/1"),
                        new JobOfferResponseDto("Tester6", "xxx", "undisclosed", "https://google.com/2")
                )
        ).offerFacadeForTests();
        offerFacade.saveOffer(new OfferRequestDto("xxx", "Tester", "undisclosed", "URL1"));
        offerFacade.saveOffer(new OfferRequestDto("xxx", "Tester2", "undisclosed", "URL2"));
        offerFacade.saveOffer(new OfferRequestDto("xxx", "Tester3", "undisclosed", "URL3"));
        offerFacade.saveOffer(new OfferRequestDto("xxx", "Tester4", "undisclosed", "URL4"));
        assertThat(offerFacade.findAllOffers()).hasSize(4);

        //when
        List<OfferResponseDto> response = offerFacade.fetchAndSaveOffersIfNotExist();

        //then
        assertThat(response).hasSize(2); // Only two new offers should be saved
        assertThat(List.of(
                        response.get(0).offerUrl(),
                        response.get(1).offerUrl()
                )
        ).containsExactlyInAnyOrder("https://google.com/1", "https://google.com/2");
    }


    @Test
    public void shouldSave4OffersWhenThereIsNoOffersInDatabase() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();

        //when
        offerFacade.saveOffer(new OfferRequestDto("xxx", "Tester", "undisclosed", "URL1"));
        offerFacade.saveOffer(new OfferRequestDto("xxx", "Tester2", "undisclosed", "URL2"));
        offerFacade.saveOffer(new OfferRequestDto("xxx", "Tester3", "undisclosed", "URL3"));
        offerFacade.saveOffer(new OfferRequestDto("xxx", "Tester4", "undisclosed", "URL4"));

        //then
        assertThat(offerFacade.findAllOffers()).hasSize(4);
    }

    @Test
    public void shouldFindSavedOfferById() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();
        OfferResponseDto offerResponseDto = offerFacade.saveOffer(new OfferRequestDto("cn", "p", "undisclosed", "url"));

        //when
        OfferResponseDto offerById = offerFacade.findOfferById(offerResponseDto.id());

        //then
        assertThat(offerById).isEqualTo(OfferResponseDto.builder()
                .id(offerResponseDto.id())
                .salary(offerResponseDto.salary())
                .position(offerResponseDto.position())
                .companyName(offerResponseDto.companyName())
                .offerUrl(offerResponseDto.offerUrl())
                .build()
        );
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenOfferDoesNotExist() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();
        assertThat(offerFacade.findAllOffers()).isEmpty();

        //when
        Throwable throwable = catchThrowable(() -> offerFacade.findOfferById("10"));

        //then
        assertThat(throwable)
                .isInstanceOf(OfferNotFoundException.class)
                .hasMessage("Offer with id = 10 was not found");
    }

    @Test
    public void shouldThrowDuplicateExceptionWhenOfferAlreadyExists() {
        //given
        OfferFacade offerFacade = new OfferFacadeTestConfiguration().offerFacadeForTests();
        OfferResponseDto offerResponseDto = offerFacade.saveOffer(new OfferRequestDto("cn", "p", "undisclosed", "url.url"));

        //when
        Throwable throwable = catchThrowable(() -> offerFacade.saveOffer(new OfferRequestDto("cn2", "p2", "undisclosed", "url.url")));

        //then
        assertThat(throwable)
                .isInstanceOf(DuplicateKeyException.class)
                .hasMessage("Offer with url url.url already exists");
    }
}