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
<jsp:include page="/WEB-INF/views/headers/header_s.jsp"/>
<div class="container main">

<h4 class="tytul">Solution ${solu.getId()}</h4>
<table class="table table-striped">
<col style="width:5%">
<col style="width:18%">
<col style="width:61%">
<col style="width:7%">
<col style="width:9%">
<thead>
<tr>
<th>id</th>
<th>Created</th>
<th>Description</th>
<th>Exercise</th>
<th>User id</th>
</tr>
</thead>
<tbody>
<tr>
<td>${solu.getId()}</td>
<td>${solu.getCreated()}</td>
<td><samp>${solu.getDescription()}</samp></td>
<td>${solu.getExercise_id()}</td>
<td>${solu.getUsers_id()}</td>

</tr>
</tbody>
</table>
<c:if test="${not empty noparam}">
<p>404 - nie podano parametru</p>
</c:if>
<div>
<a href='./'><button type="button" class="btn btn-light pow">Powrót</button></a>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</div>
</div>
</body>
</html>