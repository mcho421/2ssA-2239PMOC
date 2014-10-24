package cs9322.rest.coffee.cashier.model;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class OrderBean {
	
	String type;
	String cost;
	String additions;
	String payment_type;
	String card_details;
	boolean paid;
	boolean ready;
	boolean released;
	public OrderBean(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getAdditions() {
		return additions;
	}
	public void setAdditions(String additions) {
		this.additions = additions;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getCard_details() {
		return card_details;
	}
	public void setCard_details(String card_details) {
		this.card_details = card_details;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public boolean isReady() {
		return ready;
	}
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	public boolean isReleased() {
		return released;
	}
	public void setReleased(boolean released) {
		this.released = released;
	}
}
