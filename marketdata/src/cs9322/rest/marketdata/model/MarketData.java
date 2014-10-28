package cs9322.rest.marketdata.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
@XmlRootElement(name = "MarketData")
@XmlAccessorType(XmlAccessType.FIELD)
public class MarketData {
	@XmlElement(name = "Data")
	List<Data> marketdata = new ArrayList<Data>();

	public List<Data> getMarketdata() {
		return marketdata;
	}

	public void setMarketdata(List<Data> marketdata) {
		this.marketdata = marketdata;
	}
	
}
