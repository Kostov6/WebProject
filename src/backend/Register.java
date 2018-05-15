package backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.routines.EmailValidator;

import backend.core.managers.RegisterManager;
import exceptions.RegistrationException;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
/*
	private static final String JDBC_PASS = "TooMuchSwag69";

	public Register() {
		super();
	    try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	*/
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// public for test purposes
	@Override
	public void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		
		
		
		String password = request.getParameter("password");
		String passwordRepeat = request.getParameter("passwortRepeat");

		String status="Registration successful";
		try {
			attemptRerister(username, password, passwordRepeat);
		} catch (RegistrationException | SQLException e) {
			e.printStackTrace();
			status=e.getMessage();
		}

		PrintWriter writer = response.getWriter();
		writer.println(status);
		writer.close();

	}

	private void attemptRerister(String username, String password,
			String repeatedPassword) throws RegistrationException, SQLException {

		

		if (checkUsernameAlreadyTaken(username))
			throw new RegistrationException("This username is already in use");
		if (!password.equals(repeatedPassword))
			throw new RegistrationException("Passwords don't match");

		RegisterManager.registerInDatabase(username, password);

	}

	
	private boolean checkUsernameAlreadyTaken(String username) throws SQLException {
		

		return RegisterManager.checkUsernameAlreadyTaken(username);
	}

}
