package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DTO {
	
	public static void addFacture(FactureVehicule f) {
		try {
            Connection conn = ConfigDB.getConnection();
            String insertQuery = "INSERT INTO facture_vehicule VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, f.getCode_facture());
            preparedStatement.setString(2, f.getDate_facture());
            preparedStatement.setString(3, f.getClient());
            preparedStatement.setString(4, f.getDesignation());
            preparedStatement.setInt(5, f.getQte());
            preparedStatement.setDouble(6, f.getPrix());
            preparedStatement.setDouble(7, f.getTotal());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public static ArrayList<FactureVehicule> getAllFactures() {
		ArrayList<FactureVehicule> factures = null;
		try {
        	Connection conn = ConfigDB.getConnection();
            String selectQuery = "SELECT * FROM facture_vehicule";
            PreparedStatement preparedStatement = conn.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            factures = new ArrayList<>();
            while (resultSet.next()) {
            	FactureVehicule fm = new FactureVehicule();
                fm.setCode_facture(resultSet.getString(1));
                fm.setDate_facture(resultSet.getString(2));
                fm.setClient(resultSet.getString(3));
                fm.setDesignation(resultSet.getString(4));
                fm.setQte(resultSet.getInt(5));
                fm.setPrix(resultSet.getDouble(6));
                fm.setTotal(resultSet.getDouble(7));
                factures.add(fm);
            }
            resultSet.close();
            preparedStatement.close();
            return factures;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return factures;
    }

}
