package jpabookv2.jpashopv2.web;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class BookForm {
    //상품 등록 폼
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;
}
