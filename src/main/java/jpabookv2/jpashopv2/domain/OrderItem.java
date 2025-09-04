package jpabookv2.jpashopv2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@Getter
@Setter

public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private Item item;

    private Order order;

    private int orderPrice;
    private int count;


}
