package pl.mamarek.backend.store.service;

import lombok.RequiredArgsConstructor;
import pl.mamarek.backend.store.model.Order;
import pl.mamarek.backend.store.model.OrderDto;
import pl.mamarek.backend.store.model.OrderMapper;
import pl.mamarek.backend.store.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional(readOnly = true)
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<OrderDto> getOrderById(Long id) {
        return orderRepository.findById(id).map(orderMapper::toDto);
    }

    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Transactional
    public Optional<OrderDto> updateOrder(Long id, OrderDto orderDto) {
        return orderRepository.findById(id)
                .map(existingOrder -> {
                    orderMapper.updateEntityFromDto(orderDto, existingOrder);
                    Order savedOrder = orderRepository.save(existingOrder);
                    return orderMapper.toDto(savedOrder);
                });
    }

    @Transactional
    public Optional<OrderDto> partialUpdateOrder(Long id, OrderDto orderDto) {
        return orderRepository.findById(id)
                .map(existingOrder -> {
                    orderMapper.partialUpdateEntityFromDto(orderDto, existingOrder);
                    Order savedOrder = orderRepository.save(existingOrder);
                    return orderMapper.toDto(savedOrder);
                });
    }

    @Transactional
    public boolean deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            return false;
        }
        orderRepository.deleteById(id);
        return true;
    }
}
