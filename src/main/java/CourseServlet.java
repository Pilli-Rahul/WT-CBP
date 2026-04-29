import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/courses")
public class CourseServlet extends HttpServlet {

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
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        String role   = (String) session.getAttribute("role");
        String search = req.getParameter("search");
        String msg    = req.getParameter("msg");
        if (search == null) search = "";

        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();

        out.println("<!DOCTYPE html><html lang='en'><head>");
        out.println("<meta charset='UTF-8'><meta name='viewport' content='width=device-width,initial-scale=1'>");
        out.println("<title>Courses — LearnHub</title>");
        out.println("<link rel='stylesheet' href='style.css'>");
        out.println("</head><body><div class='app-layout'>");

        out.println(nav(role, "courses"));

        out.println("<div class='page-content'>");

        // page header
        out.println("<div class='page-header'>");
        out.println("  <h2>Explore Courses</h2>");
        out.println("  <p>Find and enroll in courses that match your goals</p>");
        out.println("</div>");

        // success message after course added
        if ("added".equals(msg)) {
            out.println("<div class='alert alert-success' style='margin-bottom:24px;'>✅ Course published successfully!</div>");
        }

        // search bar
        out.println("<form action='courses' method='get' class='search-bar'>");
        out.println("  <input type='text' name='search' placeholder='Search courses by title...' value='" + escapeHtml(search) + "'>");
        out.println("  <button type='submit'>Search</button>");
        out.println("</form>");

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM courses WHERE title LIKE ? ORDER BY id DESC"
            );
            ps.setString(1, "%" + search + "%");
            ResultSet rs = ps.executeQuery();

            out.println("<div class='courses-grid'>");

            boolean found = false;
            int delay = 0;
            while (rs.next()) {
                found = true;
                int id = rs.getInt("id");
                String title = rs.getString("title");
                double price = rs.getDouble("price");

                out.println("<div class='course-card' style='animation-delay:" + (delay * 60) + "ms'>");
                out.println("  <div class='course-card-id'>Course #" + id + "</div>");
                out.println("  <div class='course-card-title'>" + escapeHtml(title) + "</div>");
                out.println("  <div class='course-card-footer'>");
                out.println("    <span class='course-price'>₹" + String.format("%.0f", price) + "</span>");
                out.println("    <a href='payment?courseId=" + id + "' class='btn-enroll'>Enroll Now</a>");
                out.println("  </div>");
                out.println("</div>");
                delay++;
            }

            if (!found) {
                out.println("<div class='empty-state' style='grid-column:1/-1'>");
                out.println("  <div class='empty-icon'>🔍</div>");
                out.println("  <p>No courses found for <strong>\"" + escapeHtml(search) + "\"</strong></p>");
                out.println("</div>");
            }

            out.println("</div>"); // courses-grid

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<div class='alert alert-error'>⚠️ Error loading courses. Please try again.</div>");
        }

        out.println("</div></div></body></html>"); // page-content, app-layout
    }

    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}