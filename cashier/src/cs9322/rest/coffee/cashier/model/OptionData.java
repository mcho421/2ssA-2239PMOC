package cs9322.rest.coffee.cashier.model;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class OptionData {
	
	@XmlElement
	String get;
	@XmlElement
	String post;
	@XmlElement
	String put;
	@XmlElement
	String delete;
	
	public String getGet() {
		return get;
	}
	public void setGet() {
		this.get = "GET";
	}
	public String getPost() {
		return post;
	}
	public void setPost() {
		this.post = "POST";
	}
	public String getPut() {
		return put;
	}
	public void setPut() {
		this.put = "PUT";
	}
	public String getDelete() {
		return delete;
	}
	public void setDelete() {
		this.delete = "DELETE";
	}
}
