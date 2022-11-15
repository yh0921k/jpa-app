package study.jpa_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.jpa_app.domain.Delivery;
import study.jpa_app.domain.Member;
import study.jpa_app.domain.Order;
import study.jpa_app.domain.OrderItem;
import study.jpa_app.domain.item.Item;
import study.jpa_app.repository.ItemRepository;
import study.jpa_app.repository.MemberRepository;
import study.jpa_app.repository.OrderRepository;
import study.jpa_app.repository.OrderSearch;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final MemberRepository memberRepository;
  private final ItemRepository itemRepository;

  @Transactional
  public Long order(Long memberId, Long itemId, int count) {
    Member member = memberRepository.findById(memberId).get();
    Item item = itemRepository.findOne(itemId);

    Delivery delivery = new Delivery();
    delivery.setAddress(member.getAddress());

    OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

    Order order = Order.createOrder(member, delivery, orderItem);

    orderRepository.save(order);
    return order.getId();
  }

  @Transactional
  public void cancelOrder(Long orderId) {
    Order order = orderRepository.findOne(orderId);
    order.cancel();
  }

  public List<Order> findOrders(OrderSearch orderSearch) {
    return orderRepository.findAll(orderSearch);
  }
}
