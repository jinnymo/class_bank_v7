<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header.jsp -->
<%@include file="/WEB-INF/view/layout/header.jsp"%>


<!-- start of content.jsp(xxx.jsp) -->

<div class="col-sm-8">
	<h2>Login</h2>
	<h5>welecome to bank app</h5>


	<form action="/user/sign-in" method="post">
	
		<div class="form-group">
			<label for="username">username :</label> 
			<input type="text" class="form-control" placeholder="Enter username" id="username" name="username" value="길동">
		</div>
		
		<div class="form-group">
			<label for="pwd">Password :</label> 
			<input type="password" class="form-control" placeholder="Enter password" id="pwd" name="password" value="1234">
		</div>
		
		<button type="submit" class="btn btn-primary">로그인</button>
	</form>



</div>
</div>
</div>

<!-- end of content -->


<!-- footer.jsp -->
<%@include file="/WEB-INF/view/layout/footer.jsp"%>
