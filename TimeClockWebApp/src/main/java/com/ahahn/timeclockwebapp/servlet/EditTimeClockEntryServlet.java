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

@WebServlet(urlPatterns = { "/editTimeClockEntry" })
public class EditTimeClockEntryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditTimeClockEntryServlet() {
		super();
	}

	// Show timeClockEntry edit page.
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);

		String code = (String) request.getParameter("code");

		TimeClockEntry timeClockEntry = null;

		String errorString = null;

		try {
			timeClockEntry = DBUtils.findProduct(conn, code);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}

		// If no error.
		// The timeClockEntry does not exist to edit.
		// Redirect to timeClockEntryList page.
		if (errorString != null && timeClockEntry == null) {
			response.sendRedirect(request.getServletPath() + "/timeClockEntryList");
			return;
		}

		// Store errorString in request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("timeClockEntry", timeClockEntry);

		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/editTimeClockEntryView.jsp");
		dispatcher.forward(request, response);

	}

	// After the user modifies the timeClockEntry information, and click Submit.
	// This method will be executed.
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

		try {
			DBUtils.updateProduct(conn, timeClockEntry);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}
		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("timeClockEntry", timeClockEntry);

		// If error, forward to Edit page.
		if (errorString != null) {
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/editTimeClockEntryView.jsp");
			dispatcher.forward(request, response);
		}
		// If everything nice.
		// Redirect to the timeClockEntry listing page.
		else {
			response.sendRedirect(request.getContextPath() + "/timeClockEntryList");
		}
	}

}
