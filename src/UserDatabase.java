
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

/**
 * Servlet implementation class UserDatabase
 */
@WebServlet("/admin/UserDatabase")
public class UserDatabase extends HttpServlet {
	private static final long serialVersionUID = 1L;


	private static final String JDBC_PASS = "matematika6";
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserDatabase() {
		super();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String method = request.getParameter("method");
		if (method != null) {
			switch (method) {
			case "clear":

				break;
			default:
				break;
			}
		}
		Connection connection;
		PrintWriter writer = response.getWriter();

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/project", "root", JDBC_PASS);

			Statement statement = connection.createStatement();

			ResultSet res1 = statement.executeQuery("select * from users;");

			statement = connection.createStatement();

			ResultSet res2 = statement
					.executeQuery("select * from user_roles;");

			while (res1.next()) {
				res2.next();
				writer.println(res1.getString("user_name") + " "
						+ res1.getString("user_pass") + " | Confirm user: "
						+ res2.getString("user_name") + " Role: "
						+ res2.getString("role_name"));
			}

			switch (method) {
			case "clear":
				writer.println("Running command : " + method);
				clearUsers(connection);
				break;
			default:
				break;
			}

			writer.close();

			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void clearUsers(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		// stmt.update
	}


}
