package com.contact.info;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;




@Controller
@RequestMapping ("/authentication")
public class Authentication {

		private static String clientId="345290668338-mdvpnkpd6hgbv9q0r0lqr9180nu20ss4.apps.googleusercontent.com";
		private static String clientSecret="7Mb9dH4OHhjWBPZeJUV5LSgO";
		private static String redirectUrl="http://localhost:8888/authentication/googleCallback.do";
		
		@RequestMapping(value="/google",method=RequestMethod.GET)
		public void google(HttpServletRequest request,HttpServletResponse response){
			try{
				
				String requestUrl="https://accounts.google.com/o/oauth2/auth?response_type=code"
						+ "&client_id="+clientId+""
						+ "&redirect_uri="+redirectUrl+""
						+ "&scope=profile email"
						+ "&access_type=online"
						+ "&approval_prompt=auto";
				
				response.sendRedirect(requestUrl);
				
			}
			catch (Exception e){
				e.printStackTrace();
			}
			
			
		}
		
		@RequestMapping(value="/googleCallback",method=RequestMethod.GET)
		public void googleCallback(HttpServletRequest request,HttpServletResponse response)
		{
			String code = request.getParameter("code");
			String error = request.getParameter("error");
			
			try{
				if(code!=null){
					
					System.out.println("code not null");
					String requestUrl="https://accounts.google.com/o/oauth2/auth";
					String params="code="+code
							+ "&client_id="+clientId
							+ "&redirect_uri="+redirectUrl
							+ "&client_secret="+clientSecret
							+"&grant_type=authorization_code";
					
					String responseString=sendHttpRequest(requestUrl,"POST",params,null);
					System.out.println(responseString);
					
					
				}else{
					
				}
				
				System.out.println("this is from the google callback");
				System.out.println(request.getParameter("code"));
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			
		}
		
		private String sendHttpRequest(String url,String methodName, String params,String contentType)
		{
			StringBuffer response=new StringBuffer();
			HttpURLConnection conn =null;
			try
			{
				URL urlobj= new URL(url);
				conn = (HttpURLConnection) urlobj.openConnection();
				conn.setRequestMethod(methodName);
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setConnectTimeout(60000);
				
				if(contentType != null && !contentType.isEmpty()){
				    conn.setRequestProperty("Content-Type",contentType); 	
				   }
				if(params !=null && !params.isEmpty())
				{
					OutputStreamWriter output=new OutputStreamWriter(conn.getOutputStream());
					output.write(params);
					output.flush();
				}
				
				
				int status=conn.getResponseCode();
				System.out.println(status);
				if(status == HttpURLConnection.HTTP_OK){
					
					System.out.println("inside the if");
					BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					String responseString = "";
				    while ((responseString = reader.readLine()) != null){
				    	response.append(responseString);
				    	System.out.println(responseString);
				    }
				  }
				
				else
				{
					System.out.println("the else is running");
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				conn.disconnect();
			}
		
		return response.toString();
		}
}
