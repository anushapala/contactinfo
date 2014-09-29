
var contactList = {};
$(document).ready(function(){
	  
	  getContactsByUser(); 
});

$('#upload').hide();
$('#userimage').hover(function(){
	$('#upload').show();
	}, function(){$('#upload').hide();
	});


$('#upload').click(function(){
	$('#inputupload').trigger('click');	
});

$('#inputupload').on('change',function(){
	
	var input=$('#inputupload').val();
	console.log(input);
	if(input==null)
		{
			return;
		}
	else
		{
			processUploadImageRequest();
		}
});

function processUploadImageRequest(){


	$.ajaxFileUpload
	(
    {
        url:'uploadImageUrl', 
        secureuri:false,
        fileElementId:'inputupload',
        dataType: 'json',
        success: function (data, status)
        {
            if(typeof(data.error) != 'undefined')
            {
                if(data.error != '')
                {
                    alert(data.error);
                }else
                {
                    alert(data.msg);
                }
            }
        },
        error: function (data, status, e)
        {
            alert(e);
        }
    }
)

return false;
}


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
		   console.log(response); 
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

	
	$("#searchString").keyup(function(event){
	     var filter = $("#searchString").val();
	     if(filter!=""){
	      $("#contactlist").find("a").hide();
	      filter = filter.toLowerCase();
	      $.each(contactList,function(index,contact){
	       if(contact.contact_firstname.toLowerCase().indexOf(filter)!=-1 ||
	        contact.contact_lastname.toLowerCase().indexOf(filter)!=-1 ||
	        contact.contact_email_id.toLowerCase().indexOf(filter)!=-1 ||
	        contact.contact_mobile_no.indexOf(filter)!=-1){
	        $("#"+contact.contactKey).show();
	       }
	      });
	     }else{
	      $("#contactlist").find("a").show();
	     }
	 });

	
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
		//contactList.sort();
	 
 }
	
	
	
	window.fbAsyncInit = function() {
	    FB.init({
	      appId      : '281799258677339', // Set YOUR APP ID
	      status     : false, // check login status
	      cookie     : true, // enable cookies to allow the server to access the session
	      xfbml      : true  // parse XFBML
	    });
	    
	    (function(d){
	        var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
	        if (d.getElementById(id)) {return;}
	        js = d.createElement('script'); js.id = id; js.async = true;
	        js.src = "//connect.facebook.net/en_US/all.js";
	        ref.parentNode.insertBefore(js, ref);
	      }(document));

	    FB.Event.subscribe('auth.authResponseChange', function(response) 
		    {
		     if (response.status === 'connected') 
		    {
		    	 
		    	 getUserInfo();
		        //document.getElementById("message").innerHTML +=  "<br>Connected to Facebook";
		        //SUCCESS
		 
		    }    
		    else if (response.status === 'not_authorized') 
		    {
		        //document.getElementById("message").innerHTML +=  "<br>Failed to Connect";
		 		alert('log in to app');
		        //FAILED
		    } else 
		    {
		        //document.getElementById("message").innerHTML +=  "<br>Logged Out";
		 		alert('log in to faceboook');
		        //UNKNOWN ERROR
		    }
		    }); 
		 
		    };
		 
		    function Login()
		    {
		        FB.login(function(response) {
		           if (response.authResponse) 
		           {
		        	   console.log(response);
		        	} else 
		            {
		             console.log('User cancelled login or did not fully authorize.');
		           	}
		         },{scope: 'public_profile,email,user_photos'});

		    } 
			function getUserInfo() {
				
				
	    			FB.api('/me', function(response) {
							console.log(response); 
							var Firstname=response.first_name;
							var Lastname=response.last_name;
							var username=response.name;
							var email=response.email;
							FB.api('/me/picture?type=normal', function(response){	 
								console.log("in")
							      var fbprofile=response.data.url;
							      //document.getElementById("userimage").innerHTML+=str;
							
							//var fbprofile=response.data.url;			
							console.log("its here")
							var userInfo={
									"firstname":Firstname,
									"lastname":Lastname,
									"username":username,
									"email_id":email,
									"profileImage":fbprofile
									};
							console.log(userInfo);
							$.ajax({
								
								type:'POST',
								url:'/authentication/facebook.do',
								data:{userDetails : JSON.stringify(userInfo)},
								success : function(response)
								{
											console.log("success");	
											if(response=="success")
												{
													var redirect ="/jsp/userProfile.jsp";
													
													window.location.href=redirect;							 
												    
												}	
								},
								
								
								error: function()
								{
									console.log("error rendering page");
								}
							});
							
							});		
	});
	    
	}


	
	
	