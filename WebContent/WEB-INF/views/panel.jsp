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
<jsp:include page="/WEB-INF/views/headers/header_p.jsp"/>
<div class="container main">
<br>
<c:if test="${empty admin}">
<form action='panel' method='Post'>
<label>
Login:
<input type="text" name="login">
</label>
<br>
<label>
Password:
<input type="password" name="pass">
</label>
<br>
<button type="submit" class="btn btn-light pow">Submit</button>
</form>
<c:if test="${not empty authFalse}">
<br><p>Login failed. Try again.</p>
</c:if>

</c:if>
<c:if test="${not empty admin}">
<div class="container">
<ul class="nav justify-content-center">
    <li class="nav-item">
      <a class="nav-link active" href="panel">User admin</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="groupAdmin">Group admin</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="exerAdmin">Exercise admin</a>
    </li>
  </ul>  
</div>
<br>
<c:if test="${empty edit}">
<h5>All users</h5>
</c:if>
<c:if test="${not empty edit}">
<h5>Editing user: ${userek.getUsername()}</h5>
<table class="table table-striped">
<col style="width:5%">
<col style="width:17%">
<col style="width:60%">
<col style="width:18%">
<thead>
<tr>
<th>id</th>
<th>Name</th>
<th>Email</th>
<th>Group</th>
</tr>
</thead>
<tbody>
<tr>
<td>${userek.getId()}</td>
<td>${userek.getUsername()}</td>
<td>${userek.getEmail()}</td>
<td>${userek.getUsergroup_id()}</td>
</tr>
</tbody>
</table>
</c:if>
<c:if test="${empty edit}">
<table class="table table-striped">
<col style="width:5%">
<col style="width:17%">
<col style="width:50%">
<col style="width:10%">
<col style="width:8%">
<col style="width:10%">
<thead>
<tr>
<th>id</th>
<th>Name</th>
<th>Email</th>
<th>Group</th>
<th></th>
<th></th>
</tr>
</thead>
<tbody>
<c:forEach var="usr" items="${users}">
<tr>
<td>${usr.getId()}</td>
<td>${usr.getUsername()}</td>
<td>${usr.getEmail()}</td>
<td>${usr.getUsergroup_id()}</td>
<td><a href='editUser?userId=${usr.getId()}'>edit</a></td>
<td><a href='deleteUser?userId=${usr.getId()}'>delete</a></td>
</tr>
</c:forEach>
</tbody>
</table>
<c:if test="${not empty addnew}">


<form action='addUser' method='Post'>

<label>
Name:
<input type="text" name="name">
</label>
<br>
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
<label>
Choose group:
<select name='group'>
<c:forEach var='grp' items='${groups}'>
<option value='${grp.getId()}'>${grp.getName()}</option>
</c:forEach>
</select>
</label>
<br>
<button type="submit" class="btn btn-light pow">Submit</button>
<a href="panel"><button type="button" class="btn btn-light pow">Cancel</button></a>
</form>
<c:if test="${not empty invalidemail}">
<br><p>podano błędny email</p>
</c:if>
</c:if>

</c:if>
<c:if test="${not empty edit}">
<form action='editUser' method='Post'>
<label>
User id:
<input type="number" name="usId" value="${edit}" readonly>
</label>
<br>
<label>
Name:
<input type="text" name="name">
</label>
<br>
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
<label>
Choose group:
<select name='group'>
<c:forEach var='grp' items='${groups}'>
<option value='${grp.getId()}'>${grp.getName()}</option>
</c:forEach>
</select>
</label>
<br>
<button type="submit" class="btn btn-light pow">Submit</button>
<a href="panel"><button type="button" class="btn btn-light pow">Cancel</button></a>
</form>
<c:if test="${not empty invalidemail}">
<br><p>podano błędny email</p>
</c:if>
</c:if>
<c:if test="${empty edit && empty addnew}">
<a href="addUser?add=new"><button type="button" class="btn btn-light powright float-right">Add user</button></a>
<br>
<a href="logout"><button type="button" class="btn btn-light pow">Logout</button></a>
</c:if>
</c:if>

<jsp:include page="/WEB-INF/views/footer.jsp"/>
</div>
</div>
</body>
</html>