package cs9322.rest.marketdata.data;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsefa.Deserializer;
import org.jsefa.common.lowlevel.filter.HeaderAndFooterFilter;
import org.jsefa.csv.CsvIOFactory;
import org.jsefa.csv.config.CsvConfiguration;
import org.jsefa.xml.XmlDeserializer;
import org.jsefa.xml.XmlIOFactory;
import org.jsefa.xml.XmlSerializer;
import org.jsefa.xml.namespace.QName;

import cs9322.rest.marketdata.init.Constants;
import cs9322.rest.marketdata.model.Data;

public class Jsefa {

	public static String convert_csv(String url) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        CsvConfiguration config = new CsvConfiguration();
        File f = new File(url);
        String fileName = f.getName();
        fileName = fileName.replaceFirst("[.][^.]+$", "")+".xml";
        String new_location = Constants.instance.xmlFolderPath + "/" + fileName;
        config.setLineFilter(new HeaderAndFooterFilter(1, false, true));
        config.setFieldDelimiter(',');
		Deserializer deserializer = CsvIOFactory.createFactory(config,Data.class).createDeserializer();
		XmlSerializer serializer = XmlIOFactory.createFactory(Data.class).createSerializer();
		Reader reader = new BufferedReader(new FileReader(url));
		Writer writer = new PrintWriter(new_location, "UTF-8");
		serializer.open(writer);
		serializer.getLowLevelSerializer().writeXmlDeclaration("1.0", "ISO-8859-1");
		serializer.getLowLevelSerializer().writeStartElement(QName.create("MarketData"));
		deserializer.open(reader);
		while (deserializer.hasNext()) {
		    Data m = deserializer.next();
		    serializer.write(m);
		    System.out.println(m.getA_price());
		}
		serializer.getLowLevelSerializer().writeEndElement();
		serializer.close(true);
		deserializer.close(true);	
		
		return new_location;
	}
	public static List<Data> deserialize_xml(String url) throws FileNotFoundException {
		List<Data> result =  new ArrayList<Data>();
		XmlDeserializer deserializer = XmlIOFactory.createFactory(Data.class).createDeserializer();
		Reader reader = new BufferedReader(new FileReader(url));
		deserializer.open(reader);
		while(deserializer.hasNext()) {
			Data m = deserializer.next();
			result.add(m);
		}
		deserializer.close(true);
		return result;
	}
	
}


