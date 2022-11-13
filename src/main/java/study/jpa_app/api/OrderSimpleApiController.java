package study.jpa_app.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.jpa_app.domain.Order;
import study.jpa_app.repository.OrderRepository;
import study.jpa_app.repository.OrderSearch;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

  private final OrderRepository orderRepository;

  @GetMapping("/api/v1/simple-orders")
  public List<Order> orderV1() {
    List<Order> all = orderRepository.findAll(new OrderSearch());
    for (Order order : all) {
      order.getMember().getName(); // 프록시 객체 강제 초기화
    }
    return all;
  }
}
