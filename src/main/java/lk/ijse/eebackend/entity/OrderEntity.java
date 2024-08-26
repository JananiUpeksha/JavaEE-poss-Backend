package lk.ijse.eebackend.entity;

import jakarta.json.bind.annotation.JsonbDateFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderEntity {


    private String orderId; // Optional, can be null when creating a new order
    private String customerId;
    private double total;
    /*@JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime date;*/
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate date;


}
