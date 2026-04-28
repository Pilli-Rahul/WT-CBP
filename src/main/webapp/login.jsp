<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>

    <style>
        body {
            font-family: Arial;
            background: linear-gradient(to right, #4facfe, #00f2fe);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .box {
            background: white;
            padding: 30px;
            width: 320px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.2);
        }

        input {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
        }

        button {
            width: 100%;
            padding: 10px;
            background: #4facfe;
            border: none;
            color: white;
            cursor: pointer;
        }

        button:hover {
            background: #00c6ff;
        }

        .error {
            color: red;
            text-align: center;
        }

        a {
            text-decoration: none;
            color: #4facfe;
        }
    </style>
</head>

<body>

<div class="box">

    <h2 style="text-align:center;">Login</h2>

    <!-- ✅ ERROR MESSAGE FROM SERVLET -->
    <%
        String error = request.getParameter("error");
        if (error != null) {
    %>
        <p class="error">Invalid username or password</p>
    <%
        }
    %>

    <!-- ✅ LOGIN FORM -->
    <form action="login" method="post">
        <input type="text" name="username" placeholder="Username" required>
        <input type="password" name="password" placeholder="Password" required>

        <button type="submit">Login</button>
    </form>

    <p style="text-align:center;">
        Don't have an account? <a href="register.jsp">Register</a>
    </p>

</div>

</body>
</html>