package jpabookv2.jpashopv2.service;

import jpabookv2.jpashopv2.domain.*;
import jpabookv2.jpashopv2.domain.item.Item;
import jpabookv2.jpashopv2.repository.ItemRepository;
import jpabookv2.jpashopv2.repository.MemberRepository;
import jpabookv2.jpashopv2.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private  final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    /**주문 검색*/

    public List<Order> findOrders(OrderSearch orderSearch){
    return orderRepository.findAllByCriteria(orderSearch);
    }

}
