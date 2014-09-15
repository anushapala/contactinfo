<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%

String  userId = (String)session.getAttribute("userId");
   if(userId == null) 
   {
	 response.sendRedirect("/jsp/contactMain.jsp");  
	}
   
   else
   {
	   response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	   response.setHeader("Cache-Control","no-store");
	   response.setHeader("Pragma","no-cache");
   }
%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>user profile</title>


<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"> -->
<link rel="stylesheet"  type="text/css" href="/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/css/contactInfo.css">
</head>
<body background="/images/userbg.jpg">
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
            <li class="active"><a href="/jsp/userProfile.jsp">Home</a></li>
            <li><a data-toggle="modal" data-target="#addcontact" >Add</a></li>
            <li><a  class="btn btn-default btn-sm" id="logout" href="/logout.do">
  				<span class="glyphicon glyphicon-off"></span>
			</a></li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </div>
</nav>

<div id="userimage">
<img src="/images/profile.png" alt="profile" class="img-circle" width="160px;">	
  </div>
	
	<dl class="dl-horizontal">
  		<dt>First Name</dt>
  			<dd><%out.println((String)session.getAttribute("firstname")); %></dd>
  		<dt>Last Name</dt>
  			<dd><%out.println((String)session.getAttribute("lastname")); %></dd>
  		<dt>User Name</dt>
  			<dd><%out.println((String)session.getAttribute("username")); %></dd>
  		<dt>Email ID</dt>
  			<dd><%out.println((String)session.getAttribute("emailid")); %></dd>
	</dl>
	
	


<div class="modal fade" id="addcontact" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Add Contact</h4>
      </div>
      <div class="modal-body">
        
        <form role="form" id="addform">
  		
  		<div class="form-group">
    		<label for="exampleInputEmail1">First Name</label>
    		<input type="text" class="form-control"  id="contactfirstname" placeholder="Enter First name" name="contact_firstname">
  		</div>
  
  		<div class="form-group">
    		<label for="exampleInputEmail1">Last Name</label>
    		<input type="text" class="form-control"  id="contactlastname" placeholder="Enter Last name" name="contact_lastname">
  		</div>
  
  		<div class="form-group">
    		<label for="exampleInputEmail1">Email address</label>
    		<input type="email" class="form-control"  id="contactemail" placeholder="Enter email" name="contact_email">
  		</div>
  		
  		<div class="form-group">
    		<label for="exampleInputEmail1">Mobile </label>
    		<input type="text" class="form-control"  id="contactmobile" placeholder="Enter mobile no." name="contact_mobile_no">
  		</div>
  
  		
  		<button type="button" class="btn btn-default" onClick="addContact()">Submit</button>
</form>
      </div>
    </div>
  </div>
</div>


<div class="modal fade" id="updatecontact" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Update Contact</h4>
      </div>
      <div class="modal-body">
        
        <form role="form" id="updateform">
  		
  		<div class="form-group">
    		<label for="exampleInputEmail1">First Name</label>
    		<input type="text" class="form-control"  id="updatefirstname" placeholder="Enter First name" name="update_firstname">
  		</div>
  
  		<div class="form-group">
    		<label for="exampleInputEmail1">Last Name</label>
    		<input type="text" class="form-control"  id="updatelastname" placeholder="Enter Last name" name="update_lastname">
  		</div>
  
  		<div class="form-group">
    		<label for="exampleInputEmail1">Email address</label>
    		<input type="email" class="form-control"  id="updateemail" placeholder="Enter email" name="update_email">
  		</div>
  		
  		<div class="form-group">
    		<label for="exampleInputEmail1">Mobile </label>
    		<input type="text" class="form-control"  id="updatemobile" placeholder="Enter mobile no." name="update_mobile_no">
  		</div>
  
  		
  		<button type="button" id="update_contact" class="btn btn-success" onClick="updateContact()">Update</button>
    </form>
      </div>
    </div>
  </div>
</div>

<div class="col-lg-6" style= " position: relative; top: 220px; left: 220px">
    <div class="input-group">
      <input type="text" class="form-control" id="searchString" placeholder="enter search string" name="search_string">
      <span class="input-group-btn">
        <!--<button class="btn btn-default" type="button">Go!</button>  -->
        <button type="button" class="btn btn-default btn-lg"  id="search" onClick="searchContact()">
  			<span class="glyphicon glyphicon-search" ></span>
		</button>
      </span>
    </div><!-- /input-group -->
    <div id="displayList" style="position: relative;height: 20px;">
   
   </div>
  </div><!-- /.col-lg-6 -->
</div><!-- /.row -->

<!--model to  display the contact on front end -->

<div class="list-group pull-right" style="width:250px;position:relative;right:4%">
   <a href="#" class="list-group-item active">
      <h4 class="list-group-item-heading">
        Contacts
      </h4>
   </a>
		
   <div id="contactlist" style="position: relative; overflow-y: auto; height: 413px;">
   
   </div>
 
</div><!-- /.row -->
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="js/jquery-1.11.1.min.js" type="text/javascript"><\/script>')</script>
<!-- <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script> -->
<script src="/bootstrap/js/bootstrap.min.js"></script>
<script  src="/js/contactinfo.js"></script>
</body>
</html>