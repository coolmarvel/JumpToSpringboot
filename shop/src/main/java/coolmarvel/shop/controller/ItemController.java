package coolmarvel.shop.controller;

import coolmarvel.shop.domain.item.Book;
import coolmarvel.shop.domain.item.Item;
import coolmarvel.shop.service.ItemService;
import java.util.List;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Data
public class ItemController {
  private final ItemService itemService;

  /**
   * 상품 등록 폼 (GET)
   *
   * @param model
   * @return
   */
  @GetMapping("/items/new")
  public String createForm(Model model) {
    model.addAttribute("form", new BookForm()); // BookForm 클래스를 새로 생성하여 View에 넘겨준다.
    return "items/createItemForm";
  }

  /**
   * 상품 등록 처리 (POST)
   *
   * @param form
   * @return
   */
  @PostMapping("items/new")
  public String create(@ModelAttribute("form") BookForm form) {
    Book book = new Book();
    book.setName(form.getName());
    book.setPrice(form.getPrice());
    book.setStockQuantity(form.getStockQuantity());
    book.setAuthor(form.getAuthor());
    book.setIsbn(form.getIsbn());

    itemService.saveItem(book);

    return "redirect:/items";
  }

  /**
   * 상품 목록 조회
   *
   * @param model
   * @return
   */
  @GetMapping("/items")
  public String list(Model model) {
    List<Item> items = itemService.findItems();
    model.addAttribute("items", items);
    return "items/itemList";
  }

  /**
   * 상품 상세보기
   *
   * @param itemId
   * @param model
   * @return
   */
  @GetMapping("/items/{itemId}")
  public String detail(@PathVariable("itemId") Long itemId, Model model) {
    Item findItem = itemService.findOne(itemId);
    model.addAttribute("item", findItem);
    return "items/detail";
  }

  /**
   * 상품 수정 폼 (GET)
   *
   * @param itemId
   * @param model
   * @return
   */
  @GetMapping("/items/{itemId}/edit")
  public String updateItemForm(
    @PathVariable("itemId") Long itemId,
    Model model
  ) {
    Book item = (Book) itemService.findOne(itemId);

    BookForm form = new BookForm();
    form.setId(item.getId());
    form.setName(item.getName());
    form.setPrice(item.getPrice());
    form.setStockQuantity(item.getStockQuantity());
    form.setAuthor(item.getAuthor());
    form.setIsbn(item.getIsbn());

    model.addAttribute("form", form);
    return "items/updateItemForm";
  }

  /**
   * 상품 수정 처리 (POST)
   *
   * @param itemId
   * @param form
   * @return
   */
  @PostMapping("items/{itemId}/edit")
  public String updateItem(
    @PathVariable("itemId") Long itemId,
    @ModelAttribute("form") BookForm form
  ) {
    itemService.updateItem(
      itemId,
      form.getName(),
      form.getPrice(),
      form.getStockQuantity()
    );
    return "redirect:/items";
  }

  /**
   * 상품 삭제
   *
   * @param itemId
   * @return
   */
  @PostMapping("/items/{itemId}/delete")
  public String deleteItem(@PathVariable("itemId") Long itemId) {
    itemService.deleteItem(itemId);
    return "redirect:/items";
  }
}
