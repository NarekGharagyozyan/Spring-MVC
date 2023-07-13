<%@ page import="java.util.List" %>
<%@ page import="com.smartcode.SpringMVC.util.constants.Parameter" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 6/19/2023
  Time: 8:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700&display=swap');
        *{
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', sans-serif;
        }
        body{
            min-height: 100vh;
            width: 100%;
            display: flex;
            flex-direction: column;
            align-items: center;
            background: #009579;
        }

        .form {
            padding: 2rem;
            max-width: 400px;
            width: 100%;
            border-radius: 15px;
            background-color: #fff;
        }

        .button {
            color: #fff;
            background: #009579;
            font-size: 1.2rem;
            font-weight: 500;
            letter-spacing: 1px;
            margin-top: 1.7rem;
            cursor: pointer;
            transition: 0.4s;
        }

        .form input{
            height: 60px;
            margin: 0 auto 25px;
            width: 100%;
            padding: 0 15px;
            font-size: 17px;
            border: 1px solid #ddd;
            border-radius: 6px;
            outline: none;
        }

        .form input:focus {
            box-shadow: 0 1px 0 rgba(0,0,0,0.2);
        }

        .header h2 {
            color: #fff;
        }

        .message {
            padding-bottom: 30px;
            color: red;
        }

        .btn {
            padding: 15px;
            border: none;
            outline: none;
            font-size: 14px;
            border-radius: 5px;
            background-color: #006653;
            color: #fff;
            cursor: pointer;
            text-decoration: none;
        }

        .header {
            max-width:1300px;
            width:100%;
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 40px;
        }

        .buttons {
            max-width: 500px;
            width: 100%;
            display: flex;
            margin-top: 60px;
            justify-content: space-between;
        }
    </style>
</head>
<body>
    <div  class="header">
        <h2>Home Page</h2>
        <h2><%= request.getSession().getAttribute(Parameter.EMAIL_PARAMETER) != null ?
                "Welcome Dear " + request.getSession().getAttribute(Parameter.EMAIL_PARAMETER) : ""%></h2>
        <form class="btn_form" action="/secure/logout">
            <input class="btn" type="submit" value="logout">
        </form>
    </div>

    <h3 class="message" ><%= request.getAttribute(Parameter.MESSAGE_ATTRIBUTE) != null ?
            request.getAttribute(Parameter.MESSAGE_ATTRIBUTE) : "" %></h3>

    <div class="buttons">
        <a href="/secure/changePassword.jsp" class="btn" >Change Password</a>
        <form method="post" action="/secure/deleteAccount">
            <input class="btn" type="submit" value="deleteAccount">
        </form>
    </div>

</body>
</html>
