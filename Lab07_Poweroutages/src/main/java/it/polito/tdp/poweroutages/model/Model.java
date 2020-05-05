package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	private PowerOutageDAO podao;
	private List<Blackout> eventi;
	private List<Blackout> best;
	private int customersAffected;
	
	public Model() {
		podao = new PowerOutageDAO();
		best = null;
		customersAffected = 0;
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<Blackout> trovaEventi(Nerc nerc, int years, int hours) {
		eventi = new ArrayList<>(podao.getBlackoutList(nerc));
		List<Blackout> parziale = new ArrayList<>();
		cerca(parziale, 0, years, hours);
		return best;
	}

	private void cerca(List<Blackout> parziale, int livello, int years, int hours) {
		// casi terminali
		if(livello == eventi.size() || calcolaOre(parziale) == hours) {
			int clienti = sommaClienti(parziale);
			if(clienti > customersAffected) {
				best = new ArrayList<>(parziale);
				customersAffected = clienti;
			}
			return;
		}
		
		// generazione problemi
		if(aggiuntaValida(parziale, years, hours, eventi.get(livello))) {
			parziale.add(eventi.get(livello));
			cerca(parziale, livello+1, years, hours);
			parziale.remove(eventi.get(livello));
		}
		cerca(parziale, livello+1, years, hours);
	}

	public int sommaClienti(List<Blackout> parziale) {
		int somma = 0;
		for(Blackout b: parziale)
			somma += b.getCustomersAffected();
		return somma;
	}

	private boolean aggiuntaValida(List<Blackout> parziale, int years, int hours, Blackout b) {
		// controllo ore di disservizio
		long totaleOre = calcolaOre(parziale);
		long oreRichieste = (Duration.between(b.getDataBegin(), b.getDataEnd())).getSeconds() / 3600;
		if((oreRichieste + totaleOre) > hours)
		  	return false;
				
		// controllo differenza anni
		for(Blackout temp: parziale)
			if(Math.abs(temp.getDataBegin().getYear() - b.getDataBegin().getYear()) > years)
				return false;
		return true;
	}

	public long calcolaOre(List<Blackout> parziale) {
		long somma = 0;
		for(Blackout b: parziale)
			somma += (Duration.between(b.getDataBegin(), b.getDataEnd())).getSeconds() / 3600; 
		return somma;
	}

}
