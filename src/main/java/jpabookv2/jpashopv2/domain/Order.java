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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") //맵핑을 뭐로 할거냐 fk이름
    private Member member;
    // 값은 여기서 바꿔야하지
    //연관관계의 주인

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL) // persist를 전파해줌
    private List<OrderItem> orderItems = new ArrayList<>();
    // 라이프 사이클이 부모에게 종속적이면

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    // 오더 테이블에 딜리버리를 fk로 가지고 있으니까 연관관계의 주인으로

    private LocalDateTime orderDate;
    // 어노테이션 없이 hibernate가 지원해줌

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // 연관관계 메서드 왜 해야하는지 작성해봐
    //양방향 연관관계를 “일관성 있게” 맞춰주기 위해서예요.
    //== 연관관계 편의 메서드==//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
