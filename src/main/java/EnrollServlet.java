import java.io.IOException;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/enroll")
public class EnrollServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        try {
            HttpSession session = req.getSession(false);

            if (session == null || session.getAttribute("userId") == null) {
                res.sendRedirect("login.jsp");
                return;
            }

            int userId = (int) session.getAttribute("userId");
            int courseId = Integer.parseInt(req.getParameter("courseId"));

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO enrollments(user_id, course_id) VALUES(?,?)"
            );

            ps.setInt(1, userId);
            ps.setInt(2, courseId);

            ps.executeUpdate();

            res.getWriter().println("<h3>Enrolled Successfully ✅</h3>");
            res.getWriter().println("<a href='courses'>Back to Courses</a>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}