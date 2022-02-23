package igra;

import java.awt.*;

public class Igrac extends Figura {

	private Color boja = Color.RED;

	public Igrac(Polje polje) {
		super(polje);

	}

	@Override
	public void pomeri(Polje polje) {
		Polje x = this.polje;
		if (polje != null && polje.mozeFig(this))
			this.polje = polje;
		x.repaint();
		this.polje.repaint();
	}

	@Override
	public void iscrtaj() {
		Graphics g = polje.getGraphics();
		g.setColor(boja);
		g.drawLine(polje.getWidth() / 2, 0, polje.getWidth() / 2, polje.getHeight());
		g.drawLine(0, polje.getHeight() / 2, polje.getWidth(), polje.getHeight() / 2);
	}

}
