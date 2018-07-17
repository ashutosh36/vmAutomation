package hpe.csa.helperUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServiceProperties {
	
	private Properties properties;
	private static final String PROPERTY_FILE_PATH="src\\main\\resources\\config.properties";
	
	public Properties getProperties() {
		if(properties==null) {
			loadProperties();
		}
		return properties;
	}
	
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	private void loadProperties() {
		Properties prop=new Properties();
		InputStream ins = null;
		try {
			ins=new FileInputStream(PROPERTY_FILE_PATH);
			prop.load(ins);
			ins.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setProperties(prop);
	}
	
	public String getPropertyValue(Properties prop,String key) {
		String res=prop.getProperty(key);
		if(res!=null&&res.length()>0) {
			return res;
		}
		return null;
	}

}
