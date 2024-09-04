import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        String uname = request.getParameter("username");
        String pwd = request.getParameter("password");

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/ujjwal";

        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, "root", "");

            String query = "select * from register where username=? and password=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, uname);
            ps.setString(2, pwd);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                RequestDispatcher rd = request.getRequestDispatcher("Success.html");
                rd.include(request, response);

                
            } else {
                out.println("<h4 style='color:red'>Login Failed - Invalid username or password</h4>");
                RequestDispatcher rd = request.getRequestDispatcher("Login.html");
                rd.include(request, response);
            }

        } catch (Exception e) {
            out.println("<h4 style='color:red'>Error: " + e.getMessage() + "</h4>");
            RequestDispatcher rd = request.getRequestDispatcher("Login.html");
            rd.include(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect to login page for GET requests
        response.sendRedirect("Login.html");
    }
}
