import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/admin-add-course")
public class AdminServlet extends HttpServlet {

    // POST: Save new course from admin form
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        HttpSession session = req.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            res.sendRedirect("login.jsp");
            return;
        }

        String title = req.getParameter("title");
        String priceStr = req.getParameter("price");

        if (title == null || title.trim().isEmpty() || priceStr == null) {
            res.sendRedirect("admin-add-course.jsp?err=empty");
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO courses(title, price) VALUES(?,?)"
            );
            ps.setString(1, title.trim());
            ps.setDouble(2, price);
            ps.executeUpdate();

            res.sendRedirect("courses?msg=added");

        } catch (NumberFormatException e) {
            res.sendRedirect("admin-add-course.jsp?err=price");
        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("admin-add-course.jsp?err=db");
        }
    }
}