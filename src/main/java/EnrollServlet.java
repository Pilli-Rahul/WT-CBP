import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/enroll")
public class EnrollServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");

        try {
            int courseId = Integer.parseInt(req.getParameter("courseId"));

            int userId = 1; // TEMP (we will replace with login later)

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO enrollments (user_id, course_id) VALUES (?, ?)"
            );

            ps.setInt(1, userId);
            ps.setInt(2, courseId);

            ps.executeUpdate();

            res.getWriter().println("<h3>Enrolled Successfully ✅</h3>");

        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Error: " + e);
        }
    }
}