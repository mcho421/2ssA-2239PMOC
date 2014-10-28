package cs9322.rest.marketdata.init;
import java.io.File;

public enum Constants {
	instance;

	public String xmlFolderPath;
	public String xsltFolderPath;
	public String convertedXmlFolderPath;
	public String totalPriceXsltPath;
	public String filterXsltPath;
	
	public final String filterXsltName = "marketdatafilter.xsl";
	public final String totalPriceXsltName = "marketdatafiltertotalprice.xsl";


	private Constants() {
		xmlFolderPath = System.getProperty("catalina.base")+"/xml";
		xsltFolderPath = System.getProperty("catalina.base")+"/xslt";
        convertedXmlFolderPath = System.getProperty("catalina.base")+"/xml/converted";
        totalPriceXsltPath = xsltFolderPath + "/" + totalPriceXsltName;
		filterXsltPath = xsltFolderPath + "/" + filterXsltName;
        new File(xmlFolderPath).mkdirs();
        new File(xsltFolderPath).mkdirs();
        new File(convertedXmlFolderPath).mkdirs();
	}

}
