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
//toOne관계 toMany는 컬렉션 다음 장에서
//오더에서 멤버 오더에서 딜리버리
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    // 또 dto가 필요한건가?

    @GetMapping("/api/v1/simple-orders")
    public List<Order>ordersV1(){

        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        //배열로 하면 안좋음 dto를 쓰자구
        for (Order order : all) {
            order.getMember().getName();// Lazy 강제 초기화
            order.getDelivery().getAddress(); // Lazy 강제 초기화
        }
        return all;
        // 무한루프에 빠짐

        //json ignore -> lazy db에서 안가지고옴
        // null을 둘수는 없어서 프록시멤버 객체 생성 bytebuddy
        // 지연로딩 무시하는 잭슨 설정
    }
    // 쿼리가 너무 많이 나감

    //2번은 엔티티를 dto로 변환
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto>ordersV2(){
        // 리스트가 아니라 result로 감싸기
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
//        스트림으로 result변경
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
            name = order.getMember().getName(); // LAZY 초기화 없으면 디비에서 끌고옴
            // 레이지 초기화 : 영속성 컨텍스트가 이 멤버 아이디를 가지고 영속성 컨텍스트에서 찾고 없으면 DB 쿼리 날림
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); //LAZY 초기화
        }
    }

    // ORDER->SQL 1번 -> 결과 주문수 2개
    // 루프가 2번 돔
    // 첫번째 루프  처음에 오더의 멤버 찾기 멤버 쿼리나감, 딜리버리 쿼리 나감, DTO 생성
    // 두번째 루프  오더의 멤버 찾기 멤버 쿼리나감, 딜리버리 쿼리 나감, DTO 생성
    // 쿼리가 2번나감
    // 1 + 2 + 2
    // N+1 첫번째 쿼리를 날림 2개(N)개가 오고 N번 추가 실행  1+회원+배송 N은 2 ORDER가 2개니까

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
