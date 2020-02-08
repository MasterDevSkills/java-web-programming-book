package com.bazlur.eshoppers.web;

import com.bazlur.eshoppers.dto.UserDTO;
import com.bazlur.eshoppers.service.UserService;
import com.bazlur.eshoppers.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
	private final static Logger LOGGER = LoggerFactory.getLogger(SignupServlet.class);

	@Inject
	private UserService userService;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {
		LOGGER.info("serving singup page");

		req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {
		var userDTO = copyParametersTo(req);
		var errors = ValidationUtil.getInstance().validate(userDTO);

		if (!errors.isEmpty()) {
			req.setAttribute("userDto", userDTO);
			req.setAttribute("errors", errors);
			LOGGER.info("User sent invalid data: {}", userDTO);
			req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
		} else if (userService.isNotUniqueUsername(userDTO)) {
			LOGGER.info("Username: {} is already exist", userDTO.getUsername());
			errors.put("username", "The username already exists. Please use a different username");
			req.setAttribute("userDto", userDTO);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
		} else {
			LOGGER.info("user is valid, creating a new user with: {}", userDTO);
			userService.saveUser(userDTO);
			resp.sendRedirect("/login");
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
}

