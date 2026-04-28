import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/testDB")
public class TestDB extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        Connection con = DBConnection.getConnection();

        if (con != null) {
            out.println("DB Connected ✅");
        } else {
            out.println("DB Failed ❌");
        }
    }
}