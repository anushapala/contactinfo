
var contactList = {};
$(document).ready(function(){
	  
	  getContactsByUser();
	  
});
	 
	 function getContactsByUser(){
	  
	  $.ajax({
	   type:'POST',
	   url:'/getContacts.do',
	   success:function(response){
		   console.log(response);
		   if(response == null || response == ""){
			   return;
		   }
		   
		   response = JSON.parse(response);
		   console.log(response)
		    for(index in response){
		    	
		     var contact = response[index];
		     
		     contactList[contact.contactKey]=contact;
		     
		     createContact(contact);
		     
		    }
	   },error:function(response){
	    alert("error ");
	   }
	   
	  });
	  
	 }
	
	function addContact() 
	{
		
		var cfname=$("#contactfirstname").val();
		var clname=$("#contactlastname").val();
		var cemail=$("#contactemail").val();
		var cmobile=$("#contactmobile").val();
		
		//jquery ajax implementation
		$.ajax({
			
			type:'POST',
			url:'/addcontact.do',
			data:"contact_firstname="+cfname+"&contact_lastname="+clname+"&contact_email="+cemail+"&contact_mobile_no="+cmobile,
			
			success: function(response){
					console.log(response);
					console.log("response printed");
						
					if(response == null || response == ""){
						   return;
					   }
					response = JSON.parse(response);
					console.log(response);
					console.log("response is parsed");
					if(response.success){
						
						var contact = response.contact
						$("#addcontact").modal('hide');
						
						contactList[contact.contactKey]=contact;
						
						createContact(contact);
						 
					}
					
					},
			error:function(){
						alert("something worng!");
					}
			});
		
		document.getElementById("addform").reset();
	}
	
	
	function updateContactPopUp(contactKey){
		 
		 var contact = contactList[contactKey];
		 
		 $("#updatefirstname").val(contact.contact_firstname);
		 $("#updatelastname").val(contact.contact_lastname);
		 $("#updateemail").val(contact.contact_email_id);
		 $("#updatemobile").val(contact.contact_mobile_no);
		 $("#update_contact").attr('onClick','updateContact("'+contact.contactKey+'")');
		 
		 $("#updatecontact").modal('show');
	 }
	
	
	function updateContact(contactKey)
	{
		var ufname=$("#updatefirstname").val();
		var ulname=$("#updatelastname").val();
		var uemail=$("#updateemail").val();
		var umobile=$("#updatemobile").val();
		
		$.ajax({
			
			type:'POST',
			url:'/updatecontact.do',
			data:"update_firstname="+ufname+"&update_lastname="+ulname+"&update_email="+uemail+"&update_mobile_no="+umobile+"&contactKey="+contactKey,
			
			success: function(response){
						console.log(response);
						response = JSON.parse(response);
						
						if(response.success){
							 $("#updatecontact").modal('hide');
							 
							 var contact = response.contact;
							 contactList[contactKey]=contact;
							 
							 $("#"+contactKey).find("h4").html(contact.contact_firstname+" "+contact.contact_lastname);
							 $("#"+contactKey).find("p").html(contact.contact_email_id+"</br>"+contact.contact_mobile_no);
							 
						}
						
			},error : function(){
				alert("something wrong in updating!");
			}
		});
		
	}	
	
	function searchContact()
	{
		
		var inputString=$("#searchString").val();
		
		$.ajax({
			
			type:'POST',
			url:'/search.do',
			data:"search_string="+inputString,
			success: function(response)
			{
				console.log(response);
				if(response == null || response == ""){
					   return;
				   }
				 response = JSON.parse(response);
				    for(index in response){
				    	
				     var contact = response[index];
				     
				     contactList[contact.contactKey]=contact;
				     
				     displayContact(contact);
				     
				    }
				
			},error:function(response)
			{
				alert(response.message);
			}
			
		});
	}
	
	function deleteContact(contactKey)
	{
		
		$.ajax({
			type:'POST',
			url:'/deletecontact.do',
			data:"contactKey="+contactKey,
			success:function(response){
				console.log(response);
				if(response=="deleted successfully")
					{
					    delete contactList[contactKey];
						$("#"+contactKey).remove();
					}
					
				},
			error:function(){
				alert("something wrong in deleting!");
			}
		});
	}
	
	
	function google()
	{
		
		
		
	}

	
	function createContact(contact){
		
		var contact_a = document.createElement("a");
		contact_a.setAttribute('class',"list-group-item");
		contact_a.setAttribute('id',contact.contactKey);
		
		var h4   = document.createElement("h4");
		h4.setAttribute("class","list-group-item-heading");
		h4.innerHTML = contact.contact_firstname+" "+contact.contact_lastname;
		contact_a.appendChild(h4);

		var p = document.createElement("p");
		p.setAttribute("class","list-group-item-text");
		p.innerHTML = contact.contact_email_id+"</br>"+contact.contact_mobile_no;
		contact_a.appendChild(p);
	    
		
		var contact_settings = document.createElement("div");
		contact_settings.setAttribute('class','contact_settings');
		
		
		var del_button=document.createElement("button");
		del_button.setAttribute('class',"btn btn-default btn-sm");
		del_button.setAttribute('onclick',"deleteContact('"+contact.contactKey+"')");
	     
		var span=document.createElement("span");
		span.setAttribute('class',"glyphicon glyphicon-trash");
		del_button.appendChild(span);
		contact_settings.appendChild(del_button);
		
		var edit_button=document.createElement("button");
		edit_button.setAttribute('class',"btn btn-default btn-sm");
		edit_button.setAttribute('onclick',"updateContactPopUp('"+contact.contactKey+"')");
	     
		var span=document.createElement("span");
		span.setAttribute('class',"glyphicon glyphicon-edit");
		edit_button.appendChild(span);
		contact_settings.appendChild(edit_button);
		
		contact_a.appendChild(contact_settings); 
		
		$("#contactlist").append(contact_a);
	 
 }
 
	function displayContact(contact)
	{
		
		var contact_a = document.createElement("a");
		contact_a.setAttribute('class',"list-group-item");
		contact_a.setAttribute('id',contact.contactKey);
		
		var h4   = document.createElement("h4");
		h4.setAttribute("class","list-group-item-heading");
		h4.innerHTML = contact.contact_firstname+" "+contact.contact_lastname;
		contact_a.appendChild(h4);

		var p = document.createElement("p");
		p.setAttribute("class","list-group-item-text");
		p.innerHTML = contact.contact_email_id+"</br>"+contact.contact_mobile_no;
		contact_a.appendChild(p);
		
	    $("#displayList").append(contact_a);
	    $("#searchString").keyup(function()
	    		{
	    			$("#displayList").html("");
	    			//$("#displayList").load();
	    			//document.location.reload();
	    		});
	  
	    
	}
	
	
	
	

