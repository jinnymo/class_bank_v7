<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header.jsp -->
<%@include file="/WEB-INF/view/layout/header.jsp"%>


<!-- start of content.jsp(xxx.jsp) -->

<div class="col-sm-8">
	<h2>Register</h2>
	<h5>welecome to bank app</h5>


	<form action="/user/sign-up" method="post">
	
		<div class="form-group">
			<label for="username">username :</label> 
			<input type="text" class="form-control" placeholder="Enter username" id="username" name="username" value="야스오">
		</div>
		
		<div class="form-group">
			<label for="pwd">Password :</label> 
			<input type="password" class="form-control" placeholder="Enter password" id="pwd" name="password" value="asd123">
		</div>
		
		
		<div class="form-group">
			<label for="fullname">fullname :</label> 
			<input type="text" class="form-control" placeholder="Enter fullname" id="fullname" name="fullname" value="바람의 검객">
		</div>
		
		
		<button type="submit" class="btn btn-primary">회원가입</button>
		
		<div>
		<a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=c9133bb1945e2fd143b7ef2c8af37a6d&redirect_uri=http://localhost:8080/user/kakao">
		     <img src="" alt="images/kakao_login_small.png">

		 </a>
		</div>
	</form>



</div>
</div>
</div>

<!-- end of content -->


<!-- footer.jsp -->
<%@include file="/WEB-INF/view/layout/footer.jsp"%>
