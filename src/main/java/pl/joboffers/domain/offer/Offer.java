package pl.joboffers.domain.offer;

import lombok.Builder;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Document
public record Offer(
        @Id
        String id,

        @Field("company")
        String companyName,

        @Field("position")
        String position,

        @Field("salary")
        String salary,

        @Indexed(unique = true)
        @Field("url")
        String url) {
}
