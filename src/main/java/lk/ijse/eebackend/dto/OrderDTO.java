package lk.ijse.eebackend.dto;

import jakarta.json.bind.annotation.JsonbDateFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor@Data
@ToString
public class OrderDTO {
    private String customerId;
    private double total;
    /*@JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime date;*/
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate date;
    private List<ItemDTO> itemDtoList;

}
