

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

@WebServlet("/CreateServlet")
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Make PrintWriter ready
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
		//Read HTML data
        String num = request.getParameter("num");
        String name = request.getParameter("name");
        String balance = request.getParameter("balance");
        
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/ujjwal";
        
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, "root", "");

            String query = "insert into account values(?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, num);
            ps.setString(2, name);
            ps.setString(3, balance);

            int count = ps.executeUpdate();
            if (count > 0) {
                out.println("<h4 style='color:green'> Record inserted Successful </h4>");
                RequestDispatcher rd = request.getRequestDispatcher("Success.html");
                rd.include(request, response);
            }
            else {
            	out.println("<h4 style='color:green'> Error : Record not inserted Successful </h4>");
            	
            }

        } catch (Exception e) {
            out.println("<h4 style='color:red'>Error: " + e.getMessage() + "</h4>");
        }
		/*
		 * try{
		 * connect to database
		 * prepare query
		 * Execute
		 * if(succes)
		 * {
		 * 		Record inserted - Do any other Transactions
		 * 		include(Success.html)
		 * }
		 * else
		 * {
		 * Insertion failed -Try again
		 * include(Create.html)
		 * }
		 * }
		 * catch()
		 * {
		 * 		print Error Message
		 * }
		 */

	}

}
