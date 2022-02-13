package com.ahahn.timeclockwebapp.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ahahn.timeclockwebapp.beans.TimeClockEntry;
import com.ahahn.timeclockwebapp.beans.Employee;

public class DBUtils {

	public static Employee findUser(Connection conn, //
			String userName, String password) throws SQLException {

		String sql = "Select a.User_Name, a.Password, a.Gender from USER_ACCOUNT a " //
				+ " where a.User_Name = ? and a.password= ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setString(2, password);
		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			String gender = rs.getString("Gender");
			Employee user = new Employee();
			user.setUserName(userName);
			user.setPassword(password);
			user.setGender(gender);
			return user;
		}
		return null;
	}

	public static Employee findUser(Connection conn, String userName) throws SQLException {

		String sql = "Select a.User_Name, a.Password, a.Gender from USER_ACCOUNT a "//
				+ " where a.User_Name = ? ";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);

		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			String password = rs.getString("Password");
			String gender = rs.getString("Gender");
			Employee user = new Employee();
			user.setUserName(userName);
			user.setPassword(password);
			user.setGender(gender);
			return user;
		}
		return null;
	}

	public static List<TimeClockEntry> queryProduct(Connection conn) throws SQLException {
		String sql = "Select a.Code, a.Name, a.Price from PRODUCT a ";

		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rs = pstm.executeQuery();
		List<TimeClockEntry> list = new ArrayList<TimeClockEntry>();
		while (rs.next()) {
			String code = rs.getString("Code");
			String name = rs.getString("Name");
			float price = rs.getFloat("Price");
			TimeClockEntry timeClockEntry = new TimeClockEntry();
			timeClockEntry.setCode(code);
			timeClockEntry.setName(name);
			timeClockEntry.setPrice(price);
			list.add(timeClockEntry);
		}
		return list;
	}

	public static TimeClockEntry findProduct(Connection conn, String code) throws SQLException {
		String sql = "Select a.Code, a.Name, a.Price from PRODUCT a where a.Code=?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, code);

		ResultSet rs = pstm.executeQuery();

		while (rs.next()) {
			String name = rs.getString("Name");
			float price = rs.getFloat("Price");
			TimeClockEntry timeClockEntry = new TimeClockEntry(code, name, price);
			return timeClockEntry;
		}
		return null;
	}

	public static void updateProduct(Connection conn, TimeClockEntry timeClockEntry) throws SQLException {
		String sql = "Update TimeClockEntry set Name =?, Price=? where Code=? ";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, timeClockEntry.getName());
		pstm.setFloat(2, timeClockEntry.getPrice());
		pstm.setString(3, timeClockEntry.getCode());
		pstm.executeUpdate();
	}

	public static void insertTimeClockEntry(Connection conn, TimeClockEntry timeClockEntry) throws SQLException {
		String sql = "Insert into PRODUCT(Code, Name,Price) values (?,?,?)";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, timeClockEntry.getCode());
		pstm.setString(2, timeClockEntry.getName());
		pstm.setFloat(3, timeClockEntry.getPrice());

		pstm.executeUpdate();
	}

	public static void deleteTimeClockEntry(Connection conn, String code) throws SQLException {
		String sql = "Delete From PRODUCT where Code= ?";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, code);

		pstm.executeUpdate();
	}

}

