package tests;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import backend.Register;


class TestRegister {

	static Register register;
    private static MockHttpServletRequest request;
    private static MockHttpServletResponse response;
	
	@BeforeAll
	public static void setUpBefore() throws Exception {
		

	}

	@Test
	void TestRegisterPost() throws ServletException, IOException {
		register=new Register();
		request=new MockHttpServletRequest();
		response= new MockHttpServletResponse();
		
		String username = "user";
		String password = "pass";
		String passwortRepeat = "pass1";
		

		request.addParameter("username", username);
		request.addParameter("password", password);
		request.addParameter("passwortRepeat", passwortRepeat);
		
		
		register.doPost(request, response);
		
		System.out.println(response.getContentAsString());
		
	}

}
