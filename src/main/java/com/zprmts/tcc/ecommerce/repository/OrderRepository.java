package com.zprmts.tcc.ecommerce.repository;

import com.zprmts.tcc.ecommerce.domain.Order;

import com.zprmts.tcc.ecommerce.enums.StatusOrderEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUser_Email(String email, Pageable pageable);
    Optional<Order> findByStatus(StatusOrderEnum status);
    Page<Order> findByUser_EmailAndStatus(String email, StatusOrderEnum status, Pageable pageable);
    Order findByUser_EmailAndStatus(String email, StatusOrderEnum status);
}
