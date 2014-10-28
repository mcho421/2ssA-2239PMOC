package cs9322.rest.marketdata.init;
import java.io.File;

public enum Constants {
	instance;

	public String xmlFolderPath;
	public String convertedXmlFolderPath;

	private Constants() {
        xmlFolderPath = System.getProperty("catalina.home")+"/xml";
        convertedXmlFolderPath = System.getProperty("catalina.home")+"/xml/converted";
        new File(xmlFolderPath).mkdirs();
        new File(convertedXmlFolderPath).mkdirs();
	}

}
