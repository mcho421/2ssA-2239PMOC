package cs9322.rest.marketdata.model;
import javax.xml.bind.annotation.XmlRootElement;

import org.jsefa.csv.annotation.*;
import org.jsefa.xml.annotation.XmlDataType;

@XmlRootElement()
@CsvDataType()
@XmlDataType(defaultElementName = "Data")
public class Data {
	@javax.xml.bind.annotation.XmlElement
	@CsvField(pos = 1)
	@org.jsefa.xml.annotation.XmlElement(pos = 1)
	String ric;
	@javax.xml.bind.annotation.XmlElement
	@CsvField(pos = 2)
	@org.jsefa.xml.annotation.XmlElement(pos = 2)
	String date;
	@javax.xml.bind.annotation.XmlElement
	@CsvField(pos = 3)
	@org.jsefa.xml.annotation.XmlElement(pos = 3)
	String time;
	@javax.xml.bind.annotation.XmlElement
	@CsvField(pos = 4)
	@org.jsefa.xml.annotation.XmlElement(pos = 4)
	String GMT;
	@javax.xml.bind.annotation.XmlElement
	@CsvField(pos = 5)
	@org.jsefa.xml.annotation.XmlElement(pos = 5)
	String type;
	@javax.xml.bind.annotation.XmlElement
	@CsvField(pos = 6)
	@org.jsefa.xml.annotation.XmlElement(pos = 6)
	String price;
	@javax.xml.bind.annotation.XmlElement
	@CsvField(pos = 7)
	@org.jsefa.xml.annotation.XmlElement(pos = 7)
	String volume;
	@javax.xml.bind.annotation.XmlElement
	@CsvField(pos = 8)
	@org.jsefa.xml.annotation.XmlElement(pos = 8)
	String b_price;
	@javax.xml.bind.annotation.XmlElement
	@CsvField(pos = 9)
	@org.jsefa.xml.annotation.XmlElement(pos = 9)
	String b_size;
	@javax.xml.bind.annotation.XmlElement
	@CsvField(pos = 10)
	@org.jsefa.xml.annotation.XmlElement(pos = 10)
	String a_price;
	@javax.xml.bind.annotation.XmlElement
	@CsvField(pos = 11)
	@org.jsefa.xml.annotation.XmlElement(pos = 11)
	String a_size;
	public String getRic() {
		return ric;
	}
	public String getDate() {
		return date;
	}
	public String getTime() {
		return time;
	}
	public String getGMT() {
		return GMT;
	}
	public String getType() {
		return type;
	}
	public String getPrice() {
		return price;
	}
	public String getVolume() {
		return volume;
	}
	public String getB_price() {
		return b_price;
	}
	public String getB_size() {
		return b_size;
	}
	public String getA_price() {
		return a_price;
	}
	public String getA_size() {
		return a_size;
	}
	
}
