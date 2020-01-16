package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.Order;

public interface OrderRepository {

	Order save(Order order);
}
