<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Register — LearnHub</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="auth-bg">
  <div class="auth-card">

    <div class="auth-logo">
      <div class="logo-icon">✨</div>
      <h1>Create Account</h1>
      <p>Join thousands of learners today</p>
    </div>

    <%
      String error = request.getParameter("error");
      if (error != null) {
    %>
      <div class="alert alert-error">⚠️ Registration failed. Please try again.</div>
    <% } %>

    <form action="register" method="post" onsubmit="return validate()">
      <div class="form-group">
        <label>Username</label>
        <input type="text" id="username" name="username" placeholder="At least 8 characters" required>
      </div>
      <div class="form-group">
        <label>Email</label>
        <input type="email" name="email" placeholder="you@example.com" required>
      </div>
      <div class="form-group">
        <label>Password</label>
        <input type="password" id="password" name="password" placeholder="At least 5 characters" required>
      </div>
      <button type="submit" class="btn-primary">Create Account →</button>
    </form>

    <hr class="divider">

    <div class="auth-footer">
      Already have an account? <a href="login.jsp">Sign in</a>
    </div>

  </div>
</div>

<script>
  function validate() {
    const u = document.getElementById("username").value;
    const p = document.getElementById("password").value;
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
</body>
</html>
