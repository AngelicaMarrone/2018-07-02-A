package it.polito.tdp.extflightdelays.model;

public class Adiacenze {
	
	private int id1;
	private int id2;
	private double distanza;
	
	public Adiacenze(int id1, int id2, double distanza) {
		super();
		this.id1 = id1;
		this.id2 = id2;
		this.distanza = distanza;
	}

	public int getId1() {
		return id1;
	}

	public void setId1(int id1) {
		this.id1 = id1;
	}

	public int getId2() {
		return id2;
	}

	public void setId2(int id2) {
		this.id2 = id2;
	}

	public double getDistanza() {
		return distanza;
	}

	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(distanza);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id1;
		result = prime * result + id2;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adiacenze other = (Adiacenze) obj;
		if (Double.doubleToLongBits(distanza) != Double.doubleToLongBits(other.distanza))
			return false;
		if (id1 != other.id1)
			return false;
		if (id2 != other.id2)
			return false;
		return true;
	}
	
}