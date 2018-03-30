

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//		
//		PrintWriter writer=response.getWriter();
//		writer.println("<!DOCTYPE html>");
//		writer.println("<html>");
//		writer.println("<body>");
//		writer.println("<h2>Register</h1>");
//		writer.println("<form method =\"POST\" action=\"/NewWeb/Register\">");
//		writer.println("  Register username:<br>");
//		writer.println("<input type=\"text\" name=\"username\">");
//		writer.println("  <br>");
//		writer.println("  Register password:<br>");
//		writer.println("  <input type=\"text\" name=\"password\">");
//		writer.println("  <br>");
//		writer.println("  Repeat password:<br>");
//		writer.println("  <input type=\"text\" name=\"passwortRepeat\">");
//		writer.println("  <br><br>");
//		writer.println("  <input type=\"submit\" value=\"Register\">");
//		writer.println("</form> ");
//		writer.println("</body>");
//		writer.println("</html>");
//		writer.close();
//	}


    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String passwordRepeat = request.getParameter("passwortRepeat");
		
		try {
			attemptRerister(username,password,passwordRepeat);
		} catch (RegistrationException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PrintWriter writer=response.getWriter();
		writer.println("<!DOCTYPE html>");
		writer.println("<html>");
		writer.println("<body>");
		writer.println("Register attempt with parameters : username:" + username 
				+ " password:" + password
				+ " repeated password:" + passwordRepeat);
		writer.println("</body>");
		writer.println("</html>");
		writer.close();
	
	}
    private void attemptRerister(String email,String password,String repeatedPassword) throws RegistrationException, SQLException
    {
    	
    	if(!emailExists(email))
    		throw new RegistrationException("Email does not exist");
    	if(checkEmailAlreadyTaken(email))
    		throw new RegistrationException("This email is already in use");
    	if(!password.equals(repeatedPassword))
    		throw new RegistrationException("Passwords don't match");
    	
    
		registerInDatabase(email,password);

    }
   
    public static void main(String[] args) throws SQLException {
		Register reg=new Register();
//		String user="user";
//		String password="pass";
//		System.out.println("insert into users('user_name','user_pass') values('"
//    			+ user+"','"
//   			+ password +"');");
//		System.out.println
//    	("insert into user_roles('user_name','role_name') values('"
//    			+ user+"','"
//    			+ "user" +"');");
		//reg.registerInDatabase(null, null);
	}
	
    private void registerInDatabase(String user,String password) throws SQLException
    {
    	Connection connection=DriverManager.getConnection("jdbc:mysql://localhost/project",
    			"root",
    			"TooMuchSwag69");
    	
    	Statement statement=connection.createStatement();
    	//('user_name','user_pass')
    	statement.executeUpdate
    	("insert into users values('"
    			+ user+"','"
    			+ password +"');");
    	//('user_name','role_name')
    	statement.executeUpdate
    	("insert into user_roles values('"
    			+ user+"','"
    			+ "user" +"');");
    	
    	connection.close();
    	
    }
    
    
    
    private boolean emailExists(String email)
    {
    	boolean valid = EmailValidator.getInstance().isValid(email);
    	return valid;
    }
    
    private boolean checkEmailAlreadyTaken(String email)
    {
    	return false;
    }
    
    



}
