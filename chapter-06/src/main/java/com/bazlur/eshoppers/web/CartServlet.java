package com.bazlur.eshoppers.web;

import com.bazlur.eshoppers.domain.Cart;
import com.bazlur.eshoppers.domain.User;
import com.bazlur.eshoppers.repository.CartItemRepositoryImpl;
import com.bazlur.eshoppers.repository.CartRepositoryImpl;
import com.bazlur.eshoppers.repository.ProductRepositoryImpl;
import com.bazlur.eshoppers.service.CartService;
import com.bazlur.eshoppers.service.CartServiceImpl;
import com.bazlur.eshoppers.util.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add-to-cart")
public class CartServlet extends HttpServlet {
	private static final Logger LOGGER = LoggerFactory.getLogger(CartServlet.class);

	private CartService cartService
					= new CartServiceImpl(new CartRepositoryImpl(),
								new ProductRepositoryImpl(),
										new CartItemRepositoryImpl());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
		var productId = req.getParameter("productId");

		LOGGER.info(
						"Received request to add product with id: {} to cart",
						productId);

		var cart = getCart(req);
		cartService.addProductToCart(productId, cart);

		resp.sendRedirect("/home");
	}

	private Cart getCart(HttpServletRequest req) {
		final User currentUser = SecurityContext.getCurrentUser(req);

		return cartService.getCartByUser(currentUser);
	}
}
