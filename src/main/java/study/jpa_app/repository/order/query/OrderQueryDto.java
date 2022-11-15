package study.jpa_app.repository.order.query;

import lombok.Data;
import study.jpa_app.domain.Address;
import study.jpa_app.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderQueryDto {

  private Long orderId;
  private String name;
  private LocalDateTime orderDate;
  private OrderStatus orderStatus;
  private Address address;
  private List<OrderItemQueryDto> orderItems;

  public OrderQueryDto(
      Long orderId,
      String name,
      LocalDateTime orderDate,
      OrderStatus orderStatus,
      Address address) {
    this.orderId = orderId;
    this.name = name;
    this.orderDate = orderDate;
    this.orderStatus = orderStatus;
    this.address = address;
  }
}
