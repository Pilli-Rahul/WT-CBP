<%@ page contentType="text/html;charset=UTF-8" %>
<%
  String role = (String) session.getAttribute("role");
  if (!"admin".equals(role)) {
    response.sendRedirect("courses");
    return;
  }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Add Course — LearnHub</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="auth-bg">
  <div class="auth-card">

    <div class="auth-logo">
      <div class="logo-icon">⚙️</div>
      <h1>Add New Course</h1>
      <p>Admin panel — course creation</p>
    </div>

    <%
      String err = request.getParameter("err");
      if ("empty".equals(err)) {
    %>
      <div class="alert alert-error">⚠️ Title and price are required.</div>
    <% } else if ("price".equals(err)) { %>
      <div class="alert alert-error">⚠️ Please enter a valid price.</div>
    <% } else if ("db".equals(err)) { %>
      <div class="alert alert-error">⚠️ Database error. Please try again.</div>
    <% } %>

    <form action="admin-add-course" method="post">
      <div class="form-group">
        <label>Course Title</label>
        <input type="text" name="title" placeholder="e.g. Advanced Java Programming" required autofocus>
      </div>
      <div class="form-group">
        <label>Price (₹)</label>
        <input type="number" name="price" placeholder="e.g. 1999" step="0.01" min="0" required>
      </div>
      <button type="submit" class="btn-primary">Publish Course →</button>
    </form>

    <hr class="divider">
    <div class="auth-footer"><a href="courses">← Back to Courses</a></div>

  </div>
</div>
</body>
</html>
