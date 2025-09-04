package jpabookv2.jpashopv2.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Entity
@Getter@Setter
public class Member {
    @Id@GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private Address address;

    private List<Order>orders = new ArrayList<>();
}
