<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">

    <title>JSP 게시판</title>
  </head>
  <body>
  	<nav class="navbar navbar-expand-sm navbar-light bg-light">
  		<a class="navbar-brand" href="main.jsp">JSP 게시판</a>
  		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>  			
  		</button>
  		<div class="collapse navbar-collapse" id="navbarSupportedContent">
  			<ul class="navbar-nav">
  				<li class="nav-item">
  					<a class="nav-link" href="main.jsp">메인</a>
  				</li>
  				<li class="nav-item">
  					<a class="nav-link" href="bbs.jsp">게시판</a>
  				</li>
  			</ul>
  			<ul class="navbar-nav ml-auto">
  				<li class="nav-item dropdown">
  					<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
  					role="button" data-toggle="dropdown" aria-haspopup="true"
  					aria-expanded="false">접속하기</a>
  					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
				  		<a class="dropdown-item" href="login.jsp">
				  			로그인
				  		</a> <!-- active와 span-sr-only는 같이씀 -->
				  		<div class="dropdown-divider"></div>
				  		<a class="dropdown-item active" href="join.jsp">
				  			회원가입<span class="sr-only">(current)</span>
				  		</a>
  					</div>
  				</li>
  			</ul>
  		</div>
  	</nav>
    <div class="container pt-3">
    	<div class="row">
	    	<div class="col-lg-4 offset-lg-4">
	    		<div class="jumbotron pt-5">
	    			<form method="post" action="joinaction.jsp">
	    				<h3 class="text-center pb-2">회원가입</h3>
	    				<div class="form-group">
	    					<input type="text" class="form-control" name="userId"
	    					placeholder="아이디" maxlength="20" required="required"/>
	    				</div>
	    				<div class="form-group">
	    					<input type="password" class="form-control" name="userPassword"
	    					placeholder="비밀번호" maxlength="20" required="required"/>
	    				</div>
	    				<div class="form-group">
	    					<input type="text" class="form-control" name="userName"
	    					placeholder="이름" maxlength="20" required="required"/>
	    				</div>
	    				<div class="form-group text-center">
	    					<div class="btn-group" data-toggle="buttons">
	    						<label class="btn btn-primary active">
	   			 					<input type="radio" class="d-none" name="userGender"
	    							autocomplete="off" value="남자" checked="checked"/>남자
	    						</label>
	    						<label class="btn btn-primary">
	   			 					<input type="radio" class="d-none" name="userGender"
	    							autocomplete="off" value="여자" />여자
	    						</label>
	    					</div>
	    				</div>
	    				<div class="form-group">
	    					<input type="email" class="form-control" name="userEmail"
	    					placeholder="이메일" maxlength="20" required="required"/>
	    				</div>
	    				<button type="submit" class="btn btn-primary form-control">가입</button>
	    			</form>
	    		</div>
	    	</div>
    	</div>
    </div>
    
    <!-- Optional JavaScript-->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"></script>
  </body>
</html>