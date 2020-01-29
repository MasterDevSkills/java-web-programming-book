package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.Order;
import com.bazlur.eshoppers.domain.User;

import java.util.Set;

public interface OrderRepository {

	Order save(Order order);

	Set<Order> findOrderByUser(User user);
}
