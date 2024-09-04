import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/ujjwal";
        String user = "root";
        String password = "";

        String num = request.getParameter("num");
        double amount = Double.parseDouble(request.getParameter("amount"));

        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, password);
            String query = "UPDATE account SET balance = balance + ? WHERE num = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setDouble(1, amount);
            pstmt.setString(2, num);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                out.println("<h3 style='color:green;'>Deposit successful!</h3>");
                out.println("<p>Amount deposited: " + amount + "</p>");
            } else {
                out.println("<h3 style='color:red;'>Failed to deposit money. Please check the account number.</h3>");
            }

            con.close();
        } catch (Exception e) {
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
