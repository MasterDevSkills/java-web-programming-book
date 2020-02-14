package com.bazlur.converter.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/convert2")
public class TemperatureConverterServlet2 extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req,
											 HttpServletResponse resp)
					throws ServletException, IOException {
		var writer = resp.getWriter();

		writer.println("" +
						"<html>" +
						"   <head>" +
						"       <title>Temperature Converter</title>" +
						"   </head>" +
						"   <body>" +
						"       <h1>Celsius to Fahrenheit conversion</h1>" +
						"       <form action=\"/convert2\" method=\"post\">" +
						"           <input type =\"number\" name=\"temperature\"/>" +
						"           <input type=\"submit\" value=\"Submit\"/>" +
						"       </form>" +
						"</body>" +
						"</html>");
	}

	@Override
	protected void doPost(HttpServletRequest req,
                        HttpServletResponse resp)
					throws ServletException, IOException {

		var writer = resp.getWriter();
		var temperature = req.getParameter("temperature");

		if (temperature != null && temperature.length() > 0) {
			double temperatureInC = Double.parseDouble(temperature);
			double temperatureInF = (temperatureInC * 9 / 5) + 32;

			writer.println("" +
          "<html>" +
          "   <head>" +
          "       <title>Temperature Converter</title>" +
          "   </head>" +
          "   <body>" +
          "       <p> Temperature in Fahrenheit is:" +
          "           " + temperatureInF + "</p>" +
          "</body>" +
          "</html>");
		}
	}
}
