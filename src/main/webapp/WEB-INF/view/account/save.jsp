<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header.jsp -->
<%@include file="/WEB-INF/view/layout/header.jsp"%>


<!-- start of content.jsp(xxx.jsp) -->

<div class="col-sm-8">
	<h2>create new Account(verify)</h2>
	<h5>welecome to bank app</h5>


	<form action="/account/save" method="post">
	
		<div class="form-group">
			<label for="number">number :</label> 
			<input type="text" class="form-control" placeholder="Enter number" id="number" name="number" value="1002-1234">
		</div>
		
		<div class="form-group">
			<label for="pwd">Password :</label> 
			<input type="password" class="form-control" placeholder="Enter password" id="pwd" name="password" value="1234">
		</div>
		
		<div class="form-group">
			<label for="balance">balance :</label> 
			<input type="number" class="form-control" placeholder="Enter balance" id="balance" name="balance" value="1002-1234">
		</div>
		<div class="text-right">
		<button type="submit" class="btn btn-primary">create account</button>
		</div>
	</form>



</div>
</div>
</div>

<!-- end of content -->


<!-- footer.jsp -->
<%@include file="/WEB-INF/view/layout/footer.jsp"%>
