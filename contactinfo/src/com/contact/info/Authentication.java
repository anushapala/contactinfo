package com.contact.info;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.appengine.labs.repackaged.org.json.JSONObject;


@Controller
@RequestMapping ("/authentication")
public class Authentication {

		private static String clientId="345290668338-mdvpnkpd6hgbv9q0r0lqr9180nu20ss4.apps.googleusercontent.com";
		private static String clientSecret="7Mb9dH4OHhjWBPZeJUV5LSgO";
		//private static String redirectUrl="http://localhost:8888/authentication/googleCallback.do";
		private static String redirectUrl="http://application-2014.appspot.com/authentication/googleCallback.do";
		
		@RequestMapping(value="/google",method=RequestMethod.GET)
		public void google(HttpServletRequest request,HttpServletResponse response){
			try{
				
				String requestUrl="https://accounts.google.com/o/oauth2/auth?response_type=code"
						+ "&client_id="+clientId+""
						+ "&redirect_uri="+redirectUrl+""
						+ "&scope=profile email https://www.googleapis.com/auth/plus.me"
						+ "&access_type=online"
						+ "&approval_prompt=force";
				
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
		   
		   ObjectMapper mapper = new ObjectMapper();
		   
		   HttpSession session=request.getSession();
		   try{
		    if(code!=null){
		     
		     System.out.println("code:"+code);
		     String requestUrl="https://accounts.google.com/o/oauth2/token";
		     String params="code="+code
		       + "&client_id="+clientId
		       + "&redirect_uri="+redirectUrl
		       + "&client_secret="+clientSecret
		       +"&grant_type=authorization_code";
		     
		     String responseString=sendHttpRequest(requestUrl,"POST",params);
		    
		     System.out.println(responseString);
		     
		     if(responseString!=null && responseString.indexOf("access_token")!=-1){
		      HashMap<String,Object> accessTokenMap = mapper.readValue(responseString,new TypeReference<HashMap<String,Object>>() {
		      });
		      
		      String accessToken = (String) accessTokenMap.get("access_token");
		      System.out.println(accessToken);
		      
		      
		      String profileInfoUrl = "https://www.googleapis.com/oauth2/v1/userinfo?access_token="+accessToken;
		      String profileJson    = sendHttpRequest(profileInfoUrl, "GET", null);
		      
		      System.out.println("this is my profile json:"+profileJson);
		      
		      User currentUser = ServiceHelper.saveGoogleUserInfo(profileJson); 
		     
		      	session.setAttribute("firstname", currentUser.getFirstname());
		  		session.setAttribute("lastname", currentUser.getLastname());
		  		session.setAttribute("username", currentUser.getUsername());
		  		session.setAttribute("emailid", currentUser.getEmail_id());
		  		session.setAttribute("profileImage", currentUser.getProfileImage());
		  		session.setAttribute("userId", currentUser.getUserId());
		  		
		  		response.sendRedirect("/jsp/userProfile.jsp");
		     }
		     
		    }
		    
		    
		   }
		   catch(Exception e){
		    e.printStackTrace();
		   }
		  }
		private String sendHttpRequest(String url,String methodName, String params)
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
				    	
				    }
				  }
				
				else
				{
					System.out.println("something wrong with status");
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
		
		@RequestMapping(value="/facebook",method=RequestMethod.POST)
		public @ResponseBody  String facebook(@RequestParam(value = "userDetails",required = true)String userDetails ,HttpServletRequest request,HttpServletResponse response) 
		{
			 HttpSession session=request.getSession();
			
			try
			{	
				System.out.println("coming here check :"+userDetails);
				
				if(userDetails != null)
				{ 
					User currentUser=ServiceHelper.saveFbUserInfo(userDetails);
					 
					 System.out.println("iin  my authentication :" +currentUser.getProfileImage());
					 
					 	session.setAttribute("firstname", currentUser.getFirstname());
				  		session.setAttribute("lastname", currentUser.getLastname());
				  		session.setAttribute("emailid", currentUser.getEmail_id());
				  		session.setAttribute("userId", currentUser.getUserId());
				  		session.setAttribute("username", currentUser.getUsername());
				  		session.setAttribute("profileImage", currentUser.getProfileImage());
				  		System.out.println("session is set");
				  		//response.sendRedirect("/jsp/userProfile.jsp");			
				}
			}
			
			catch (Exception e)
			{
				e.printStackTrace();
			}
		
			System.out.println("before success");
			
			return "success";
		}
}





	