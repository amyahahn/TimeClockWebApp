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

import com.ahahn.timeclockwebapp.utils.DBUtils;
import com.ahahn.timeclockwebapp.utils.MyUtils;

@WebServlet(urlPatterns = { "/deleteTimeClockEntry" })
public class DeleteTimeClockEntryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteTimeClockEntryServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);

		String code = (String) request.getParameter("code");

		String errorString = null;

		try {
			DBUtils.deleteTimeClockEntry(conn, code);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		} 
		
		// If has an error, redirecte to the error page.
		if (errorString != null) {
			// Store the information in the request attribute, before forward to views.
			request.setAttribute("errorString", errorString);
			// 
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/deleteTimeClockEntryErrorView.jsp");
			dispatcher.forward(request, response);
		}
		// If everything nice.
		// Redirect to the timeClockEntry listing page.		
		else {
			response.sendRedirect(request.getContextPath() + "/timeClockEntryList");
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}