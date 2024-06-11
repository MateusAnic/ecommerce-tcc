package com.zprmts.tcc.ecommerce.repository;

import com.zprmts.tcc.ecommerce.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderItemRepository  extends JpaRepository<OrderItem, Long> {

    @Transactional
    @Modifying
    @Query(value = """
            DELETE FROM ORDER_ITEM WHERE ID = :id
            """, nativeQuery = true
    )
    Integer deleteOrderItem(@Param("id") Long id);
}
