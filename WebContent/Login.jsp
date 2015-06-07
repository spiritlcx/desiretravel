<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to desiretravel</title>
<link rel="stylesheet" href="./jquery-ui.css">
<script src="./jquery.js"></script>
<script src="./jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">

  <script>
  $(function() {
    $( "#from" ).datepicker({
      defaultDate: "+1w",
      changeMonth: true,
      numberOfMonths: 3,
      onClose: function( selectedDate ) {
        $( "#to" ).datepicker( "option", "minDate", selectedDate );
      }
    });
    $( "#to" ).datepicker({
      defaultDate: "+1w",
      changeMonth: true,
      numberOfMonths: 3,
      onClose: function( selectedDate ) {
        $( "#from" ).datepicker( "option", "maxDate", selectedDate );
      }
    });
  });
  </script>
</head>
<body>
	<form action="getflights" method="get" name="form1">
		<label for="characteristic">characteristic</label>
		<input type="text" id="characteristic" name="characteristic">
	
		<label for="continent">Continent</label>
		<input type="text" id="continent" name="continent">

		<label for="yourcity">your city</label>
		<input type="text" id="yourcity" name="yourcity">

		<br/><br/>

		<label for="from">From</label>
		<input type="text" id="from" name="startDate">
		<label for="to">to</label>
		<input type="text" id="to" name="endDate"></body>

		<label for="days">days</label>
		<input type="text" id="days" name="days">

		<input type="submit" value = "submit"/>
	</form>
</html>