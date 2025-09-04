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
public class Category {
    @Id@GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    private List<Item>items = new ArrayList<>();

    private Category parent;
    private  List<Category>child = new ArrayList<>();

    //연관관계 메서드
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }
}
