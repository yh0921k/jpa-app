package study.jpa_app.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.jpa_app.domain.Address;
import study.jpa_app.domain.Order;
import study.jpa_app.domain.OrderStatus;
import study.jpa_app.repository.OrderRepository;
import study.jpa_app.repository.OrderSearch;
import study.jpa_app.repository.simplequery.OrderSimpleQueryDto;
import study.jpa_app.repository.simplequery.OrderSimpleQueryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

  private final OrderRepository orderRepository;
  private final OrderSimpleQueryRepository orderSimpleQueryRepository;

  @GetMapping("/api/v1/simple-orders")
  public List<Order> orderV1() {
    List<Order> all = orderRepository.findAll(new OrderSearch());
    for (Order order : all) {
      order.getMember().getName(); // 프록시 객체 강제 초기화
    }
    return all;
  }

  @GetMapping("/api/v2/simple-orders")
  public List<SimpleOrderDto> orderV2() {
    List<Order> orders = orderRepository.findAll(new OrderSearch());
    return orders.stream().map(SimpleOrderDto::new).collect(Collectors.toList());
  }

  @GetMapping("/api/v3/simple-orders")
  public List<SimpleOrderDto> orderV3() {
    List<Order> orders = orderRepository.findAllWithMemberDelivery();
    return orders.stream().map(SimpleOrderDto::new).collect(Collectors.toList());
  }

  @GetMapping("/api/v4/simple-orders")
  public List<OrderSimpleQueryDto> orderV4() {
    return orderSimpleQueryRepository.findOrderDtos();
  }

  @Data
  static class SimpleOrderDto {
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
}
