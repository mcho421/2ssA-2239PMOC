package cs9322.rest.marketdata.init;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.Path;

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
    }

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext context = config.getServletContext();
		String webInfFilterXslt = context.getRealPath("/WEB-INF/" + Constants.instance.filterXsltName);
		String webInfTotalPriceXslt = context.getRealPath("/WEB-INF/" + Constants.instance.totalPriceXsltName);
        System.out.println("WEB-INF = " + webInfFilterXslt);
        
        try {
			copyFile(new File(webInfFilterXslt), new File(Constants.instance.filterXsltPath));
			copyFile(new File(webInfTotalPriceXslt), new File(Constants.instance.totalPriceXsltPath));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not copy xslt files.");
		}

	}
	
	public static void copyFile(File sourceFile, File destFile) throws IOException {
		// taken from:
		// http://stackoverflow.com/questions/106770/standard-concise-way-to-copy-a-file-in-java
		
	    if(!destFile.exists()) {
	        destFile.createNewFile();
	    }

	    FileChannel source = null;
	    FileChannel destination = null;

	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();
	        destination.transferFrom(source, 0, source.size());
	    }
	    finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
	}

}
