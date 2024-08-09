package com.jsp.MechBank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/CheckBalance")
public class  CheckBalance extends HttpServlet
{
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
  {

		String mobileNumber = req.getParameter("mobilenumber");
		String password = req.getParameter("password");
		PrintWriter writer = resp.getWriter();

		String url = "jdbc:mysql://localhost:3306/teca44?user=root&password=12345";
		String select = "select * from bank where  moblie_nuber=? and password=?";
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setString(1, mobileNumber);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			resp.setContentType("text/html");
			
			if (rs.next())
			{
				String name = rs.getString(2);
				double damount = rs.getDouble(5);	
				
				RequestDispatcher dispatcher = req.getRequestDispatcher("CheckBalance.html");
				dispatcher.include(req, resp);
				
				writer.println("<center><h1>name : "+name+"</h1></center>");
				writer.println("<center><h1>Balance : "+damount+"</h1></center>");					
			} 
			else
			{
				RequestDispatcher dispatcher1 = req.getRequestDispatcher("CheckBalance.html");
				dispatcher1.include(req, resp);
				writer.println("<center><h1>InValid Details</h1></center>");
			}
		} 
		catch (Exception e) 
		{
		  	e.printStackTrace();
		}
  }
}