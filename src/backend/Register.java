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

	private static final String JDBC_PASS = "TooMuchSwag69";

	public Register() {
		super();
	    try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			status=e.getMessage();
		}

		PrintWriter writer = response.getWriter();
		writer.println(status);
		writer.close();

	}

	private void attemptRerister(String email, String password,
			String repeatedPassword) throws RegistrationException, SQLException {

		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost/project", "root", JDBC_PASS);

		if (!emailExists(email))
			throw new RegistrationException("Email does not exist");
		if (checkEmailAlreadyTaken(connection,email))
			throw new RegistrationException("This email is already in use");
		if (!password.equals(repeatedPassword))
			throw new RegistrationException("Passwords don't match");

		registerInDatabase(connection,email, password);

		connection.close();
	}

	//for testing
	public static void main(String[] args) throws SQLException {
		Register reg = new Register();
		String email="email";
		
		System.out.println
		("select * from users where user_name='" + email+"';");
		// String user="user";
		// String password="pass";
		// System.out.println("insert into users('user_name','user_pass') values('"
		// + user+"','"
		// + password +"');");
		// System.out.println
		// ("insert into user_roles('user_name','role_name') values('"
		// + user+"','"
		// + "user" +"');");
		// reg.registerInDatabase(null, null);
	}

	private void registerInDatabase(Connection connection,String user, String password) throws SQLException{
		Statement statement = connection.createStatement();
		
		statement.executeUpdate("insert into users values('" + user + "','"
				+ password + "');");

		statement.executeUpdate("insert into user_roles values('" + user
				+ "','" + "user" + "');");
	}

	private boolean emailExists(String email) {
		boolean valid = EmailValidator.getInstance().isValid(email);
		return valid;
	}

	private boolean checkEmailAlreadyTaken(Connection connection,String email) throws SQLException {
		Statement statement = connection.createStatement();
		
		ResultSet res = statement
				.executeQuery("select * from users where user_name='" + email+"';");

		return res.next();
	}

}
