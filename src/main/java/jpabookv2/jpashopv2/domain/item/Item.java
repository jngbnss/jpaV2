package jpabookv2.jpashopv2.domain.item;

import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter@Setter
public abstract class Item {

    @Id@GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private List<Category> categories = new ArrayList<>();


}
