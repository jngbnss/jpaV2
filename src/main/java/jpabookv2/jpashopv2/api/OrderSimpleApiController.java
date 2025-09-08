package jpabookv2.jpashopv2.api;

import jpabookv2.jpashopv2.domain.Address;
import jpabookv2.jpashopv2.domain.Order;
import jpabookv2.jpashopv2.domain.OrderSearch;
import jpabookv2.jpashopv2.domain.OrderStatus;
import jpabookv2.jpashopv2.repository.OrderRepository;
import jpabookv2.jpashopv2.repository.order.simplequery.OrderSimpleQueryDto;
import jpabookv2.jpashopv2.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order>ordersV1(){
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();// Lazy 강제 초기화
            order.getDelivery().getAddress(); // Lazy 강제 초기화
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto>ordersV2(){
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());


        List<SimpleOrderDto> result = orders.stream().map(o -> new SimpleOrderDto(o)).collect(toList());
        return result;
    }

    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }

    // lazy가 아니라서 N+1 이 되나본데
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto>ordersV3(){
        List<Order>orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream().map(o->new SimpleOrderDto(o)).collect(toList());
        return result;
    }

    private final OrderSimpleQueryRepository orderSimpleQueryRepository;
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto>ordersV4(){
        return orderSimpleQueryRepository.findOrderDtos();
    }

}
