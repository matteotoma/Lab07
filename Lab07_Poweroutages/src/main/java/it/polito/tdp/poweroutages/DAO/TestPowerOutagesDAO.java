package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.util.List;

import it.polito.tdp.poweroutages.model.Blackout;
import it.polito.tdp.poweroutages.model.Nerc;

public class TestPowerOutagesDAO {

	public static void main(String[] args) {
		
		try {
			Connection connection = ConnectDB.getConnection();
			connection.close();
			System.out.println("Connection Test PASSED");
			
			PowerOutageDAO dao = new PowerOutageDAO() ;
			
			System.out.println(dao.getNercList()) ;
			System.out.println("------------------");
			List<Blackout> l = dao.getBlackoutList(new Nerc(8));
			for(Blackout b: l)
				System.out.println(b.getId()+" "+b.getDataBegin()+" "+b.getDataEnd()+"\n");

		} catch (Exception e) {
			System.err.println("Test FAILED");
		}

	}

}
