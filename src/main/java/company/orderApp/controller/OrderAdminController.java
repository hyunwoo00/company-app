package company.orderApp.controller;


import company.orderApp.controller.response.OrderAdminDto;
import company.orderApp.controller.response.ResultResponse;
import company.orderApp.domain.order.Order;
import company.orderApp.repository.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/admin/orders")
public class OrderAdminController {


    private final OrderRepository orderRepository;

    @GetMapping("")
    public ResultResponse orders(@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime date,
                                 @RequestParam(value = "offset", defaultValue = "0") int offset,
                                 @RequestParam(value = "limit", defaultValue = "20") int limit) {
        List<Order> orders = orderRepository.findAllByDate(date, offset, limit);

        List<OrderAdminDto> result = orders.stream().map(OrderAdminDto::new).toList();

        return new ResultResponse(result.size(), result);
    }


}
