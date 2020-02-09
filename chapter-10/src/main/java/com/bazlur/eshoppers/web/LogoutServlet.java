package com.bazlur.eshoppers.web;

import com.bazlur.eshoppers.util.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final Logger LOGGER
					= LoggerFactory.getLogger(LogoutServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
					throws IOException {
		LOGGER.info("Logging out");
		SecurityContext.logout(req);
		resp.sendRedirect("/login?logout=true");
	}
}
