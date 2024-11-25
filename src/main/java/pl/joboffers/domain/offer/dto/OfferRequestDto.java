package pl.joboffers.domain.offer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

@Builder
public record OfferRequestDto(
        @NotEmpty(message = "{companyName.not.empty}")
        @NotNull(message = "{companyName.not.null}")
        String companyName,

        @NotEmpty(message = "{position.not.empty}")
        @NotNull(message = "{position.not.null}")
        String position,

        @NotEmpty(message = "{salary.not.empty}")
        @NotNull(message = "{salary.not.null}")
        String salary,

        @NotEmpty(message = "{offerUrl.not.empty}")
        @NotNull(message = "{offerUrl.not.null}")
        String offerUrl
) {
}
