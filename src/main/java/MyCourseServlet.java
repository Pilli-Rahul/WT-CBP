import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/my-courses")
public class MyCourseServlet extends HttpServlet {

    private String nav(String role, String active) {
        StringBuilder sb = new StringBuilder();
        sb.append("<nav class='topbar'>");
        sb.append("  <a href='courses' class='topbar-brand'>");
        sb.append("    <div class='brand-icon'>🎓</div><span>LearnHub</span>");
        sb.append("  </a>");
        sb.append("  <div class='topbar-nav'>");
        sb.append("    <a href='courses' class='nav-link " + ("courses".equals(active) ? "active" : "") + "'>Courses</a>");
        sb.append("    <a href='my-courses' class='nav-link " + ("my-courses".equals(active) ? "active" : "") + "'>My Courses</a>");
        if ("admin".equals(role)) {
            sb.append("    <a href='admin-add-course.jsp' class='nav-link'>+ Add Course</a>");
        }
        sb.append("    <a href='logout.jsp' class='nav-link logout'>Logout</a>");
        sb.append("  </div>");
        sb.append("</nav>");
        return sb.toString();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        int    userId = (int)    session.getAttribute("userId");
        String role   = (String) session.getAttribute("role");
        String msg    = req.getParameter("msg");

        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();

        out.println("<!DOCTYPE html><html lang='en'><head>");
        out.println("<meta charset='UTF-8'><meta name='viewport' content='width=device-width,initial-scale=1'>");
        out.println("<title>My Courses — LearnHub</title>");
        out.println("<link rel='stylesheet' href='style.css'>");
        out.println("</head><body><div class='app-layout'>");

        out.println(nav(role, "my-courses"));

        out.println("<div class='page-content'>");

        out.println("<div class='page-header'>");
        out.println("  <h2>My Courses</h2>");
        out.println("  <p>Track your enrolled courses and payment status</p>");
        out.println("</div>");

        if ("success".equals(msg)) {
            out.println("<div class='alert alert-success' style='margin-bottom:24px;'>✅ Successfully enrolled! Start learning today.</div>");
        } else if ("already".equals(msg)) {
            out.println("<div class='alert alert-warn' style='margin-bottom:24px;'>⚠️ You are already enrolled in that course.</div>");
        }

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT c.id, c.title, c.price, e.payment_status " +
                "FROM enrollments e JOIN courses c ON e.course_id = c.id " +
                "WHERE e.user_id = ? ORDER BY e.id DESC"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            boolean found = false;

            out.println("<div class='table-wrap'>");
            out.println("<table>");
            out.println("<thead><tr>");
            out.println("  <th>#</th><th>Course Title</th><th>Price</th><th>Payment</th>");
            out.println("</tr></thead><tbody>");

            while (rs.next()) {
                found = true;
                String status = rs.getString("payment_status");
                String badgeClass = "paid".equals(status) ? "badge-paid" : "badge-pending";

                out.println("<tr>");
                out.println("  <td style='color:var(--muted);'>" + rs.getInt("id") + "</td>");
                out.println("  <td style='font-weight:500;'>" + escapeHtml(rs.getString("title")) + "</td>");
                out.println("  <td style='color:var(--gold);font-weight:600;'>₹" + String.format("%.0f", rs.getDouble("price")) + "</td>");
                out.println("  <td><span class='badge " + badgeClass + "'>" + status + "</span></td>");
                out.println("</tr>");
            }

            if (!found) {
                out.println("<tr><td colspan='4'>");
                out.println("  <div class='empty-state'>");
                out.println("    <div class='empty-icon'>📚</div>");
                out.println("    <p>You haven't enrolled in any courses yet. <a href='courses' style='color:var(--gold);'>Browse courses →</a></p>");
                out.println("  </div>");
                out.println("</td></tr>");
            }

            out.println("</tbody></table></div>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<div class='alert alert-error'>⚠️ Error loading your courses. Please try again.</div>");
        }

        out.println("</div></div></body></html>");
    }

    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}