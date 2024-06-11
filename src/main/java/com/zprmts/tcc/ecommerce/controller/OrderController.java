package com.zprmts.tcc.ecommerce.controller;

import com.zprmts.tcc.ecommerce.controller.interfaces.OrderControllerInterface;
import com.zprmts.tcc.ecommerce.dto.OrderItemResponse;
import com.zprmts.tcc.ecommerce.dto.order.OrderResponse;
import com.zprmts.tcc.ecommerce.exception.RegraDeNegocioException;
import com.zprmts.tcc.ecommerce.service.Impl.OrderServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@Tag(name = "Order")
public class OrderController implements OrderControllerInterface {
    
    private final OrderServiceImpl orderService;


    @Override
    @PutMapping("/add-perfume/{idPerfume}")
    public ResponseEntity<OrderResponse> adicionarPerfume(@Valid @PathVariable Long idPerfume) throws RegraDeNegocioException {
        return new ResponseEntity<>(orderService.adicionarPerfume(idPerfume), HttpStatus.OK);
    }

    @Override
    @PutMapping("/remove-perfume/{idPerfume}")
    public ResponseEntity<OrderResponse> removerPerfume(@Valid @PathVariable Long idPerfume) throws RegraDeNegocioException {
        return new ResponseEntity<>(orderService.removerPerfume(idPerfume), HttpStatus.OK);
    }

    @Override
    @PutMapping("/finalizar-pedido")
    public ResponseEntity<OrderResponse> finalizarPedido() throws RegraDeNegocioException {
        return new ResponseEntity<>(orderService.finalizarPedido(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/{idOrder}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long idOrder) throws RegraDeNegocioException {
        return new ResponseEntity<>(orderService.findById(idOrder), HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}/itens")
    public ResponseEntity<List<OrderItemResponse>> getOrderItemsByOrderId(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(orderService.getOrderItemsByOrderId(id), HttpStatus.OK);
    }

    @Override
    @GetMapping("/meus-pedidos")
    public ResponseEntity<Page<OrderResponse>> meusPedidos(@PageableDefault(size = 10) Pageable pageable) throws RegraDeNegocioException {
        return new ResponseEntity<>(orderService.meusPedidos(pageable), HttpStatus.OK);
    }
    
    @Override
    @GetMapping
    public ResponseEntity<Page<OrderResponse>> list(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) throws RegraDeNegocioException {
        return new ResponseEntity<>(orderService.list(pageable), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{idOrder}")
    public ResponseEntity<Void> delete(@PathVariable Long idOrder) throws RegraDeNegocioException {
        orderService.delete(idOrder);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/meu-carrinho")
    public ResponseEntity<OrderResponse> meuCarrinho() throws RegraDeNegocioException {
        return new ResponseEntity<>(orderService.meuCarrinho(), HttpStatus.OK);
    }
}
