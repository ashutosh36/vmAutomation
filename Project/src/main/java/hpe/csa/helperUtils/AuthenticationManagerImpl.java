package hpe.csa.helperUtils;

import java.util.Base64;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import hpe.csa.model.Users;
import hpe.csa.model.Dao.UsersDaoImpl;

public class AuthenticationManagerImpl {

	public static boolean validateAuthorizationHeader(String authorizationHeader) throws RuntimeException {

		System.out.println("User authorizationHeader :" + authorizationHeader);
		int pos = authorizationHeader.indexOf(" ");
		String userHeaderEncoded = authorizationHeader.substring(pos + 1, authorizationHeader.length());

		// String userauthorizationHeader=new String(new
		// Base64.Decoder().decode(userHeaderEncoded));
		String userHeader = new String(Base64.getDecoder().decode(userHeaderEncoded));
		System.out.println("User header decoded:" + authorizationHeader.substring(0, pos) + "@@" + userHeader);

		String inpUser = userHeader.split(":")[0];
		String inpPass = userHeader.split(":")[1];
		try {
			Users u = new UsersDaoImpl().getUserByName(inpUser);
			if (u == null) {
				throw new RuntimeException("User " + inpUser + " not found");
			}
			System.out.println("Id:" + u.getId() + " username: " + u.getUsername() + " password: " + u.getPassword());
			if (u.getPassword().compareToIgnoreCase(inpPass) != 0) {
				throw new RuntimeException("Incorrect Password");
			}
		} catch (Exception e) {
			throw new RuntimeException("Authentication failed for user '" + inpUser + "'. " + e.getMessage());
		}

		return true;
	}

	public static Response authorizeUser(String authorizationHeader) {
			if(validateAuthorizationHeader(authorizationHeader)) {
				return Response.status(Status.OK).build();
			}
			return Response.status(Status.FORBIDDEN).build();
	}

}
