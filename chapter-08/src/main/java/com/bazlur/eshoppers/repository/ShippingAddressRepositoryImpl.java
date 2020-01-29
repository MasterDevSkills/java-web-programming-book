package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.ShippingAddress;

import java.util.HashSet;
import java.util.Set;

public class ShippingAddressRepositoryImpl implements ShippingAddressRepository {
	private static final Set<ShippingAddress> SHIPPING_ADDRESS = new HashSet<>();

	@Override
	public ShippingAddress save(ShippingAddress shippingAddress) {
		SHIPPING_ADDRESS.add(shippingAddress);

		return shippingAddress;
	}
}
