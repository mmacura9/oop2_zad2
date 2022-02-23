package igra;

public abstract class Figura {
	protected Polje polje;

	public Figura(Polje polje) {
		super();
		this.polje = polje;
	}

	public abstract void pomeri(Polje polje);

	public Polje getPolje() {
		return polje;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Figura))
			return false;
		Figura o = (Figura) obj;
		return o.polje == polje;
	}

	public abstract void iscrtaj();

}
