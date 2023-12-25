package database;

import java.io.Serializable;

public class FactureVehicule implements Serializable {

	private String code_facture;
	private String date_facture;
	private String client;
	private String designation;
	private int qte;
	private double prix;
	private double total;

	public FactureVehicule(String code_facture, String date_facture, String client, String designation, int qte,
			double prix, double total) {
		this.code_facture = code_facture;
		this.date_facture = date_facture;
		this.client = client;
		this.designation = designation;
		this.qte = qte;
		this.prix = prix;
		this.total = total;
	}

	public FactureVehicule() {
	}

	public String getCode_facture() {
		return code_facture;
	}

	public void setCode_facture(String code_facture) {
		this.code_facture = code_facture;
	}

	public String getDate_facture() {
		return date_facture;
	}

	public void setDate_facture(String date_facture) {
		this.date_facture = date_facture;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getQte() {
		return qte;
	}

	public void setQte(int qte) {
		this.qte = qte;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "FactureModel [code_facture=" + code_facture + ", date_facture=" + date_facture + ", client=" + client
				+ ", designation=" + designation + ", qte=" + qte + ", prix=" + prix + ", total=" + total + "]";
	}

}
