<%@ page import="com.smartcode.SpringMVC.util.constants.Parameter" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 6/20/2023
  Time: 1:05 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register Page</title>
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
          flex-direction: column;
          display: flex;
          justify-content: center;
          align-items: center;
          background: #009579;
      }
      .container{
          max-width: 600px;
          width: 100%;
          background: #fff;
          border-radius: 7px;
          box-shadow: 0 5px 10px rgba(0,0,0,0.3);
      }
      .container .form{
        padding: 2rem;
      }
      .form header{
        font-size: 2rem;
        font-weight: 500;
        text-align: center;
        margin-bottom: 1.5rem;
      }

      .register_form {
          display: flex;
          justify-content: space-between;
          flex-wrap: wrap;
      }
      .form input {
        height: 60px;
        width: 47%;
        padding: 0 15px;
        font-size: 17px;
        margin-bottom: 1.3rem;
        border: 1px solid #ddd;
        border-radius: 6px;
        outline: none;
      }

      .form form {
          display: flex;
          flex-direction: column;
          justify-content: center;
      }

      .form input:focus{
        box-shadow: 0 1px 0 rgba(0,0,0,0.2);
      }
      .form a{
        font-size: 16px;
        color: #009579;
        text-decoration: none;
      }
      .form a:hover{
        text-decoration: underline;
      }
      .form input.button{
        color: #fff;
        background: #009579;
        font-size: 1.2rem;
        font-weight: 500;
        letter-spacing: 1px;
        margin: 0.7rem auto;
        cursor: pointer;
        transition: 0.4s;
      }
      .form input.button:hover{
        background: #006653;
      }
      .signup{
        font-size: 17px;
        text-align: center;
      }
      .signup label{
        color: #009579;
        cursor: pointer;
      }
      .signup label:hover{
        text-decoration: underline;
      }

      .message {
          padding-bottom: 30px;
          color: red;
      }
    </style>
</head>
<body>


    <h3 class="message"><%= request.getAttribute(Parameter.MESSAGE_ATTRIBUTE) != null ?
            request.getAttribute(Parameter.MESSAGE_ATTRIBUTE) : "" %></h3>

      <div class="container">
          <div class="registration form">
              <header>Signup</header>
              <form method="post" action="/register" >
                  <div class="register_form">
                      <input type="text" placeholder="Enter your name" name="name">
                      <input type="text" placeholder="Enter your lastname" name="lastname">
                      <input type="text" placeholder="Enter your balance" name="balance">
                      <input type="email" placeholder="Enter your email" name="email">
                      <input type="password" placeholder="Enter your password" name="password">
                      <input type="text" placeholder="Enter your age" name="age">
                  </div>
                  <input type="submit" class="button" value="register">
              </form>
              <div class="signup">
            <span class="signup">Already have an account?
             <a href="login.jsp">Login</a>
            </span>
              </div>
          </div>
      </div>
      </form>
</body>
</html>
