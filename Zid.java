package igra;

import java.awt.*;

public class Zid extends Polje {
	
	public Zid(Mreza mreza) {
		super(mreza);
		boja = Color.LIGHT_GRAY;
		setBackground(boja);
	}
	
	@Override
	public boolean mozeFig(Figura f) {
		return false;
	}

	@Override
	public void iscrtaj() {
		Graphics g = getGraphics();
		g.setColor(boja);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
