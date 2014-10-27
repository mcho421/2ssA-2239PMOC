package cs9322.rest.marketdata.model;
import javax.xml.bind.annotation.XmlRootElement;

import org.jsefa.csv.annotation.*;
import org.jsefa.xml.annotation.XmlDataType;
import org.jsefa.xml.annotation.XmlElement;

@XmlRootElement
@CsvDataType()
@XmlDataType(defaultElementName = "MarketDatas")
public class MarketData {
	@CsvField(pos = 1)
	@XmlElement(pos = 1)
	String ric;
	@CsvField(pos = 2)
	@XmlElement(pos = 2)
	String date;
	@CsvField(pos = 3)
	@XmlElement(pos = 3)
	String time;
	@CsvField(pos = 4)
	@XmlElement(pos = 4)
	String GMT;
	@CsvField(pos = 5)
	@XmlElement(pos = 5)
	String type;
	@CsvField(pos = 6)
	@XmlElement(pos = 6)
	String price;
	@CsvField(pos = 7)
	@XmlElement(pos = 7)
	String volume;
	@CsvField(pos = 8)
	@XmlElement(pos = 8)
	String b_price;
	@CsvField(pos = 9)
	@XmlElement(pos = 9)
	String b_size;
	@CsvField(pos = 10)
	@XmlElement(pos = 10)
	String a_price;
	@CsvField(pos = 11)
	@XmlElement(pos = 11)
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
