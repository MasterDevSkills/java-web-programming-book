package com.bazlur.converter.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/convert")
public class TemperatureConverterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var writer = resp.getWriter();
        var temp = req.getParameter("temp");

        if (temp != null && temp.length() > 0) {
            double temperatureInC = Double.parseDouble(temp);
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

            return;
        }


        writer.println(
                "<html>" +
                        "   <head>" +
                        "       <title>Temperature Converter</title>" +
                        "   </head>" +
                        "   <body>" +
                        "       <h1>Celsius to Fahrenheit conversion</h1>" +
                        "       <form action=\"/convert\">" +
                        "           <input type =\"number\" name=\"temp\"/>" +
                        "           <input type=\"submit\" value=\"Submit\"/>" +
                        "       </form>" +
                        "</body>" +
                        "</html>");
    }
}
