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

@WebServlet("/SendAmount")
public class SendAmount extends HttpServlet 
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String amount = req.getParameter("amount");
		double ramount = Double.parseDouble(amount);
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");

		HttpSession session = req.getSession();
		Double sdamount = (Double) session.getAttribute("damount");
		String mobilenumber = (String) session.getAttribute("mobilenumber");
		String password = (String) session.getAttribute("password");
		String sname=(String) session.getAttribute("name");

		String url = "jdbc:mysql://localhost:3306/teca44?user=root&password=12345";

		HttpSession session1 = req.getSession();
		String receivermobilenumber = (String) session1.getAttribute("receivermobile");
		Double rdAmount = (Double) session1.getAttribute("rdamount");

		if (ramount >= 0)
		{

			if (ramount <= sdamount) 
			{

				double sub = sdamount - ramount;
				String update = "update bank set amount=? where moblie_nuber=?";
				try 
				{
					Class.forName("com.mysql.jdbc.Driver");
					Connection connection = DriverManager.getConnection(url);
					PreparedStatement sendps = connection.prepareStatement(update);

					sendps.setDouble(1, sub);
					sendps.setString(2, mobilenumber);
					int sendresult = sendps.executeUpdate();

					if (sendresult != 0) 
					{
						double add = rdAmount + ramount;
						String reciveupdate = "update bank set amount=? where moblie_nuber=?";

						PreparedStatement reciverps = connection.prepareStatement(reciveupdate);

						reciverps.setDouble(1, add);
						reciverps.setString(2, receivermobilenumber);
						int reciveresult = reciverps.executeUpdate();
						if (reciveresult != 0) 
						{
							RequestDispatcher dispatcher = req.getRequestDispatcher("SendAmount.html");
							dispatcher.include(req, resp);
							writer.println("<center><h1>Transaction Successfull</h1></center>");
							
							
							  writer.println("<center><h1> Amount :" +sub+"</h1></center>");
							  writer.println("<center><h1> Mobile Number :" +mobilenumber.substring(0, 4)+"****"+mobilenumber.substring(8,10)+"</h1></center>");
							  writer.println("<center><h1> Account holder  Name :" +sname+"</h1></center>");
						}
					}
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else 
			{
				RequestDispatcher dispatcher = req.getRequestDispatcher("SendAmount.html");
				dispatcher.include(req, resp);
				writer.println("<center><h1>Insufficient Balance</h1></center>");
			}
		}
		else 
		{
			RequestDispatcher dispatcher = req.getRequestDispatcher("SendAmount.html");
			dispatcher.include(req, resp);
			writer.println("<center><h1>Invalid Amount</h1></center>");
		}

	}
}

