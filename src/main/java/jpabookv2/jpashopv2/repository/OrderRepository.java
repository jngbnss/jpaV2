package jpabookv2.jpashopv2.repository;

import jakarta.persistence.EntityManager;
import jpabookv2.jpashopv2.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class,id);
    }

    //주문 검색 기능
    //public List<order> findAll(OrderSearch orderSearch){}

}
