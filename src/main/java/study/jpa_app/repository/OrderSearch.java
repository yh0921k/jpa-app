package study.jpa_app.repository;

import lombok.Getter;
import lombok.Setter;
import study.jpa_app.domain.OrderStatus;

@Getter
@Setter
public class OrderSearch {

  private String memberName;
  private OrderStatus orderStatus;
}
