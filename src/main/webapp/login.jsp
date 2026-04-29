<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login — LearnHub</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="auth-bg">
  <div class="auth-card">

    <div class="auth-logo">
      <div class="logo-icon">🎓</div>
      <h1>LearnHub</h1>
      <p>Sign in to continue learning</p>
    </div>

    <%
      String error = request.getParameter("error");
      if (error != null) {
    %>
      <div class="alert alert-error">⚠️ Invalid username or password. Please try again.</div>
    <% } %>

    <form action="login" method="post">
      <div class="form-group">
        <label>Username</label>
        <input type="text" name="username" placeholder="Enter your username" required autofocus>
      </div>
      <div class="form-group">
        <label>Password</label>
        <input type="password" name="password" placeholder="Enter your password" required>
      </div>
      <button type="submit" class="btn-primary">Sign In →</button>
    </form>

    <hr class="divider">

    <div class="auth-footer">
      Don't have an account? <a href="register.jsp">Create one free</a>
    </div>

  </div>
</div>
</body>
</html>
