package igra;

import java.awt.*;

public class Tenk extends Figura implements Runnable {

	private Color boja = Color.BLACK;
	private Thread nit;
	private boolean pokrenuta = false;

	public Tenk(Polje polje) {
		super(polje);
	}

	@Override
	public void pomeri(Polje polje) {
		this.polje = polje;
	}

	@Override
	public synchronized void iscrtaj() {
		Graphics g = polje.getGraphics();
		g.setColor(boja);

		g.drawLine(0, 0, polje.getWidth(), polje.getHeight());
		g.drawLine(0, polje.getHeight(), polje.getWidth(), 0);

	}

	private Polje generisiPolje() {
		int x = (int) (Math.random() * 4);
		if (x == 0)
			return polje.pomeraj(1, 0);
		if (x == 1)
			return polje.pomeraj(-1, 0);
		if (x == 2)
			return polje.pomeraj(0, 1);
		return polje.pomeraj(0, -1);
	}

	@Override
	public void run() {

		try {
			while (!Thread.interrupted()) {
				Thread.sleep(500);
				Polje x = null;
				while (x == null || !x.mozeFig(this)) {
					x = generisiPolje();
				}
				Polje y = polje;
				pomeri(x);
				y.repaint();
				polje.repaint();
			}
		}
		catch (InterruptedException g) { }
	}

	public void pokreni() {
		if (pokrenuta == false) {
			pokrenuta = true;
			nit = new Thread(this);
			nit.start();
		}
	}

	public void zaustavi() {
		if (pokrenuta == true) {
			pokrenuta = false;
			nit.interrupt();
		}
	}

}
