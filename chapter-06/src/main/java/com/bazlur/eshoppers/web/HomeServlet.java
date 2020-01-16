package com.bazlur.eshoppers.web;

import com.bazlur.eshoppers.domain.Cart;
import com.bazlur.eshoppers.dto.ProductDTO;
import com.bazlur.eshoppers.repository.CartItemRepositoryImpl;
import com.bazlur.eshoppers.repository.CartRepositoryImpl;
import com.bazlur.eshoppers.repository.ProductRepositoryImpl;
import com.bazlur.eshoppers.service.CartService;
import com.bazlur.eshoppers.service.CartServiceImpl;
import com.bazlur.eshoppers.service.ProductService;
import com.bazlur.eshoppers.service.ProductServiceImpl;
import com.bazlur.eshoppers.util.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeServlet.class);

	private ProductService productService
					= new ProductServiceImpl(new ProductRepositoryImpl());

	private CartService cartService
					= new CartServiceImpl(new CartRepositoryImpl(), new ProductRepositoryImpl(),
					new CartItemRepositoryImpl());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {
		LOGGER.info("Serving home page");

		List<ProductDTO> allProducts = productService.findAllProductSortedByName();
		LOGGER.info("Total product found {}", allProducts.size());

		Cart cart = cartService.getCartByUser(SecurityContext.getCurrentUser(req));

		req.setAttribute("cart", cart);
		req.setAttribute("products", allProducts);

		req.getRequestDispatcher("/WEB-INF/home.jsp")
						.forward(req, resp);
	}
}
