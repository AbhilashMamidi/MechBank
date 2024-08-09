package com.jsp.MechBank;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
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
@WebServlet("/Amount")
public class Amount extends HttpServlet
{
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
  {
	String tamount = req.getParameter("amount");
	double amount = Double.parseDouble(tamount);
	PrintWriter writer = resp.getWriter();
	resp.setContentType("text/html");
	
	HttpSession session=req.getSession();
	Double damount = (Double) session.getAttribute("damount");
	String mobilenumber = (String) session.getAttribute("mb");
    String password = (String) session.getAttribute("password");
	             
	             
	
	
	if(damount>=amount)
	{
	  double sub=damount-amount;
	  
	  String url="jdbc:mysql://localhost:3306/teca44?user=root&password=12345";
	  
	  String update="update bank set amount=? where  moblie_nuber=? and password=?";
	  
	    
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(update);
			ps.setDouble(1, sub);
			ps.setString(2, mobilenumber);
			ps.setString(3, password);
			int result = ps.executeUpdate();
			
			if(result!=0)
			{
			       RequestDispatcher dispatcher = req.getRequestDispatcher("MechBank.html");
			       dispatcher.include(req, resp);
			       writer.println("<center><h1>Withdraw Successful</h1></center>");
			}
			else
			{
				   RequestDispatcher dispatcher = req.getRequestDispatcher("Withdraw.html");
			       dispatcher.include(req, resp);
			       writer.println("<center><h1>Server Busy</h1></center>");
			}
			
	    	}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}	     
	}
	else
	{
		RequestDispatcher dispatcher = req.getRequestDispatcher("Amount.html");
		dispatcher.include(req, resp);
		writer.println("<center><h1>Insufficient Balance</h1></center>");
		
	}
	
  }
}
