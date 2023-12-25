package database;

public class FacturePharmacie {
	
	private String code;
    private String nomClient;
    private double montantVerse;
    
    
	public FacturePharmacie(String code, String nomClient, double montantVerse) {
		this.code = code;
		this.nomClient = nomClient;
		this.montantVerse = montantVerse;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getNomClient() {
		return nomClient;
	}


	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}


	public double getMontantVerse() {
		return montantVerse;
	}


	public void setMontantVerse(double montantVerse) {
		this.montantVerse = montantVerse;
	}


	@Override
	public String toString() {
		return "FacturePharmacie [code=" + code + ", nomClient=" + nomClient + ", montantVerse=" + montantVerse + "]";
	}
    
    

}
