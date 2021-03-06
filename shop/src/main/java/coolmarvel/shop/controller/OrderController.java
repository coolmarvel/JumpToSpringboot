package coolmarvel.shop.controller;

import coolmarvel.shop.domain.Member;
import coolmarvel.shop.domain.Order;
import coolmarvel.shop.domain.item.Item;
import coolmarvel.shop.repository.OrderSearch;
import coolmarvel.shop.service.ItemService;
import coolmarvel.shop.service.MemberService;
import coolmarvel.shop.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;
  private final MemberService memberService;
  private final ItemService itemService;

  /**
   * 주문 생성 폼 (GET)
   * @param model
   * @return
   */
  @GetMapping("/order")
  public String createForm(Model model) {
    List<Member> members = memberService.findMembers();
    List<Item> items = itemService.findItems();

    model.addAttribute("members", members);
    model.addAttribute("items", items);

    return "order/orderForm";
  }

  /**
   * 주문 생성 처리 (POST)
   * @param memberId
   * @param itemId
   * @param count
   * @return
   */
  @PostMapping("/order")
  public String order(
    @RequestParam("memberId") Long memberId,
    @RequestParam("itemId") Long itemId,
    @RequestParam("count") int count
  ) {
    orderService.order(memberId, itemId, count);
    return "redirect:/orders";
  }

  /**
   * 주문 목록 검색 및 취소 (GET)
   * @param orderSearch
   * @param model
   * @return
   */
  @GetMapping("/orders")
  public String orderList(
    @ModelAttribute("orderSearch") OrderSearch orderSearch,
    Model model
  ) {
    List<Order> orders = orderService.findOrders(orderSearch);
    model.addAttribute("orders", orders);
    return "order/orderList";
  }

  /**
   * 주문 취소 (POST)
   * @param orderId
   * @return
   */
  @PostMapping("/orders/{orderId}/cancel")
  public String cancelOrder(@PathVariable("orderId") Long orderId) {
    orderService.cancelOrder(orderId);
    return "redirect:/orders";
  }
}
