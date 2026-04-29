import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

    // GET: Show payment page
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        String courseIdParam = req.getParameter("courseId");
        if (courseIdParam == null) {
            res.sendRedirect("courses");
            return;
        }

        int courseId = Integer.parseInt(courseIdParam);

        try {
            Connection con = DBConnection.getConnection();

            // Check already enrolled
            int userId = (int) session.getAttribute("userId");
            PreparedStatement checkPs = con.prepareStatement(
                "SELECT id FROM enrollments WHERE user_id=? AND course_id=?"
            );
            checkPs.setInt(1, userId);
            checkPs.setInt(2, courseId);
            ResultSet checkRs = checkPs.executeQuery();
            if (checkRs.next()) {
                res.sendRedirect("my-courses?msg=already");
                return;
            }

            // Fetch course details
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM courses WHERE id=?"
            );
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                req.setAttribute("courseId", courseId);
                req.setAttribute("courseTitle", rs.getString("title"));
                req.setAttribute("coursePrice", rs.getDouble("price"));
                req.getRequestDispatcher("payment.jsp").forward(req, res);
            } else {
                res.sendRedirect("courses");
            }

        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("courses");
        }
    }

    // POST: Process simulated payment → then enroll
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        int courseId = Integer.parseInt(req.getParameter("courseId"));
        String cardNumber = req.getParameter("cardNumber");

        // Basic simulation: any 16-digit number passes
        if (cardNumber == null || cardNumber.replaceAll("\\s", "").length() != 16) {
            res.sendRedirect("payment?courseId=" + courseId + "&err=invalid");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT IGNORE INTO enrollments(user_id, course_id, payment_status) VALUES(?,?,'paid')"
            );
            ps.setInt(1, userId);
            ps.setInt(2, courseId);
            ps.executeUpdate();

            res.sendRedirect("my-courses?msg=success");

        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("payment?courseId=" + courseId + "&err=db");
        }
    }
}