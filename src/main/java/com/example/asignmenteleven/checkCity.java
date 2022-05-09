package com.example.asignmenteleven;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet("/city-page")
public class checkCity extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String countryValue = request.getParameter("userChoice");
        //request.setAttribute("country", countryValue);

        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "B)to123");

            String queryVar = "select c.Name from city as c\n" +
                    "left join  country as ctr\n" +
                    "on c.countryCode=ctr.code\n" +
                    "where ctr.name=\'" + countryValue + "\'";

            PreparedStatement psObject = myConnection.prepareStatement(queryVar);
            ResultSet resSet = psObject.executeQuery();

            while (resSet.next()) {
                String cityName = resSet.getString("Name");
                request.setAttribute("printValue", cityName);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        getServletContext().getRequestDispatcher("/submitPage.jsp").forward(request, response);
    }
}