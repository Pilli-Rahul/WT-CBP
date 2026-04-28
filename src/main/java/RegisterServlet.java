import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // ✅ Get form data
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // ✅ BACKEND VALIDATION (IMPORTANT)
        if (username == null || username.length() < 8) {
            out.println("<h3>Username must be at least 8 characters ❌</h3>");
            out.println("<a href='register.html'>Go Back</a>");
            return;
        }

        if (password == null || password.length() < 5) {
            out.println("<h3>Password must be at least 5 characters ❌</h3>");
            out.println("<a href='register.html'>Go Back</a>");
            return;
        }

        try {
            // ✅ DB connection
            Connection con = DBConnection.getConnection();

            // ✅ Insert user
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users (username, email, password) VALUES (?, ?, ?)"
            );

            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);

            ps.executeUpdate();

            // ✅ Redirect to login page after success
            res.sendRedirect("login.html");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error: " + e + "</h3>");
        }
    }
}