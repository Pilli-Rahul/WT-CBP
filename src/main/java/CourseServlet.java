import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/courses")
public class CourseServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM courses");

            out.println("<h2>Available Courses</h2>");
            out.println("<a href='logout.jsp'>Logout</a><br><br>");

            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Title</th><th>Price</th><th>Action</th></tr>");

            while (rs.next()) {

                int id = rs.getInt("id");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + rs.getString("title") + "</td>");
                out.println("<td>" + rs.getDouble("price") + "</td>");

                out.println("<td><a href='enroll?courseId=" + id + "'>Enroll</a></td>");
                out.println("</tr>");
            }

            out.println("</table>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}