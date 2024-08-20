package pl.joboffers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.joboffers.infrastructure.offer.http.OfferHttpClientConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties(OfferHttpClientConfigProperties.class)
public class JobOffersApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobOffersApplication.class, args);
    }
}
