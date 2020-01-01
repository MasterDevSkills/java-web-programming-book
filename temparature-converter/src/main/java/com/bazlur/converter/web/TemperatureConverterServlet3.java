package com.bazlur.converter.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/convert3")
public class TemperatureConverterServlet3 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/convert.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var temp = req.getParameter("temp");

        if (temp != null && temp.length() > 0) {
            double temperatureInC = Double.parseDouble(temp);
            double temperatureInF = (temperatureInC * 9 / 5) + 32;

            req.setAttribute("result", temperatureInF);
        }

        req.getRequestDispatcher("/WEB-INF/result.jsp")
                .forward(req, resp);
    }
}
