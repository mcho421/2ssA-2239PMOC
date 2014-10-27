package cs9322.rest.marketdata.data;

import java.io.File;
import java.io.StringWriter;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class FilterXml {
	
	private static final String totalPriceXsltPath = "/Users/mathew/Developer/uni/postgrad/soa/xslexample/marketdatafiltertotalprice.xsl";
	private static final String filterXsltPath = "/Users/mathew/Developer/uni/postgrad/soa/xslexample/marketdatafilter.xsl";
	
	public static void filterByType(String in_path, String out_path, String type) throws TransformerException {
		TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File(filterXsltPath));
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File(in_path));
        transformer.setParameter("type", type);
        transformer.transform(text, new StreamResult(new File(out_path)));
	}

	public static String totalPrice(String in_path, String type) throws TransformerException {
		TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File(totalPriceXsltPath));
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File(in_path));
        transformer.setParameter("type", type);
        StringWriter outWriter = new StringWriter();
        transformer.transform(text, new StreamResult(outWriter));
        StringBuffer sb = outWriter.getBuffer();
        String totalPrice = sb.toString();
        System.out.println(totalPrice);
        return totalPrice;
	}

}
