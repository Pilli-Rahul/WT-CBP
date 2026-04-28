import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users (username, email, password) VALUES (?, ?, ?)"
            );

            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);

            ps.executeUpdate();

            res.getWriter().println("User Registered Successfully ✅");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}