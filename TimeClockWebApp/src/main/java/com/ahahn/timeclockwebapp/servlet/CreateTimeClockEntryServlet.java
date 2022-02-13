package com.ahahn.timeclockwebapp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.ahahn.timeclockwebapp.beans.TimeClockEntry;
import com.ahahn.timeclockwebapp.utils.DBUtils;
import com.ahahn.timeclockwebapp.utils.MyUtils;

@WebServlet(urlPatterns = { "/createTimeClockEntry" })
public class CreateTimeClockEntryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateTimeClockEntryServlet() {
		super();
	}

	// Show timeClockEntry creation page.
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/createTimeClockEntryView.jsp");
		dispatcher.forward(request, response);
	}

	// When the user enters the timeClockEntry information, and click Submit.
	// This method will be called.
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);

		String code = (String) request.getParameter("code");
		String name = (String) request.getParameter("name");
		String priceStr = (String) request.getParameter("price");
		float price = 0;
		try {
			price = Float.parseFloat(priceStr);
		} catch (Exception e) {
		}
		TimeClockEntry timeClockEntry = new TimeClockEntry(code, name, price);

		String errorString = null;

		// TimeClockEntry ID is the string literal [a-zA-Z_0-9]
		// with at least 1 character
		String regex = "\\w+";

		if (code == null || !code.matches(regex)) {
			errorString = "TimeClockEntry Code invalid!";
		}

		if (errorString == null) {
			try {
				DBUtils.insertTimeClockEntry(conn, timeClockEntry);
			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}
		}

		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("timeClockEntry", timeClockEntry);

		// If error, forward to Edit page.
		if (errorString != null) {
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/createTimeClockEntryView.jsp");
			dispatcher.forward(request, response);
		}
		// If everything nice.
		// Redirect to the timeClockEntry listing page.
		else {
			response.sendRedirect(request.getContextPath() + "/timeClockEntryList");
		}
	}

}