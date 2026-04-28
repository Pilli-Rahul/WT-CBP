import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/courses")
public class CourseServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM courses");

            out.println("<h2>Available Courses</h2>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Title</th><th>Price</th><th>Action</th></tr>");

            while (rs.next()) {
                int id = rs.getInt("id");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + rs.getString("title") + "</td>");
                out.println("<td>" + rs.getDouble("price") + "</td>");

                // Enroll button
                out.println("<td><a href='enroll?courseId=" + id + "'>Enroll</a></td>");
                out.println("</tr>");
            }

            out.println("</table>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("ERROR: " + e);
        }
    }
}