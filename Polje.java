package igra;

import java.awt.*;
import java.awt.event.*;

public abstract class Polje extends Canvas {

	protected Color boja;
	protected Mreza mreza;
	private int i = 0;

	public Polje(Mreza mreza) {
		this.mreza = mreza;
		Polje p = this;

		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				mreza.obavesti(p);
			}
		});

	}

	public Mreza getMreza() {
		return mreza;
	}

	public abstract void iscrtaj();

	public abstract boolean mozeFig(Figura f);

	public int[] pozicija() {
		return mreza.pozPolja(this);
	}

	public Polje pomeraj(int y, int x) {
		return mreza.pomeraj(this, y, x);
	}

	@Override
	public void paint(Graphics g) {
		Igrac ig = new Igrac(this);
		if (mreza.getIgrac() != null && mreza.getIgrac().getPolje() == this)
			mreza.getIgrac().iscrtaj();
		if (mreza.getTenkovi().contains(ig))
			mreza.getTenkovi().get(mreza.getTenkovi().indexOf(ig)).iscrtaj();
		if (mreza.getNovcici().contains(ig))
			mreza.getNovcici().get(mreza.getNovcici().indexOf(ig)).iscrtaj();
	}

}
