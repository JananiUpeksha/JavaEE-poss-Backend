package lk.ijse.eebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order_itemEntity {
    private String orderId;
    private int itemId;
    private int quantity;
}
