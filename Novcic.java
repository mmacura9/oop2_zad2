package igra;

import java.awt.*;

public class Novcic extends Figura {

	private Color boja = Color.YELLOW;
	
	public Novcic(Polje polje) {
		super(polje);
	}

	@Override
	public void pomeri(Polje polje) {

	}

	@Override
	public void iscrtaj() {
		Graphics g = polje.getGraphics();
		int x = polje.getWidth()/4;
		int y = polje.getHeight()/4;
		int width = polje.getWidth()/2;
		int height = polje.getHeight()/2;
		g.setColor(boja);
		g.fillOval(x, y, width, height);
	}

}
