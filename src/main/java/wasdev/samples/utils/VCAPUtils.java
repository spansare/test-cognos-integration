package wasdev.samples.utils;

import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VCAPUtils {

	String host;
	String rest_url;
	String namespace;
	String username;
	String password;
	
	
	public VCAPUtils() {
		String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
		
		if(VCAP_SERVICES != null) {
			try {
				JSONObject jsonObj = (JSONObject) JSONObject.stringToValue(VCAP_SERVICES);
				
				System.out.println("jsonObj : " + jsonObj);
				
				String key = null;
				Iterator keys = jsonObj.keys();
				while (keys.hasNext()) {
					key = (String) keys.next();
					System.out.println("key : " + key);
					if (key.contains("user-provided"))
						break;
				}
				
				
				JSONArray list = (JSONArray) jsonObj.get(key);
				JSONObject cognos = (JSONObject) list.get(0);
				
				System.out.println("cognos : " + cognos);
				
				JSONObject creds = (JSONObject) cognos.get("credentials");
				
				System.out.println("creds : " + creds);
				
				host = creds.getString("host");
				rest_url = creds.getString("rest_url");
				namespace = creds.getString("namespace");
				username = creds.getString("username");
				password = creds.getString("password");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getRest_url() {
		return rest_url;
	}
	public void setRest_url(String rest_url) {
		this.rest_url = rest_url;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
