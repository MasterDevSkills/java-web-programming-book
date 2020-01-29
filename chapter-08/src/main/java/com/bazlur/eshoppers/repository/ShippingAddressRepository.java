package com.bazlur.eshoppers.repository;

import com.bazlur.eshoppers.domain.ShippingAddress;

public interface ShippingAddressRepository {
	ShippingAddress save(ShippingAddress convertTo);
}
