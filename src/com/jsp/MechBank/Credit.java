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
@WebServlet("/Credit")
public class Credit extends HttpServlet 
{
   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
   {
	  String mobilenumber=req.getParameter("mobilenumber");
	  String password=req.getParameter("password");
	  resp.setContentType("text/html");
	

	                   String url="jdbc:mysql://localhost:3306/teca44?user=root&password=12345";
	                   String select=" select * from bank  where moblie_nuber=? and password=?";
	                   try 
                       {
	                	Class.forName("com.mysql.jdbc.Driver");
						Connection connection = DriverManager.getConnection(url);
						PreparedStatement ps = connection.prepareStatement(select);
						ps.setString(1, mobilenumber);
						ps.setString(2, password);
						ResultSet rs = ps.executeQuery();
						PrintWriter writer = resp.getWriter();
						
						HttpSession session=req.getSession();
						
						if(rs.next())
						{
							double damount=rs.getDouble(5);
							session.setAttribute("damount", damount);
							session.setAttribute("mobilenumber", mobilenumber);
							session.setAttribute("password", password);
							
							RequestDispatcher dispatcher=req.getRequestDispatcher("CreditAmount.html");
						    dispatcher.include(req, resp);		
						}
						else
						{
							RequestDispatcher dispatcher=req.getRequestDispatcher("Credit.html");
						    dispatcher.include(req, resp);
							writer.println("Server Problem");
						}
					   } 
                       catch (Exception e) 
                       {
						e.printStackTrace();
					   }                         
	                   
   }
}
