package com.zprmts.tcc.ecommerce.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zprmts.tcc.ecommerce.domain.Order;
import com.zprmts.tcc.ecommerce.domain.OrderItem;
import com.zprmts.tcc.ecommerce.domain.Perfume;
import com.zprmts.tcc.ecommerce.domain.User;
import com.zprmts.tcc.ecommerce.dto.OrderItemResponse;
import com.zprmts.tcc.ecommerce.dto.order.OrderResponse;
import com.zprmts.tcc.ecommerce.enums.StatusOrderEnum;
import com.zprmts.tcc.ecommerce.exception.RegraDeNegocioException;
import com.zprmts.tcc.ecommerce.repository.OrderItemRepository;
import com.zprmts.tcc.ecommerce.repository.OrderRepository;
import com.zprmts.tcc.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.zprmts.tcc.ecommerce.constants.ErrorMessage.ORDER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ObjectMapper objectMapper;
    private final UserServiceImpl usuarioService;
    private final PerfumeServiceImpl perfumeService;


    @Override
    public OrderResponse adicionarPerfume(Long idPerfume) throws RegraDeNegocioException {
        User user =  usuarioService.getUserLogado();
        Order order = orderRepository.findByStatus(StatusOrderEnum.ABERTA)
                .orElse(new Order());
        Double totalPrice = order.getTotalPrice();
        Perfume perfume = perfumeService.getById(idPerfume);
        totalPrice += perfume.getPrice();

        order = adicionarPerfumeOrderList(order, perfume);
        order.setStatus(StatusOrderEnum.ABERTA);
        order.setTotalPrice(totalPrice);
        order.setUser(user);
        order = save(order);

        return getOrderResponse(order);
    }

    private Order adicionarPerfumeOrderList(Order order, Perfume perfume) {
        List<OrderItem> orderItemList = order.getOrderItemList();
        boolean perfumeNaOrderList = false;
        if (Objects.isNull(orderItemList)) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPerfume(perfume);
            orderItem.setQuantity(orderItem.getQuantity()+1);
            orderItemList.add(orderItem);
        } else {
            for (OrderItem orderItem : orderItemList) {
                if (orderItem.getPerfume() == perfume) {
                    perfumeNaOrderList = true;
                    orderItem.setQuantity(orderItem.getQuantity() + 1);
                }
            }
            if (!perfumeNaOrderList) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setPerfume(perfume);
                orderItem.setQuantity(orderItem.getQuantity()+1);
                orderItemList.add(orderItem);
            }
        }
        order.setOrderItemList(orderItemList);
        order.setTotalPrice(order.getTotalPrice()+perfume.getPrice());
        return order;
    }

    @Transactional
    @Override
    public OrderResponse removerPerfume(Long idPerfume) throws RegraDeNegocioException {
        Order order = orderRepository.findByStatus(StatusOrderEnum.ABERTA)
                .orElseThrow(() -> new RegraDeNegocioException("Não é possível remover o produto de um pedido fechado."));
        Perfume perfume = perfumeService.getById(idPerfume);

        removerPerfumeOrderList(order, perfume);
        save(order);
        return getOrderResponse(order);
    }

    private Order removerPerfumeOrderList(Order order, Perfume perfume) throws RegraDeNegocioException {
        List<OrderItem> orderItemList = order.getOrderItemList();
        boolean perfumeNaOrderList = false;
        Double valorRemovido = 0.0;
        if (Objects.isNull(orderItemList)) {
            throw new RegraDeNegocioException("Não é possível remover um item sem termos itens no pedido.");
        }
        for (OrderItem orderItem : orderItemList) {
            if (orderItem.getPerfume() == perfume) {
                perfumeNaOrderList = true;
                orderItem.setQuantity(orderItem.getQuantity() - 1);
                valorRemovido += perfume.getPrice();
            }
        }
        if (!perfumeNaOrderList) {
            throw new RegraDeNegocioException("Não é possível remover um item que não está no pedido.");
        }
        Integer indexRemover = -1;
        for (int i = 0; i < orderItemList.size(); i++) {
            if (orderItemList.get(i).getQuantity() <= 0) {
                indexRemover = i;
            }
        }

        if (indexRemover != -1) {
            OrderItem orderItem = orderItemList.get(indexRemover);
            orderItemList.remove(orderItem);
            orderItem.setPerfume(null);
        }
        order.setOrderItemList(orderItemList);
        order.setTotalPrice(order.getTotalPrice()-valorRemovido);
        return order;
    }

    @Override
    public OrderResponse finalizarPedido() throws RegraDeNegocioException {
        User user =  usuarioService.getUserLogado();
        Order order = orderRepository.findByStatus(StatusOrderEnum.ABERTA)
                .orElseThrow(() -> new RegraDeNegocioException("Não é possível finalizar um pedido sem ter algum pedido em aberto."));
        order.setStatus(StatusOrderEnum.FECHADA);
        order = save(order);

        return getOrderResponse(order);
    }

    @Override
    public Order getById(Long orderId) throws RegraDeNegocioException {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RegraDeNegocioException(ORDER_NOT_FOUND));
    }

    @Override
    public OrderResponse findById(Long orderId) throws RegraDeNegocioException {
        Order order = getById(orderId);
        return getOrderResponse(order);
    }

    @Override
    public List<OrderItemResponse> getOrderItemsByOrderId(Long id) throws RegraDeNegocioException {
        Order order = getById(id);
        List<OrderItem> orderItemList = order.getOrderItemList();
        List<OrderItemResponse> orderItemResponseList = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            orderItemResponseList.add(objectMapper.convertValue(orderItem, OrderItemResponse.class));
        }
        return orderItemResponseList;
    }

    @Override
    public Page<OrderResponse> meusPedidos(Pageable pageable) throws RegraDeNegocioException {
        User user = usuarioService.getUserLogado();
        String email = user.getEmail();
        Page<Order> orders = orderRepository.findByUser_EmailAndStatus(email, StatusOrderEnum.FECHADA, pageable);
        if (Objects.isNull(orders)) {
            throw new RegraDeNegocioException("Não há pedidos");
        }
        List<Order> orderList = orders.getContent();
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (Order order : orderList) {
            orderResponseList.add(getOrderResponse(order));
        }
        return new PageImpl<>(orderResponseList);
    }

    @Override
    public OrderResponse meuCarrinho() throws RegraDeNegocioException {
        User user = usuarioService.getUserLogado();
        String email = user.getEmail();
        Order order = orderRepository.findByUser_EmailAndStatus(email, StatusOrderEnum.ABERTA);
        if (Objects.isNull(order)) {
            throw new RegraDeNegocioException("Não há itens no carrinho.");
        }
        List<OrderItem> orderItemList = order.getOrderItemList();
        List<OrderItemResponse> orderItemResponseList = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            orderItemResponseList.add(objectMapper.convertValue(orderItem, OrderItemResponse.class));
        }
        OrderResponse orderResponse = objectMapper.convertValue(order, OrderResponse.class);
        orderResponse.setOrderItemList(orderItemResponseList);
        return orderResponse;
    }

    @Override
    public Page<OrderResponse> list(Pageable pageable) {

        Page<Order> orders = orderRepository.findAll(pageable);
        List<Order> orderList = orders.getContent();
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (Order order : orderList) {
            orderResponseList.add(getOrderResponse(order));
        }
        return new PageImpl<>(orderResponseList);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public String delete(Long orderId) throws RegraDeNegocioException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RegraDeNegocioException(ORDER_NOT_FOUND));
        List<OrderItem> orderItemList = order.getOrderItemList();
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrder(null);
            orderItemRepository.deleteOrderItem(orderItem.getId());
        }
        order.setOrderItemList(null);
        orderRepository.delete(order);
        return "Order deleted successfully";
    }

    private OrderResponse getOrderResponse(Order order) {
        List<OrderItemResponse> orderItemResponseList = new ArrayList<>();
        List<OrderItem> orderItemList = order.getOrderItemList();
        for (OrderItem orderItem : orderItemList) {
            orderItemResponseList.add(objectMapper.convertValue(orderItem, OrderItemResponse.class));
        }
        OrderResponse orderResponse = objectMapper.convertValue(order, OrderResponse.class);
        orderResponse.setOrderItemList(orderItemResponseList);

        return orderResponse;
    }
}
