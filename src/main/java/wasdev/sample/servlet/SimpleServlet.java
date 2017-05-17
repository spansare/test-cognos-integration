package wasdev.sample.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import wasdev.samples.utils.VCAPUtils;

/**
 * Servlet implementation class SimpleServlet
 */
@WebServlet("/SimpleServlet")
public class SimpleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        //response.getWriter().print("Hello World!");
        
        VCAPUtils vcap = new VCAPUtils();
        String serverUrl = vcap.getRest_url();
        String namespace = vcap.getNamespace();
        String username = vcap.getUsername();
        String password = vcap.getPassword();
        
        HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(serverUrl + "/rds/auth/logon");
		StringEntity input = new StringEntity("xmlData=<auth:credentials xmlns:auth='http://developer.cognos.com/schemas/ccs/auth/types/1'><auth:credentialElements><auth:name>CAMNamespace</auth:name><auth:value><auth:actualValue>" + namespace +"</auth:actualValue></auth:value></auth:credentialElements><auth:credentialElements><auth:name>CAMUsername</auth:name><auth:value><auth:actualValue>" + username + "</auth:actualValue></auth:value></auth:credentialElements><auth:credentialElements><auth:name>CAMPassword</auth:name><auth:value><auth:actualValue>" + password + "</auth:actualValue></auth:value></auth:credentialElements></auth:credentials>");
		post.addHeader("Content-type", "application/x-www-form-urlencoded");
		//post.addHeader("Content-length", "576");
		post.addHeader("Connection", "close");
		post.setEntity(input);
		
		HttpResponse response_new = client.execute(post);
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(response_new.getEntity().getContent()));
		
		String line = "";
		
		while ((line = rd.readLine()) != null) {
		
			System.out.println(line);
		}
		
		System.out.println("\n\n\n");

		
		HttpGet request1 = new HttpGet(serverUrl + "/rds/pagedReportData/report/i​A57559C8E344428B82B257A7BBDD58B0?fmt=HTML&version=LATEST&v=3");
		HttpResponse response1 = client.execute(request1);
		BufferedReader rd1 = new BufferedReader (new InputStreamReader(response1.getEntity().getContent()));
		System.out.println(response1.toString());
		StringBuilder str = new StringBuilder();
		line = "";
		while ((line = rd1.readLine()) != null) {
			System.out.println(line);
			str.append(line);
		}
		response.getWriter().print(str);
		
    }

}
