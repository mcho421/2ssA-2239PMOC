

import java.io.File;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import cs9322.rest.marketdata.data.Jdbc;

/**
 * Servlet implementation class InitServlet
 */
@WebServlet("/InitServlet")
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws SQLException 
     * @see HttpServlet#HttpServlet()
     */
    public InitServlet() throws SQLException {
        super();
        Jdbc.init_db();
        // TODO Auto-generated constructor stub
    }

}
