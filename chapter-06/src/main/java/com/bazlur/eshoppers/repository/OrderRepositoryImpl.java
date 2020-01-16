package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.Order;

import java.util.HashSet;
import java.util.Set;

public class OrderRepositoryImpl implements OrderRepository {
	private static final Set<Order> ORDERS = new HashSet<>();

	@Override
	public Order save(Order order) {
		ORDERS.add(order);

		return order;
	}
}
