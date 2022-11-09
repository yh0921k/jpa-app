package study.jpa_app.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.jpa_app.domain.Address;
import study.jpa_app.domain.Member;
import study.jpa_app.domain.Order;
import study.jpa_app.domain.OrderStatus;
import study.jpa_app.domain.item.Book;
import study.jpa_app.domain.item.Item;
import study.jpa_app.exception.NotEnoughStockException;
import study.jpa_app.repository.OrderRepository;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

  @Autowired EntityManager em;

  @Autowired OrderService orderService;

  @Autowired OrderRepository orderRepository;

  @Test
  public void order() {
    // given
    Member member = createMember();

    Item book = createBook("BookA", 10000, 10);

    int orderCount = 2;

    // when
    Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

    // then
    Order findOrder = orderRepository.findOne(orderId);

    assertEquals(OrderStatus.ORDER, findOrder.getStatus(), "상품 주문시 상태는 ORDER");
    assertEquals(1, findOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
    assertEquals(10000 * orderCount, findOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
    assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
  }

  @Test
  public void stockOver() {
    // given
    Member member = createMember();
    Item book = createBook("BookA", 10000, 10);

    int orderCount = 11;

    // then
    assertThrows(
        NotEnoughStockException.class,
        () -> orderService.order(member.getId(), book.getId(), orderCount));
  }

  @Test
  public void cancel() {
    // given
    Member member = createMember();
    Item book = createBook("BookA", 10000, 10);

    int orderCount = 2;
    Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

    // when
    orderService.cancelOrder(orderId);

    // then
    Order findOrder = orderRepository.findOne(orderId);

    assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
    assertThat(book.getStockQuantity()).isEqualTo(10);
  }

  private Item createBook(String name, int price, int stockQuantity) {
    Item book = new Book();
    book.setName(name);
    book.setPrice(price);
    book.setStockQuantity(stockQuantity);
    em.persist(book);
    return book;
  }

  private Member createMember() {
    Member member = new Member();
    member.setName("MemberA");
    member.setAddress(new Address("Seoul", "Street", "123-123"));
    em.persist(member);
    return member;
  }
}
