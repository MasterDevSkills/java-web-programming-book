package com.bazlur.eshoppers.service;

import com.bazlur.eshoppers.domain.Cart;
import com.bazlur.eshoppers.domain.CartItem;
import com.bazlur.eshoppers.domain.Product;
import com.bazlur.eshoppers.domain.User;
import com.bazlur.eshoppers.exceptions.ProductNotFoundException;
import com.bazlur.eshoppers.repository.CartItemRepository;
import com.bazlur.eshoppers.repository.CartRepository;
import com.bazlur.eshoppers.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Optional;

public class CartServiceImpl implements CartService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	private final CartItemRepository cartItemRepository;

	public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository) {
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
		this.cartItemRepository = cartItemRepository;
	}

	@Override
	public Cart getCartByUser(User currentUser) {
		if (currentUser == null) {
			throw new IllegalArgumentException("current user cannot be null");
		}

		Optional<Cart> optionalCart = cartRepository.findByUser(currentUser);

		return optionalCart
						.orElseGet(() -> createNewCart(currentUser));
	}

	@Override
	public void addProductToCart(String productId, Cart cart) {
		if (productId == null || productId.length() == 0) {
			throw new IllegalArgumentException("Product id cannot be null");
		}
		Long id = parseProductId(productId);

		Product product = productRepository.findById(id)
						.orElseThrow(() -> new ProductNotFoundException("Product not found by id: " + id));

		addProductToCart(product, cart);

		Integer totalTotalItem = getTotalItem(cart);
		BigDecimal totalPrice = calculateTotalPrice(cart);

		cart.setTotalItem(totalTotalItem);
		cart.setTotalPrice(totalPrice);

		cartRepository.update(cart);
	}

	private void addProductToCart(Product product, Cart cart) {
		var cartItemOptional = findSimilarProductInCart(cart, product);

		var cartItem = cartItemOptional
						.map(this::increaseQuantityByOne)
						.orElseGet(() -> createNewCartItem(product));

		cart.getCartItems().add(cartItem);
	}

	private CartItem createNewCartItem(Product product) {
		var cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantity(1);
		cartItem.setPrice(product.getPrice());

		return cartItemRepository.save(cartItem);
	}

	private CartItem increaseQuantityByOne(CartItem cartItem) {
		cartItem.setQuantity(cartItem.getQuantity() + 1);

		BigDecimal totalPrice = cartItem.getProduct()
						.getPrice()
						.multiply(BigDecimal.valueOf(cartItem.getQuantity()));

		cartItem.setPrice(totalPrice);

		return cartItemRepository.update(cartItem);
	}

	private Optional<CartItem> findSimilarProductInCart(Cart cart, Product product) {

		return cart.getCartItems()
						.stream()
						.filter(cartItem -> cartItem.getProduct().equals(product))
						.findFirst();
	}

	private Integer getTotalItem(Cart cart) {

		return cart.getCartItems()
						.stream()
						.map(CartItem::getQuantity)
						.reduce(0, Integer::sum);
	}

	private BigDecimal calculateTotalPrice(Cart cart) {

		return cart.getCartItems()
						.stream()
						.map(CartItem::getPrice)
						.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private Long parseProductId(String productId) {
		try {
			return Long.parseLong(productId);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Product id must be a number", ex);
		}
	}

	private Cart createNewCart(User currentUser) {
		Cart cart = new Cart();
		cart.setUser(currentUser);

		return cartRepository.save(cart);
	}
}
