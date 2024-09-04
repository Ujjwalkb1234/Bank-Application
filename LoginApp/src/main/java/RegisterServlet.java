import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        String uname = request.getParameter("username");
        String pwd = request.getParameter("password");
        String confirm = request.getParameter("cnfm");

        if (pwd != null && pwd.equals(confirm)) {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/ujjwal";
            try {
                Class.forName(driver);
                Connection con = DriverManager.getConnection(url, "root", "");

                String query = "insert into register values(?,?)";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, uname);
                ps.setString(2, pwd);

                int count = ps.executeUpdate();
                if (count > 0) {
                    out.println("<h4 style='color:green'>Registered Successfully - Login Now</h4>");
                    RequestDispatcher rd = request.getRequestDispatcher("Login.html");
                    rd.include(request, response);
                }

            } catch (Exception e) {
                out.println("<h4 style='color:red'>Error: " + e.getMessage() + "</h4>");
                RequestDispatcher rd = request.getRequestDispatcher("Login.html");
                rd.include(request, response);
            }
        } else {
            out.println("<h4 style='color:red'>Error : Password Mismatch </h4>");
            RequestDispatcher rd = request.getRequestDispatcher("Register.html");
            rd.include(request, response);
        }
    }
}
