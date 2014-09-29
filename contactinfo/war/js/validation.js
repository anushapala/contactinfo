$(document).ready(function(){
	$(".ok").hide();
	$(".error").hide();

});

$('#InputEmail').change(function(){
	var inputEmail=$("#InputEmail").val();
	console.log(inputEmail);
	
	var inputEmail=$.trim(inputEmail);
	 var validStatus=validateEmail(inputEmail);
	 console.log(validStatus);	
	if(validStatus)
	{
		$.ajax({
			
			type:'POST',
			url:'/authentication/validation.do',
			data:"signup_email="+inputEmail,
			success:function(response){
				console.log(response);
				
				response = JSON.parse(response);
				console.log(response.success);
				
				if(response.success){
						console.log("email already exists");
						$("#emailok").hide();
						$("#emailerror").show();
						$("#emailCheck").removeClass("has-success");
						$("#emailCheck").addClass("has-error");	
						}
				else{
					$("#emailerror").hide();
					$("#emailok").show();
					$("#emailCheck").removeClass("has-error");
					$("#emailCheck").addClass("has-success");
				}
			},
			error:function(){
				
				alert("error");
			}			
		});	
	}

$('#InputEmail').keyup(function(){
		
		
		if($('#emailCheck').hasClass('has-error')){
				$('#emailCheck').removeClass('has-error');
			}
		else{
				$('#emailCheck').removeClass('has-success');
			}
	});
	
});
	
function validateEmail(inputEmail)
{	
	console.log("in validate function  email");
	var filter = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
	if (filter.test(inputEmail)){
		return true;
		}
	else
		return false;
		console.log("error");
}


$(function (){
	$("#passwordinfo").popover({trigger: 'hover'});
	      });


$('#InputPassword').change(function(){
	
	var inputPassword=$('#InputPassword').val();
	console.log(inputPassword);
	
	var validateStatus= validatePassword(inputPassword);
	console.log(validateStatus);
	if(validateStatus)
		{
			$('#passerror').hide();
			$('#passok').show();
			$('#passwordCheck').removeClass('has-error');
			$('#passwordCheck').addClass('has-success');
			
			
		}
	else
		{
			console.log('false executing');
			
			$("#passok").hide();
			$("#passerror").show();
			$("#passwordCheck").removeClass('has-success');
			$("#passwordCheck").addClass("has-error");	
		}
	$('#InputPassword').keyup(function(){
		
		if($('#passwordCheck').hasClass('has-error')){
				$('#passwordCheck').removeClass('has-error');
			}
		else{
				$('#passwordCheck').removeClass('has-success');
			}
	});
	
});


function validatePassword(inputPassword)
{
	console.log("in password validation");
	var passFilter=/^.*(?=.{8,})(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&]).*$/;
	if(passFilter.test(inputPassword)){
		return true;
		}
	else
		return false;
		console.log('error in passvalidation');
}