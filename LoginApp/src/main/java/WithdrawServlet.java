import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/WithdrawServlet")
public class WithdrawServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/ujjwal";
        String user = "root";
        String password = "";

        String accountNumber = request.getParameter("num");
        double amount = Double.parseDouble(request.getParameter("amount"));

        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, password);
            String query = "SELECT balance FROM account WHERE account_number = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (balance >= amount) {
                    query = "UPDATE account SET balance = balance - ? WHERE num = ?";
                    pstmt = con.prepareStatement(query);
                    pstmt.setDouble(1, amount);
                    pstmt.setString(2, accountNumber);

                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        out.println("<h3 style='color:green;'>Withdrawal successful!</h3>");
                        out.println("<p>Amount withdrawn: $" + amount + "</p>");
                    } else {
                        out.println("<h3 style='color:red;'>Failed to withdraw money. Please try again later.</h3>");
                    }
                } else {
                    out.println("<h3 style='color:red;'>Insufficient balance to withdraw money.</h3>");
                }
            } else {
                out.println("<h3 style='color:red;'>No account found for the provided account number.</h3>");
            }

            con.close();
        } catch (Exception e) {
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
