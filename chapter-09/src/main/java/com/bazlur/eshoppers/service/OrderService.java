package com.bazlur.eshoppers.service;

import com.bazlur.eshoppers.domain.User;
import com.bazlur.eshoppers.dto.ShippingAddressDTO;

public interface OrderService {
	void processOrder(ShippingAddressDTO shippingAddress, User currentUser);
}
