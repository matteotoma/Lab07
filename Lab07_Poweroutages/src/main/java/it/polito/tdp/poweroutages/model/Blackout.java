package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Blackout implements Comparable<Blackout>{
	private int id;
	private int customersAffected;
	private LocalDateTime dataBegin;
	private LocalDateTime dataEnd;
	
	public Blackout(int id, int customersAffected, LocalDateTime dataBegin, LocalDateTime localEnd) {
		super();
		this.id = id;
		this.customersAffected = customersAffected;
		this.dataBegin = dataBegin;
		this.dataEnd = localEnd;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomersAffected() {
		return customersAffected;
	}

	public void setCustomersAffected(int customersAffected) {
		this.customersAffected = customersAffected;
	}

	public LocalDateTime getDataBegin() {
		return dataBegin;
	}

	public void setDataBegin(LocalDateTime dataBegin) {
		this.dataBegin = dataBegin;
	}

	public LocalDateTime getDataEnd() {
		return dataEnd;
	}

	public void setDataEnd(LocalDateTime dataEnd) {
		this.dataEnd = dataEnd;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Blackout other = (Blackout) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int compareTo(Blackout b) {
		return this.dataBegin.compareTo(b.dataBegin);
	}

}
