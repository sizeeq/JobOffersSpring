package pl.joboffers.infrastructure.offer.http;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "job-offers.http.client.config")
@Builder
public record OfferHttpClientConfigProperties(long connectionTimeout,
                                              long readTimeout,
                                              String uri,
                                              int port
                                                           ) {
}
