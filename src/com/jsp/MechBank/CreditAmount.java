package com.jsp.MechBank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CreditAmount")
public class CreditAmount extends HttpServlet 
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String tamount = req.getParameter("amount");
		double amount = Double.parseDouble(tamount);
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");

		HttpSession session = req.getSession();
		String mobilenumber = (String) session.getAttribute("mobilenumber");
		String password = (String) session.getAttribute("password");
		Double damount = (double) session.getAttribute("damount");

		if (amount != 0 && amount <= damount) 
		{
			double add = damount + amount;

			String url = "jdbc:mysql://localhost:3306/teca44?user=root&password=12345";
			String update = "update bank set amount=? where  moblie_nuber=? and password=?";

			try 
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement ps = connection.prepareStatement(update);
				ps.setDouble(1, add);
				ps.setString(2, mobilenumber);
				ps.setString(3, password);
				int result = ps.executeUpdate();

				if (result != 0) 
				{
					RequestDispatcher dispatcher = req.getRequestDispatcher("CreditAmount.html");
					dispatcher.include(req, resp);
					writer.println("<center><h1>Amount Credited Successful</h1></center>");
				} else
				{
					RequestDispatcher dispatcher = req.getRequestDispatcher("CreditAmount.html");
					dispatcher.include(req, resp);
					writer.println("<center><h1>Server Busy</h1></center>");
				}

			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

		} else 
		{
			RequestDispatcher dispatcher = req.getRequestDispatcher("CreditAmount.html");
			dispatcher.include(req, resp);
			writer.println("<center><h1> Insufficient Balance</h1></center>");
		}
	}
}
