package com.contact.info;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class ServiceHelper {

	
	public static User saveGoogleUserInfo(String profileInfo){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ObjectMapper mapper = new ObjectMapper();
		
		User user = null;
		
		try{
			
				HashMap<String,Object> profileMap = mapper.readValue(profileInfo, new TypeReference<HashMap<String,Object>>() {
				});
			
				System.out.println("this is the google profile" +profileMap);
			
			String email		= (String) profileMap.get("email");
				   user 		= getUserByEmail(email);
				   
			if(user == null){
				String firstname 	= (String) profileMap.get("given_name");
				String lastname 	= (String) profileMap.get("family_name");
				String username		= (String) profileMap.get("name");
				String gender		= (String) profileMap.get("gender");
				String profileImage	= (String) profileMap.get("picture");
					user = new User();
					
					user.setUserId(UUID.randomUUID().toString());
					user.setEmail_id(email);
					user.setFirstname(firstname);
					user.setLastname(lastname);
					user.setUsername(username);
					user.setGender(gender);
					user.setProfileImage(profileImage);
					pm.makePersistent(user);
				
			}
				
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			pm.close();
		}
		
		return user;
		
	}
	
	
	public static User saveFbUserInfo(String fbProfile)
	{
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ObjectMapper mapper = new ObjectMapper();
		
		User user = null;
		
		try
		{
			HashMap<String,Object> profileMap = mapper.readValue(fbProfile, new TypeReference<HashMap<String,Object>>() {
			});
			
			System.out.println("this is the converted map :"+profileMap);
			
			String email		= (String) profileMap.get("email_id");
			   user 		= getUserByEmail(email);
			   System.out.println("the user after checking mail"+user);
			   if(user == null){
					String firstname 	= (String) profileMap.get("firstname");
					String lastname 	= (String) profileMap.get("lastname");
					String username 	= (String) profileMap.get("username");
					String gender		= (String) profileMap.get("gender");
					String profileImage	= (String) profileMap.get("profileImage");
					
					user = new User();
					
					user.setUserId(UUID.randomUUID().toString());
					user.setEmail_id(email);
					user.setFirstname(firstname);
					user.setLastname(lastname);
					user.setGender(gender);
					user.setUsername(username);
					user.setProfileImage(profileImage);
					
					pm.makePersistent(user);
			
		}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			pm.close();
		}
		return user;
	} 
	
	public static User getUserByEmail(String email){
		PersistenceManager pm= PMF.get().getPersistenceManager();
		User user 	= null;
		if(email!=null){
			email = email.trim();
			System.out.println(email);
		}
		
		try{
			
			System.out.println("entered the try in the helper class to execute email query");
			
			Query query = pm.newQuery(User.class);
			query.declareParameters("String googleemail");
			query.setFilter("email_id==googleemail");
			
			List<User> userList = (List<User>)query.execute(email);
			System.out.println("size of hte list"+userList.size());

			if(userList.size()>0){
				user = userList.get(0);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			pm.close();
		}
		
		return user;
	}
	
	
	
}
