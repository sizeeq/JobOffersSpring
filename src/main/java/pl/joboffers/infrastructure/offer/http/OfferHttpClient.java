package pl.joboffers.infrastructure.offer.http;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.joboffers.domain.offer.OfferFetchable;
import pl.joboffers.domain.offer.dto.JobOfferResponseDto;

import java.util.Collections;
import java.util.List;

@Log4j2
@AllArgsConstructor
public class OfferHttpClient implements OfferFetchable {

    public final RestTemplate restTemplate;
    public String uri;
    public int port;

    @Override
    public List<JobOfferResponseDto> fetchOffers() {
        log.info("Fetching offers from external server...");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(httpHeaders);
        try {
            String urlForService = getUrlForService("/offers");
            final String url = UriComponentsBuilder.fromHttpUrl(urlForService).toUriString();
            ResponseEntity<List<JobOfferResponseDto>> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {
            });
            final List<JobOfferResponseDto> body = response.getBody();
            if (body == null || body.isEmpty()) {
                log.info("Response body is null or empty - returning empty list");
                return Collections.emptyList();
            }
            log.info("Response body: {}", body);
            return body;
        } catch (ResourceAccessException e) {
            log.error("Error while fetching offers using http client {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }
}
