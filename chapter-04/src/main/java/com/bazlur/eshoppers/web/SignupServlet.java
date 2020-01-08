package com.bazlur.eshoppers.web;

import com.bazlur.eshoppers.dto.UserDTO;
import com.bazlur.eshoppers.repository.UserRepositoryImpl;
import com.bazlur.eshoppers.service.UserService;
import com.bazlur.eshoppers.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
	private final static Logger LOGGER = LoggerFactory.getLogger(SignupServlet.class);

	private UserService userService = new UserServiceImpl(new UserRepositoryImpl());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {
		LOGGER.info("serving singup page");

		req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {
		UserDTO userDTO = copyParametersTo(req);

		if (isValid(userDTO)) {
			LOGGER.info("user is valid, creating a new user with: {}", userDTO);
			userService.saveUser(userDTO);
			resp.sendRedirect("/home");
		} else {
			LOGGER.info("User sent invalid data: {}", userDTO);
			req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
		}
	}

	private UserDTO copyParametersTo(HttpServletRequest req) {
		var userDTO = new UserDTO();
		userDTO.setFirstName(req.getParameter("firstName"));
		userDTO.setLastName(req.getParameter("lastName"));
		userDTO.setPassword(req.getParameter("password"));
		userDTO.setPasswordConfirmed(req.getParameter("passwordConfirmed"));
		userDTO.setEmail(req.getParameter("email"));
		userDTO.setUsername(req.getParameter("username"));

		return userDTO;
	}

	private boolean isValid(UserDTO userDTO) {
		//we will implement it later
		return true;
	}
}
