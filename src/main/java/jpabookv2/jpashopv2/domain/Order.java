package jpabookv2.jpashopv2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter@Setter
public class Order {

    @Id@GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private Member member;

    private List<OrederItem>orderItems = new ArryaList<>();

    private Delivery deliver;

    private LocalDateTime orederDate;

    private OrderStatus status;

    // 연관관계 메서드 왜 해야하는지 작성해봐
}
