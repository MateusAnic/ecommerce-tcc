package com.zprmts.tcc.ecommerce.service;

import com.zprmts.tcc.ecommerce.domain.Order;
import com.zprmts.tcc.ecommerce.dto.OrderItemResponse;
import com.zprmts.tcc.ecommerce.dto.order.OrderResponse;
import com.zprmts.tcc.ecommerce.exception.RegraDeNegocioException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    Order getById(Long orderId) throws RegraDeNegocioException;

    OrderResponse findById(Long orderId) throws RegraDeNegocioException;

    List<OrderItemResponse> getOrderItemsByOrderId(Long id) throws RegraDeNegocioException;

    Page<OrderResponse> meusPedidos(Pageable pageable) throws RegraDeNegocioException;

    Page<OrderResponse> list(Pageable pageable);

    Order save(Order order);

    String delete(Long orderId) throws RegraDeNegocioException;

    OrderResponse finalizarPedido() throws RegraDeNegocioException;

    OrderResponse adicionarPerfume(Long idPerfume) throws RegraDeNegocioException;

    OrderResponse removerPerfume(Long idPerfume) throws RegraDeNegocioException;

    OrderResponse meuCarrinho() throws RegraDeNegocioException;
}
