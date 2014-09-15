<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Contact Main Page</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/css/contactInfo.css">
</head>
<body background="/images/Contact.jpg" id="contactMain">

<nav class="navbar navbar-inverse" role="navigation">
  	<div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-9">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Contact info</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="nav-div">
          <ul class="nav navbar-nav pull-right">
            <li class="active"><a href="/jsp/contactMain.jsp">Home</a></li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </div>
</nav>

<!-- Nav tabs -->
<div id="authenticate">

	<ul class="nav nav-tabs" role="tablist">
		<li class="active auth"><a href="#signUp" role="tab" data-toggle="tab"><b>Signup</b></a></li>
  		<li class="pull-right auth"><a href="#login" role="tab" data-toggle="tab" ><b>Login</b></a></li>
	</ul>

<!-- Tab panes -->
<div class="tab-content">
  <div class="tab-pane active" id="signUp">
  
  	<div class="modal-body">
        
        <form role="form" action="/signup.do" method="post">
        
        <div class="form-group">
    		<label for="exampleInputEmail1">Email address <span style="color:red;">*</span></label>
    		<input type="email" class="form-control"  id="exampleInputEmail1" placeholder="Enter email" name="signup_email" required>
  		</div>
  		
        <div class="form-group">
    		<label for="exampleInputEmail1">Username</label>
    		<input type="text" class="form-control"  id="exampleInputusername" placeholder="Enter username" name="signup_username" required>
  		</div>
  		
  		<div class="form-group">
    		<label for="exampleInputEmail1">First Name</label>
    		<input type="text" class="form-control"  id="exampleInputfirstname" placeholder="Enter First name" name="signup_firstname" required>
  		</div>
  
  		<div class="form-group">
    		<label for="exampleInputEmail1">Last Name</label>
    		<input type="text" class="form-control"  id="exampleInputlastname" placeholder="Enter Last name" name="signup_lastname" required>
  		</div>
  
  		<div class="form-group">
    		<label for="exampleInputPassword1">Password</label>
    		<input type="password" class="form-control"  id="exampleInputPassword1" placeholder="Password" name="signup_password" required>
  		</div>
  		<button type="submit" class="btn btn-default">Submit</button>
  		
</form>
      </div>
  
  </div>
  
  
  <div class="tab-pane" id="login">
  	
      <div class="modal-body">
        
        <form role="form" action="/login.do" method="post">
        <div class="form-group">
    		<label for="exampleInputEmail1">Email</label>
    		<input type="text" class="form-control"  id="exampleInputemail" placeholder="Enter email" name="login_email" required>
  		</div>
  		
  		<div class="form-group">
    		<label for="exampleInputPassword1">Password</label>
    		<input type="password" class="form-control"  id="exampleInputPassword1" placeholder="Password" name="login_password" required>
  		</div>
  		
  		<button type="submit" class="btn btn-default">Submit</button>
  		<div class="pull-right">
  		<a href="/authentication/google.do">
  			<span>
  				<img src="http://images.sb.a-cti.com/images/Google signin_Red_Long.png">
  			</span>
  		</a>
  		</div>
  		
		</form>
      </div>
  </div>
</div>
</div>




<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="js/jquery-1.11.1.min.js" type="text/javascript"><\/script>')</script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
</html>