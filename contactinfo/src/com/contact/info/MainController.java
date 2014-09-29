package com.contact.info;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;



@Controller
public class MainController {
	
	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public  String signup(HttpServletRequest request,HttpServletResponse response)
	{
		
		System.out.println("in the signup method");
		
		String firstname=request.getParameter("signup_firstname");
		String lastname=request.getParameter("signup_lastname");
		String username=request.getParameter("signup_username");
		String password=request.getParameter("signup_password");
		String emailid=request.getParameter("signup_email");
		
		password=ServiceHelper.passwordEncryption(password);
		
		System.out.println("encrypted password is:"+password);

		PersistenceManager pm= PMF.get().getPersistenceManager();
		
		try
		{
			User user=new User();
			
			user.setFirstname(firstname);
			user.setLastname(lastname);
			user.setUsername(username);
			user.setPassword(password);
			user.setEmail_id(emailid);
			user.setUserId(UUID.randomUUID().toString());
			pm.makePersistent(user);
			
		}	
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			pm.close();
		}
		
		return "contactMain";
		
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public void login(HttpServletRequest request,HttpServletResponse response)
	{
		
			System.out.println("in my login method");
			
			String email=request.getParameter("login_email");
			String password=request.getParameter("login_password");
			
			PersistenceManager pm=PMF.get().getPersistenceManager();
			
			HttpSession session=request.getSession();
			
			email=email.trim();
			password=password.trim();
			
			password=ServiceHelper.passwordEncryption(password);
			System.out.println(password);
			
		
			Query query=pm.newQuery(User.class);
			query.declareParameters("String loginemail,String loginpass");
			query.setFilter("email_id==loginemail&& password==loginpass");
		
			System.out.println("password successfully matches");
			
			try
			{
				List<User> results=(List<User>)query.execute(email,password);
				System.out.println(results);
				
				if (!results.isEmpty()) 
				  {
					  	for(User user:results)
					  	{
					  		
					  		session.setAttribute("firstname", user.getFirstname());
					  		session.setAttribute("lastname", user.getLastname());
					  		session.setAttribute("username", user.getUsername());
					  		session.setAttribute("emailid", user.getEmail_id());
					  		
					  		session.setAttribute("userId", user.getUserId());
					  	}
					
					  	response.sendRedirect("/jsp/userProfile.jsp");
				  } else
				  {
					  
					  System.out.println("else is executing");
					 response.setContentType("text/html");
				    response.getWriter().println("<html><head><body><p>login failed! </p></body></head></html>");
				  }
				  
				} 
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			finally {
				  query.closeAll();
				  }
		
	}
	
	@RequestMapping(value="/getContacts",method=RequestMethod.POST)
	public @ResponseBody String getContacts(HttpServletRequest request,HttpServletResponse response)
	{
		
		System.out.println("in my get contacts method");
		
		String userId =  (String) request.getSession().getAttribute("userId");
		System.out.println(userId);
		
		
		PersistenceManager pm=PMF.get().getPersistenceManager();
		
		Query query=pm.newQuery(Contact.class);
		query.setFilter("userId=='"+userId+"'");
		
		String json = "";
		try
		{
			List<Contact> results=(List<Contact>)query.execute();
			System.out.println(results.size());
			
	
			if (!results.isEmpty())
			{
				//response.getWriter().write(json);
				//response.toString();
				Gson gson=new Gson();
				json=gson.toJson(results);
				
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			pm.close();
		}
	
		return json;
		
	}
	
	@RequestMapping(value="/addcontact",method=RequestMethod.POST)
	public @ResponseBody String addContact(HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("in my addcontact method");
		
		String contactFirstname=request.getParameter("contact_firstname");
		String contactLastname=request.getParameter("contact_lastname");
		String contactEmail=request.getParameter("contact_email");
		String contactMobile=request.getParameter("contact_mobile_no");
	
	
		String userId =  (String) request.getSession().getAttribute("userId");
		
	
		PersistenceManager pm=PMF.get().getPersistenceManager();
		String contactJson="";
		try
		{
			
			Contact contact=new Contact();
		
			contact.setContact_firstname(contactFirstname);
			contact.setContact_lastname(contactLastname);
			contact.setContact_email_id(contactEmail);
			contact.setContact_mobile_no(contactMobile);
			contact.setUserId(userId);
			contact.setContactKey(UUID.randomUUID().toString());

			pm.makePersistent(contact);
			
			HashMap<String,Object> responseMap = new HashMap<String,Object>();
			responseMap.put("success", true);
			responseMap.put("contact", contact);
			
			Gson gson = new Gson();
			contactJson = gson.toJson(responseMap);
			
			//response.getWriter().write(contactJson);
			}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			pm.close();
		}
		return contactJson;
	}
		
	@RequestMapping(value="/updatecontact",method=RequestMethod.POST)
	public @ResponseBody String updateContact(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("in the update contact method");
		
		String updateFirstname=request.getParameter("update_firstname");
		String updateLastname=request.getParameter("update_lastname");
		String updateEmail=request.getParameter("update_email");
		String updateMobile=request.getParameter("update_mobile_no");
		
		String contactKey=(String)request.getParameter("contactKey");
		
		String contactJson="";
		PersistenceManager pm=PMF.get().getPersistenceManager();
		HashMap<String,Object> responseMap = new HashMap<String,Object>();
		try
		{
			Contact contact=pm.getObjectById(Contact.class,contactKey);
			
			if(contact!=null)
			{
				contact.setContact_firstname(updateFirstname);
				contact.setContact_lastname(updateLastname);
				contact.setContact_email_id(updateEmail);
				contact.setContact_mobile_no(updateMobile);
				
				pm.makePersistent(contact);
				responseMap.put("success", true);
				responseMap.put("contact", contact);
			}
			else
			{
				responseMap.put("success", false);
				responseMap.put("status", "contactNotFound");
				
			}
			Gson gson = new Gson();
			contactJson = gson.toJson(responseMap);
			
			//response.getWriter().write(contactJson);
		}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				pm.close();
			}
				
		return contactJson;
	}
	
	@RequestMapping(value="/deletecontact",method=RequestMethod.POST)
	public @ResponseBody String deleteContact(HttpServletRequest request, HttpServletResponse response)
	{
		
		
		System.out.println("in my delete method");
		
		PersistenceManager pm=PMF.get().getPersistenceManager();
		
		String contactKey=(String)request.getParameter("contactKey");
		
		System.out.println(contactKey);
		
		try
		{
			Contact contact=pm.getObjectById(Contact.class,contactKey);
			pm.deletePersistent(contact);
			
			response.setContentType("text/html");
			//response.getWriter().write("deleted successfully");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			pm.close();
		}
		
		return "deleted successfully" ;
		
	}
	

	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public  String logout(HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("in my log out method");
		
			try
		
		{
			request.getSession().invalidate();	
			request.setAttribute("userId",null);
		  
			
			//response.sendRedirect("/jsp/contactMain.jsp");
			  
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		 return "contactMain";
		
	}
}



