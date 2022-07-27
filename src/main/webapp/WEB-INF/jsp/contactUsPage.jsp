<%--
  Created by IntelliJ IDEA.
  User: Tony
  Date: 7/15/2022
  Time: 2:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Contact Us</title>
</head>
<body>
<div class="container" style=" height: 100%; width: 100%; ">
    <%@include file="navBar.jsp"%>
    <div>
        <h1>Contact us!</h1>
        <p>Address: 50 Millstone Rd, Building 300. Suite 120, East Windsor, NJ 08520</p>
        <p>Email: info@beaconfiresolution.com</p>
        <p>Phone: 609-608-0477</p>
    </div>
    <form action="contact">

        <label for="fname">First Name</label>
        <input type="text" id="fname" name="firstname" placeholder="Your name..">

        <label for="lname">Last Name</label>
        <input type="text" id="lname" name="lastname" placeholder="Your last name..">

        <label for="country">Country</label>
        <select id="country" name="country">
            <option value="australia">Australia</option>
            <option value="canada">Canada</option>
            <option value="china">China</option>
            <option value="usa">USA</option>
            <option value="other">Other</option>
        </select>

        <label for="subject">Subject</label>
        <textarea id="subject" name="subject" placeholder="Write something.." style="height:200px"></textarea>

        <input type="submit" value="Submit">

    </form>
</div>
</body>
</html>
