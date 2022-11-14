package study.jpa_app.repository.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

  private final EntityManager em;

  // new 연산에서 엔티티를 넘기면 식별자로 넘어가기 때문에 필드를 지정해야함
  // 단 예외적으로 Address는 값 타입이므로 가능
  public List<OrderSimpleQueryDto> findOrderDtos() {
    return em.createQuery(
            "select new study.jpa_app.repository.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address) "
                + "from Order o "
                + "join o.member m "
                + "join o.delivery d",
            OrderSimpleQueryDto.class)
        .getResultList();
  }
}
