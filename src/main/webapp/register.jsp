<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>

    <style>
        body {
            font-family: Arial;
            background: linear-gradient(to right, #43e97b, #38f9d7);
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
            background: #43e97b;
            border: none;
            color: white;
            cursor: pointer;
        }

        button:hover {
            background: #2ecc71;
        }

        a {
            text-decoration: none;
            color: #2ecc71;
        }

        .error {
            color: red;
            text-align: center;
        }
    </style>

    <script>
        function validate() {
            let u = document.getElementById("username").value;
            let p = document.getElementById("password").value;

            if (u.length < 8) {
                alert("Username must be at least 8 characters");
                return false;
            }

            if (p.length < 5) {
                alert("Password must be at least 5 characters");
                return false;
            }

            return true;
        }
    </script>
</head>

<body>

<div class="box">

    <h2 style="text-align:center;">Register</h2>

    <!-- OPTIONAL ERROR (if you later add servlet validation) -->
    <%
        String error = request.getParameter("error");
        if (error != null) {
    %>
        <p class="error">Registration failed</p>
    <%
        }
    %>

    <!-- ✅ REGISTER FORM -->
    <form action="register" method="post" onsubmit="return validate()">

        <input type="text" id="username" name="username" placeholder="Username" required>

        <input type="email" name="email" placeholder="Email" required>

        <input type="password" id="password" name="password" placeholder="Password" required>

        <button type="submit">Register</button>

    </form>

    <p style="text-align:center;">
        Already have an account? <a href="login.jsp">Login</a>
    </p>

</div>

</body>
</html>