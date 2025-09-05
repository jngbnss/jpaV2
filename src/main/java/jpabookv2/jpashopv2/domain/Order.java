package jpabookv2.jpashopv2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter@Setter
public class Order {

    @Id@GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id") //맵핑을 뭐로 할거냐 fk이름
    private Member member;
    // 값은 여기서 바꿔야하지
    //연관관계의 주인

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    // 오더 테이블에 딜리버리를 fk로 가지고 있으니까 연관관계의 주인으로

    private LocalDateTime orderDate;
    // 어노테이션 없이 hibernate가 지원해줌

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // 연관관계 메서드 왜 해야하는지 작성해봐
}
