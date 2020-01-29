package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.Order;
import com.bazlur.eshoppers.domain.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderRepositoryImpl implements OrderRepository {
	private static final Set<Order> ORDERS = new HashSet<>();

	@Override
	public Order save(Order order) {
		ORDERS.add(order);

		return order;
	}

	public Set<Order> findOrderByUser(User user) {

		return ORDERS.stream()
						.filter(order -> order.getUser().equals(user))
						.collect(Collectors.toSet());
	}
}
