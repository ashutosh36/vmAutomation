package hpe.csa.helperUtils;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import hpe.csa.model.Users;
import hpe.csa.model.VM;
import hpe.csa.model.Dao.UsersDaoImpl;

public class Helper {
	
	private static Logger logger=Logger.getLogger(Helper.class.getName());
	
	public Timestamp getSqlDateFromToday(int daysFromNow) {
	
		int secsToadd=daysFromNow*24*60*60;
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.SECOND, secsToadd);
		Timestamp time=new Timestamp(cal.getTimeInMillis());

		return time;
	}
	
	public String allVMsToString(List<VM> vms) {
		String res="";
		for(VM v:vms) {
			res+=v.toString()+"\n";
		}
		return res;
	}
	/*
	public static boolean authenticateHeader(String header) throws RuntimeException{
		
		System.out.println("User header :"+header);
		int pos=header.indexOf(" ");
		String userHeaderEncoded=header.substring(pos+1,header.length());
		
		//String userHeader=new String(new Base64.Decoder().decode(userHeaderEncoded));
		String userHeader=new String(Base64.getDecoder().decode(userHeaderEncoded));
		System.out.println("User header decoded:"+header.substring(0,pos)+"@@"+userHeader);
		
		String inpUser=userHeader.split(":")[0];
		String inpPass=userHeader.split(":")[1];
		try {
			Users u=new UsersDaoImpl().getUserByName(inpUser);
			if(u==null) {
				throw new RuntimeException("User "+inpUser+" not found");
			}
			System.out.println("Id:"+u.getId()+" username: "+u.getUsername()+" password: "+u.getPassword());
			if(u.getPassword().compareToIgnoreCase(inpPass)!=0) {
				throw new RuntimeException("Incorrect Password");
			}
		}catch(Exception e) {
			throw new RuntimeException("Authentication failed for user '"+inpUser+"'. "+e.getMessage());
		}
		
		
		return true;
	}
	*/
	public static Users getHeaderUser(String header) {
		int pos=header.indexOf(" ");
		//String userHeaderEncoded=header.substring(pos+1);
		
		//String userHeader=new String(new Base64.Decoder().decode(userHeaderEncoded));
		String userHeader=new String(Base64.getDecoder().decode(header.substring(pos+1)));
		System.out.println("User header decoded:"+header.substring(0,pos)+"@@"+userHeader);
		
		String inpUser=userHeader.split(":")[0];
		String inpPass=userHeader.split(":")[1];
		Users u=new Users();
		try {
			u=new UsersDaoImpl().getUserByName(inpUser);
			if(u==null) {
				throw new RuntimeException("User "+inpUser+" not found");
			}
			System.out.println("Id:"+u.getId()+" username: "+u.getUsername()+" password: "+u.getPassword());
		}catch(Exception e) {
			throw new RuntimeException("Authentication failed for user '"+inpUser+"'. "+e.getMessage());
		}
		return u;
	}
	
	
}
