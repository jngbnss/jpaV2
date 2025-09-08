package jpabookv2.jpashopv2.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
public class Member {
    @Id@GeneratedValue
    @Column(name = "member_id") //pk
    private Long id;

    @NotEmpty
    private String name;

    @Embedded // 내장타입을 포함했다
    private Address address;

    @OneToMany(mappedBy = "member") // 오더 테이블에 있는 필드명 멤버
    private List<Order> orders = new ArrayList<>();
    // 거울 읽기 전용
}
