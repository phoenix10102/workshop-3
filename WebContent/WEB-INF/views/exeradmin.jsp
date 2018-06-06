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
      <a class="nav-link" href="panel">User admin</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="groupAdmin">Group admin</a>
    </li>
    <li class="nav-item">
      <a class="nav-link active" href="exerAdmin">Exercise admin</a>
    </li>
  </ul>  
</div>
<br>
<c:if test="${empty editexer}">
<h5>All Exercises</h5>
</c:if>

<c:if test="${not empty editexer}">
<h5>Editing exercise: ${cwicz.getTitle()}</h5>
<table class="table table-striped">
<col style="width:5%">
<col style="width:18%">
<col style="width:59%">


<thead>
<tr>
<th>id</th>
<th>Title</th>
<th>Description</th>

</tr>
</thead>
<tbody>
<tr>
<td>${cwicz.getId()}</td>
<td>${cwicz.getTitle()}</td>
<td>${cwicz.getDescription()}</td>

</tr>
</tbody>
</table>
<form action='editExer' method='Post'>


<label>
Id:
<input type="number" name="exerId" value="${cwicz.getId()}" readonly>
</label>
<br>
<label>
Title:
<input type="text" name="title">
</label>
<br>
<label>
Description:
<input type="text" name="desc">
</label>
<br>

<button type="submit" class="btn btn-light pow">Submit</button>
<a href="exerAdmin"><button type="button" class="btn btn-light pow">Cancel</button></a>
</form>
<c:if test="${not empty invalidexer}">
<br><p>podano błędną nazwę zadania</p>
</c:if>

</c:if>

<c:if test="${empty editexer}">
<table class="table table-striped">
<col style="width:5%">
<col style="width:18%">
<col style="width:59%">
<col style="width:8%">
<col style="width:10%">

<thead>
<tr>
<th>id</th>
<th>Title</th>
<th>Description</th>
<th></th>
<th></th>
</tr>
</thead>
<tbody>
<c:forEach var="exerr" items="${exercises}">
<tr>
<td>${exerr.getId()}</td>
<td>${exerr.getTitle()}</td>
<td>${exerr.getDescription()}</td>
<td><a href='editExer?exerId=${exerr.getId()}'>edit</a></td>
<td><a href='deleteExer?exerId=${exerr.getId()}'>delete</a></td>
</tr>
</c:forEach>
</tbody>
</table>
<c:if test="${not empty addnewexer}">


<form action='addExer' method='Post'>

<label>
Title:
<input type="text" name="title">
</label>
<br>
<label>
Description:
<input type="text" name="desc">
</label>
<br>

<button type="submit" class="btn btn-light pow">Submit</button>
<a href="exerAdmin"><button type="button" class="btn btn-light pow">Cancel</button></a>
</form>
<c:if test="${not empty invalidexer}">
<br><p>podano błędną nazwę grupy</p>
</c:if>
</c:if>

</c:if>

<c:if test="${empty editexer && empty addnewexer}">
<a href="addExer?addexer=new"><button type="button" class="btn btn-light powright float-right">Add new exercise</button></a>
<br>
<a href="logout"><button type="button" class="btn btn-light pow">Logout</button></a>
</c:if>
</c:if>

<jsp:include page="/WEB-INF/views/footer.jsp"/>
</div>
</div>
</body>
</html>