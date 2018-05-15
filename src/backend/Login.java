package backend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/user/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Check for login
		// Search for git cookie
	/*	String gitCookie = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies)
				if (cookie.getName().equals("git_hash")) {
					// dataBaseSearch
					gitCookie = cookie.getValue();
					response.getWriter().append("Hello git user " + gitCookie + "!");

				}
*/
		if (request.getUserPrincipal() == null) {
			// login form
			String user = "";
			String pas = "";
			

			if (request.getParameter("j_username") != null)
				user = request.getParameter("j_username");

			if (request.getParameter("j_password") != null)
				pas = request.getParameter("j_password");

			// login form
			PrintWriter writer = response.getWriter();
			writer.append("<!DOCTYPE html>\r\n" + "<html>\r\n" + "<body>\r\n" + "<h2>Login</h2>\r\n"
					+ "<form method=\"POST\" action=\"j_security_check\" >\r\n" + "  First name:<br>\r\n"
					+ "  <input type=\"text\" name=\"j_username\" value=\"" + user + "\">\r\n" + "  <br>\r\n"
					+ "  Last name:<br>\r\n" + "  <input type=\"text\" name=\"j_password\" value=\"" + pas + "\">\r\n"
					+ "  <br><br>\r\n" + "  <input type=\"submit\" value=\"Submit\">\r\n" + "</form> \r\n" + "\r\n"
					+ "<br>\r\n" + "\r\n" + "<form style=\"margin: 0; padding: 0;\">\r\n"
					+ "	<h4>Or login through GitHub \r\n"
					+ "		<input type=\"button\" style=\"display: inline;\" value=\"Go to GitHub!\" onclick=\"window.location.href='https://github.com/login/oauth/authorize?client_id=099b3b26927004bc8273&scope=user:email%20public_repo'\" />\r\n"
					+ "	</h4>\r\n" + "</form>\r\n" + "\r\n" + "<a href=\"/WebProject/register.html\">\r\n"
					+ "	<button type=\"button\">Register</button> \r\n" + "</a>\r\n" + "</body>\r\n" + "</html>");

			writer.close();
		} 
		else
		{
			response.getWriter().append("Hello "+request.getUserPrincipal().getName()+"!");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		

	}

}
