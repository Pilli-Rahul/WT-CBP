<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Payment — LearnHub</title>
  <link rel="stylesheet" href="style.css">
  <style>
    .card-row { display: grid; grid-template-columns: 1fr 1fr; gap: 12px; }
    .secure-note {
      text-align: center;
      color: var(--muted);
      font-size: 0.78rem;
      margin-top: 14px;
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 5px;
    }
  </style>
</head>
<body>
<div class="auth-bg">
  <div class="auth-card" style="max-width:460px;">

    <div class="auth-logo">
      <div class="logo-icon">💳</div>
      <h1>Secure Payment</h1>
      <p>Complete your enrollment</p>
    </div>

    <%
      String err = request.getParameter("err");
      if ("invalid".equals(err)) {
    %>
      <div class="alert alert-error">⚠️ Invalid card number. Please enter a valid 16-digit number.</div>
    <% } else if ("db".equals(err)) { %>
      <div class="alert alert-error">⚠️ Something went wrong. Please try again.</div>
    <% } %>

    <%
      Object titleAttr = request.getAttribute("courseTitle");
      Object priceAttr = request.getAttribute("coursePrice");
      Object idAttr    = request.getAttribute("courseId");
    %>

    <div class="course-info-box">
      <div>
        <div style="font-size:0.75rem;color:var(--muted);text-transform:uppercase;letter-spacing:.7px;margin-bottom:3px;">Enrolling in</div>
        <div class="ci-title"><%= titleAttr %></div>
      </div>
      <div class="ci-price">₹<%= priceAttr %></div>
    </div>

    <form action="payment" method="post">
      <input type="hidden" name="courseId" value="<%= idAttr %>">

      <div class="form-group">
        <label>Card Number</label>
        <input type="text" name="cardNumber" placeholder="1234 5678 9012 3456" maxlength="19"
               oninput="formatCard(this)" required>
      </div>

      <div class="form-group">
        <label>Name on Card</label>
        <input type="text" name="cardName" placeholder="Full name" required>
      </div>

      <div class="card-row">
        <div class="form-group">
          <label>Expiry</label>
          <input type="text" name="expiry" placeholder="MM / YY" maxlength="5" required>
        </div>
        <div class="form-group">
          <label>CVV</label>
          <input type="text" name="cvv" placeholder="•••" maxlength="3" required>
        </div>
      </div>

      <button type="submit" class="btn-primary">🔒 Pay & Enroll →</button>
    </form>

    <div class="secure-note">🔐 Simulated payment — no real charges</div>

    <hr class="divider">
    <div class="auth-footer"><a href="courses">← Back to Courses</a></div>

  </div>
</div>

<script>
  function formatCard(input) {
    let v = input.value.replace(/\D/g,'').substring(0,16);
    input.value = v.replace(/(.{4})/g,'$1 ').trim();
  }
</script>
</body>
</html>
