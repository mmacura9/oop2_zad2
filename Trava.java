package igra;

import java.awt.*;

public class Trava extends Polje {
	
	public Trava(Mreza mreza) {
		super(mreza);
		boja = Color.GREEN;
		setBackground(boja);
	}

	@Override
	public boolean mozeFig(Figura f) {
		return true;
	}

	@Override
	public void iscrtaj() {
		
	}
}
