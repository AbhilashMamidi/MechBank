package com.jsp.MechBank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
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
@WebServlet("/ReceiverAccount")
public class ReceiverAccount extends HttpServlet
{ 
   @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
   {
	  String receivermb=req.getParameter("receivermb");
	 // double receivermobile = Double.parseDouble(receivermb);
	  PrintWriter writer = resp.getWriter();
	  
	 
	  
		String url = "jdbc:mysql://localhost:3306/teca44?user=root&password=12345";
	    String select ="select * from bank where moblie_nuber=? ";
	    
	    try 
	    {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setString(1,receivermb);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{				
				   double rdamount = rs.getDouble(5);
				   
				   HttpSession session=req.getSession();
				   session.setAttribute("receivermobile",receivermb);
				   session.setAttribute("rdamount", rdamount);
				
			       RequestDispatcher dispatcher = req.getRequestDispatcher("SendAmount.html");
			       dispatcher.include(req, resp);
			}
			else
			{
				   RequestDispatcher dispatcher = req.getRequestDispatcher("RecieverAccount.html");
			       dispatcher.include(req, resp);
			       writer.println("<center><h1>Server Busy</h1></center>");
			}
			
	    }
		catch (Exception e)
		{			
			e.printStackTrace();
		}	  	    
   }
}
