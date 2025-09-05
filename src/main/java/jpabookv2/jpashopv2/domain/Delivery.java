package jpabookv2.jpashopv2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class Delivery {
    @Id@GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // 디폴트 오디너리 숫자로 들어가면 꼬임
    private DeliveryStatus status;
}
