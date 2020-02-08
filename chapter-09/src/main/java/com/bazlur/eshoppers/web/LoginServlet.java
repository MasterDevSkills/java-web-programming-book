package com.bazlur.eshoppers.web;

import com.bazlur.eshoppers.domain.User;
import com.bazlur.eshoppers.dto.LoginDTO;
import com.bazlur.eshoppers.exceptions.UserNotFoundException;
import com.bazlur.eshoppers.service.UserService;
import com.bazlur.eshoppers.util.SecurityContext;
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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginServlet.class);

	@Inject
	private UserService userService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {
		LOGGER.info("Serving login page");
		String logout = req.getParameter("logout");

		if (logout != null && Boolean.parseBoolean(logout)) {
			req.setAttribute("message", "You have been successfully logged out.");
		}

		req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var loginDTO = new LoginDTO(req.getParameter("username"),
						req.getParameter("password"));
		LOGGER.info("Received long data: {}", loginDTO);

		var errors = ValidationUtil.getInstance().validate(loginDTO);
		if (!errors.isEmpty()) {
			LOGGER.info("Failed to login, sending login form again");
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/WEB-INF/login.jsp")
							.forward(req, resp);
		}

		try {
			login(loginDTO, req);

			LOGGER.info("Login successful, redirecting to home page");
			resp.sendRedirect("/home");
		} catch (UserNotFoundException e) {
			LOGGER.error("incorrect username/password", e);

			errors.put("username", "Incorrect username/password");
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/WEB-INF/login.jsp")
							.forward(req, resp);
		}
	}

	private void login(LoginDTO loginDTO, HttpServletRequest req)
					throws UserNotFoundException {
		User user = userService.verifyUser(loginDTO);

		SecurityContext.login(req, user);
	}
}
