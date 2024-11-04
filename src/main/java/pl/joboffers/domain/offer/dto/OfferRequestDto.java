package pl.joboffers.domain.offer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

@Builder
public record OfferRequestDto(
        @NotBlank(message = "${companyName.not.blank}")
        @NotNull(message = "${companyName.not.null}")
        String companyName,
        @NotBlank(message = "${position.not.blank}")
        @NotNull(message = "${position.not.null}")
        String position,
        @NotEmpty(message = "${salary.not.empty}")
        @NotNull(message = "${salary.not.null}")
        String salary,
        @NotBlank(message = "${offerUrl.not.blank}")
        @NotNull(message = "${offerUrl.not.null}")
        @URL(message = "${offerUrl.proper.url.format}")
        String offerUrl
) {
}
