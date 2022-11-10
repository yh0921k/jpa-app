package study.jpa_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import study.jpa_app.domain.item.Book;
import study.jpa_app.domain.item.Item;
import study.jpa_app.service.ItemService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  @GetMapping("/items/new")
  public String createForm(Model model) {
    model.addAttribute("form", new BookForm());
    return "items/createItemForm";
  }

  @PostMapping("/items/new")
  public String create(BookForm bookForm) {

    // Setter를 제거하고 create 등의 메서드를 만드는 것이 더 좋은 설계
    Book book = new Book();
    book.setName(bookForm.getName());
    book.setPrice(bookForm.getPrice());
    book.setStockQuantity(bookForm.getStockQuantity());
    book.setAuthor(bookForm.getAuthor());
    book.setIsbn(bookForm.getIsbn());

    itemService.saveItem(book);
    return "redirect:/";
  }

  @GetMapping("/items")
  public String list(Model model) {
    List<Item> items = itemService.findItems();
    model.addAttribute("items", items);

    return "items/itemList";
  }
}
