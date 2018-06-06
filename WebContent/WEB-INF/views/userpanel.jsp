<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Warsztaty 3</title>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
</head>
<style>
<%@ include file="css/style.css"%>
</style>
<body>

<div>
<jsp:include page="/WEB-INF/views/headers/header_u.jsp"/>
<div class="container main">
<c:if test="${empty useradmin}">
<br>
<form action='userPanel' method='Post'>
<label>
Email:
<input type="text" name="email">
</label>
<br>
<label>
Password:
<input type="password" name="pass">
</label>
<br>
<button type="submit" class="btn btn-light pow">Submit</button>
</form>
<c:if test="${not empty userfail}">
<br><p>User does not exist. Try again.</p>
</c:if>
<c:if test="${not empty loginfail}">
<br><p>Password incorrect. Try again.</p>
</c:if>
</c:if>

<c:if test="${not empty useradmin}">
<c:if test="${empty editsol}">
<h4 class="tytul">Hi ${loggedu.getUsername()}, these are your solutions:</h4>


<table class="table table-striped">
<col style="width:18%">
<col style="width:54%">
<col style="width:10%">
<col style="width:9%">
<col style="width:9%">
<thead>
<tr>
<th>Created</th>
<th>Your solution</th>
<th>Exercise id</th>
<th></th>
<th></th>
</tr>
</thead>
<tbody>
<c:forEach var="usrsol" items="${usersols}">
<tr>
<td>${usrsol.getCreated()}</td>
<td><samp>${usrsol.getDescription()}</samp></td>
<td>${usrsol.getExercise_id()}</td>
<td><a href='editSolution?soluId=${usrsol.getId()}'>edit</a></td>
<td><a href='deleteSolution?soluId=${usrsol.getId()}'>delete</a></td>
</tr>
</c:forEach>
</tbody>
</table>
</c:if>
<c:if test="${not empty editsol}">

<h4 class="tytul">Editing Solution id ${editsol.getId()}</h4>

<table class="table table-striped">
<col style="width:18%">
<col style="width:72%">
<col style="width:10%">
<thead>
<tr>
<th>Created</th>
<th>Your solution</th>
<th>Exercise id</th>

</tr>
</thead>
<tbody>

<tr>
<td>${editsol.getCreated()}</td>
<td><samp>${editsol.getDescription()}</samp></td>
<td>${editsol.getExercise_id()}</td>

</tr>

</tbody>
</table>

<form action='editSolution' method='Post'>

<label>
Your solution:
<input type="text" name="desc">
</label>
<br>
<label>
Solution id:
<input type="number" name="soluId" value="${editsol.getId()}" readonly>
</label>
<br>
<label>
Exercise id:
<input type="number" name="exerid" value="${editsol.getExercise_id()}" readonly>
</label>
<br>
<label>
Your user id:
<input type="number" name="userid" value="${editsol.getUsers_id()}" readonly>
</label>
<br>
<button type="submit" class="btn btn-light pow">Submit</button>
<a href="userPanel"><button type="button" class="btn btn-light pow">Cancel</button></a>
</form>

</c:if>


<c:if test="${not empty addsol}">

<form action='addSolution' method='Post'>

<label>
Your solution:
<input type="text" name="desc">
</label>
<br>
<label>
Choose Exercise:
<select name='exerid'>
<c:forEach var='exr' items='${allexer}'>
<option value='${exr.getId()}'>${exr.getId()}. ${exr.getTitle()}</option>
</c:forEach>
</select>
</label>
<br>
<label>
Your user id:
<input type="number" name="userid" value="${userId}" readonly>
</label>
<br>
<button type="submit" class="btn btn-light pow">Submit</button>
<a href="userPanel"><button type="button" class="btn btn-light pow">Cancel</button></a>
</form>
</c:if>




<c:if test="${empty editsol && empty addsol}">
<a href="addSolution?addsol=new"><button type="button" class="btn btn-light powright float-right">Add new solution</button></a>
<br>
<a href="logoutUser"><button type="button" class="btn btn-light pow">Logout</button></a>
</c:if>
</c:if>




<jsp:include page="/WEB-INF/views/footer.jsp"/>
</div>
</div>

</body>
</html>