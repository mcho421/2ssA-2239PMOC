package cs9322.rest.marketdata.data;


import java.io.BufferedReader;
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

import cs9322.rest.marketdata.model.MarketData;

public class Jsefa {

	public static String convert_csv(String url) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
        CsvConfiguration config = new CsvConfiguration();
        config.setLineFilter(new HeaderAndFooterFilter(1, false, true));
        config.setFieldDelimiter(',');
		Deserializer deserializer = CsvIOFactory.createFactory(config,MarketData.class).createDeserializer();
		XmlSerializer serializer = XmlIOFactory.createFactory(MarketData.class).createSerializer();
		Reader reader = new BufferedReader(new FileReader(url));
		Writer writer = new PrintWriter("/Users/lan/Desktop/0.xml", "UTF-8");
		serializer.open(writer);
		serializer.getLowLevelSerializer().writeXmlDeclaration("1.0", "ISO-8859-1");
		serializer.getLowLevelSerializer().writeStartElement(QName.create("MarketDatas"));
		deserializer.open(reader);
		while (deserializer.hasNext()) {
		    MarketData m = deserializer.next();
		    serializer.write(m);
		    System.out.println(m.getA_price());
		}
		serializer.getLowLevelSerializer().writeEndElement();
		serializer.close(true);
		deserializer.close(true);	
		
		return "";
	}
	public static List<MarketData> deserialize_xml(String url) throws FileNotFoundException {
		List<MarketData> result =  new ArrayList<MarketData>();
		XmlDeserializer deserializer = XmlIOFactory.createFactory(MarketData.class).createDeserializer();
		Reader reader = new BufferedReader(new FileReader(url));
		deserializer.open(reader);
		while(deserializer.hasNext()) {
			MarketData m = deserializer.next();
			result.add(m);
		}
		deserializer.close(true);
		return result;
	}
	
}


