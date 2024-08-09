package com.jsp.MechBank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/MobileTransaction")
public class MobileTransaction extends HttpServlet
{
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
  {
	  String mobilenumber=req.getParameter("mobilenumber");
	  String password=req.getParameter("password");
	  
	  String url = "jdbc:mysql://localhost:3306/teca44?user=root&password=12345";
	  String select = "select * from bank where  moblie_nuber=? and password=?";
	  
	  try 
	  {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(url);
		PreparedStatement ps = connection.prepareStatement(select);
		ps.setString(1, mobilenumber);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		if(rs.next())
		{
		  double damount=rs.getDouble(5);
		  String name=rs.getString(2);
		  
		  HttpSession session=req.getSession();
		  session.setAttribute("mobilenumber", mobilenumber);
		  session.setAttribute("password", password);
		  session.setAttribute("damount", damount);
		  session.setAttribute("name",name);
		  
		    RequestDispatcher  dispatcher = req.getRequestDispatcher("RecieverAccount.html");
		    dispatcher.include(req, resp);
		    
		   // writer.println("<center><h1></h1></center>");
		  
		}  
		else
		{
			    writer.println("<center><h1>Invalid Details</h1></center>");
			    RequestDispatcher  dispatcher = req.getRequestDispatcher("MobileTransaction.html");
			    dispatcher.include(req, resp);
		}
	  }
	  catch (Exception e) 
	  {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}

