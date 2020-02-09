package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.ShippingAddress;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ShippingAddressRepositoryImpl implements ShippingAddressRepository {
	private static final Set<ShippingAddress> SHIPPING_ADDRESS = new CopyOnWriteArraySet<>();

	@Override
	public ShippingAddress save(ShippingAddress shippingAddress) {
		SHIPPING_ADDRESS.add(shippingAddress);

		return shippingAddress;
	}

	@Override
	public Optional<ShippingAddress> findOne(long shippingAddressId) {

		return SHIPPING_ADDRESS.stream()
						.filter(shippingAddress -> shippingAddress.getId().equals(shippingAddressId))
						.findFirst();
	}
}
