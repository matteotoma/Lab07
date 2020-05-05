package it.polito.tdp.poweroutages.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		System.out.println(model.getNercList());
		
		System.out.println("--------------------------");
		List<Blackout> list = model.trovaEventi(new Nerc(3), 4, 200);
		long totOre = model.calcolaOre(list);
		int clienti = model.sommaClienti(list);
		System.out.println(totOre + " "+clienti);
		for(Blackout b: list) {
			System.out.println(b.getDataBegin()+" "+b.getDataEnd()+"\n");
		}
	}

}
