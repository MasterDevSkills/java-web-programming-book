package com.bazlur.eshoppers.web;

import com.bazlur.eshoppers.dto.ProductDTO;
import com.bazlur.eshoppers.repository.DummyProductRepositoryImpl;
import com.bazlur.eshoppers.service.ProductService;
import com.bazlur.eshoppers.service.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	private ProductService productService
           = new ProductServiceImpl(new DummyProductRepositoryImpl());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {

		List<ProductDTO> allProducts = productService.findAllProductSortedByName();

		req.setAttribute("products", allProducts);

		req.getRequestDispatcher("/WEB-INF/home.jsp")
						.forward(req, resp);
	}
}
