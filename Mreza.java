package igra;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Mreza extends Panel implements Runnable {
	private Igra igra;
	private Polje[][] polja;
	private int dim = 17;
	private Igrac igrac;
	private ArrayList<Novcic> novcici = new ArrayList<>();
	private ArrayList<Tenk> tenkovi = new ArrayList<>();
	private int brNovcica = 15;
	private int brTenkova = 5;
	private Thread nit;
	private int poeni = 0;
	private Label p;
	private Checkbox radioTrava, radioZid;

	public Mreza(int dim, Igra igra) {
		super();
		setLayout(new GridLayout(dim, dim));
		this.igra = igra;
		this.dim = dim;
		dodajPolja();
		dodajListener();
	}

	public Mreza(Igra igra) {
		super();
		setLayout(new GridLayout(dim, dim));
		this.igra = igra;
		dodajPolja();
		dodajListener();
	}

	private void dodajListener() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Polje x;
				if (igrac == null)
					return;
				switch (e.getKeyCode()) {
				case KeyEvent.VK_W:
					x = igrac.getPolje();
					x = x.pomeraj(-1, 0);
					igrac.pomeri(x);
					break;
				case KeyEvent.VK_S:
					x = igrac.getPolje();
					x = x.pomeraj(1, 0);
					igrac.pomeri(x);
					break;
				case KeyEvent.VK_A:
					x = igrac.getPolje();
					x = x.pomeraj(0, -1);
					igrac.pomeri(x);
					break;
				case KeyEvent.VK_D:
					x = igrac.getPolje();
					x = x.pomeraj(0, 1);
					igrac.pomeri(x);
					break;
				}
			}
		});
	}

	public void setBrNovcica(int brNovcica) {
		this.brNovcica = brNovcica;
		brTenkova = brNovcica / 3;
		dodajFigure();
	}

	public synchronized Igrac getIgrac() {
		return igrac;
	}

	public synchronized ArrayList<Novcic> getNovcici() {
		return novcici;
	}

	public synchronized ArrayList<Tenk> getTenkovi() {
		return tenkovi;
	}

	public void postaviReference(Label l, Checkbox t, Checkbox z) {
		p = l;
		radioTrava = t;
		radioZid = z;
	}

	private void refresujLabelu() {
		p.setText("Poeni: " + poeni);
	}

	public void obavesti(Polje p) {
		if (igra.getRezimIzmena() == false)
			return;
		int[] poz = pozPolja(p);

		remove(polja[poz[0]][poz[1]]);

		if (radioTrava.getState() == true && !(p instanceof Trava))
			polja[poz[0]][poz[1]] = new Trava(this);
		if (radioZid.getState() == true && !(p instanceof Zid))
			polja[poz[0]][poz[1]] = new Zid(this);

		add(polja[poz[0]][poz[1]], poz[0] * dim + poz[1]);

		revalidate();

	}

	private void dodajIgraca() {
		int x1 = (int) (Math.random() * dim);
		int y1 = (int) (Math.random() * dim);
		igrac = new Igrac(polja[y1][x1]);
		while (!polja[y1][x1].mozeFig(igrac)) {
			x1 = (int) (Math.random() * dim);
			y1 = (int) (Math.random() * dim);
			igrac = new Igrac(polja[y1][x1]);
		}
	}

	private void dodajNovcice() {
		for (int i = 0; i < brNovcica; i++) {
			int x = (int) (Math.random() * dim);
			int y = (int) (Math.random() * dim);
			Novcic n = new Novcic(polja[y][x]);

			while (!polja[y][x].mozeFig(n) || novcici.contains(n) || n.equals(igrac)) {
				x = (int) (Math.random() * dim);
				y = (int) (Math.random() * dim);
				n = new Novcic(polja[y][x]);
			}
			novcici.add(n);
		}
	}

	private void dodajTenkove() {
		for (int i = 0; i < brTenkova; i++) {
			int x = (int) (Math.random() * dim);
			int y = (int) (Math.random() * dim);
			Tenk t = new Tenk(polja[y][x]);

			while (!polja[y][x].mozeFig(t) || t.equals(igrac)) {
				x = (int) (Math.random() * dim);
				y = (int) (Math.random() * dim);
				t = new Tenk(polja[y][x]);
			}
			tenkovi.add(t);
		}
	}

	private void dodajFigure() {
		igrac = null;
		tenkovi.clear();
		novcici.clear();
		dodajIgraca();
		dodajNovcice();
		dodajTenkove();
	}

	private void dodajPolja() {
		polja = new Polje[dim][dim];
		int ukupno = dim * dim;
		int trave = (int) (ukupno * 0.8);
		int zidovi = ukupno - trave;
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (trave == 0)
					polja[i][j] = new Zid(this);
				else if (zidovi == 0)
					polja[i][j] = new Trava(this);
				else {
					double rand = Math.random();
					if (rand < 0.8) {
						polja[i][j] = new Trava(this);
						trave--;
					} else {
						polja[i][j] = new Zid(this);
						zidovi--;
					}
				}
				add(polja[i][j]);
			}
		}
	}

	public int[] pozPolja(Polje p) {
		int[] izlaz = new int[2];
		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++)
				if (polja[i][j] == p) {
					izlaz[0] = i;
					izlaz[1] = j;
					return izlaz;
				}
		return null;
	}

	public Polje pomeraj(Polje p, int y, int x) {
		int[] pozicija = pozPolja(p);
		if (pozicija[0] + y < 0 || pozicija[0] + y >= dim || pozicija[1] + x < 0 || pozicija[1] + x >= dim)
			return null;
		return polja[pozicija[0] + y][pozicija[1] + x];
	}

	private synchronized void provera() {
		if (novcici.contains(igrac)) {
			poeni++;
			refresujLabelu();
			novcici.remove(igrac);
			igrac.getPolje().repaint();
		}
		if (tenkovi.contains(igrac)) {
			Polje x = igrac.getPolje();
			igrac = null;
			x.repaint();
			zaustavi();
		}
		if (novcici.size() == 0)
			zaustavi();
	}

	@Override
	public void run() {

		try {
			int i = 0;
			while (!Thread.interrupted()) {
				Thread.sleep(40);
				provera();
			}
		} catch (InterruptedException g) {
		}
	}

	private boolean pokrenuta = false;

	public void pokreni() {
		if (pokrenuta == false) {
			dodajFigure();
			poeni = 0;
			for (int i = 0; i < dim; i++)
				for (int j = 0; j < dim; j++)
					polja[i][j].repaint();
			pokrenuta = true;
			nit = new Thread(this);
			nit.start();
			for (Tenk t : tenkovi)
				t.pokreni();
			requestFocus();
			refresujLabelu();
		}
	}
	
	public synchronized void zaustavi() {
		if (pokrenuta == true) {
			pokrenuta = false;
			for (Tenk t : tenkovi)
				t.zaustavi();
			nit.interrupt();
		}
	}

	public void resetuj() {
		novcici.clear();
		tenkovi.clear();
		igrac = null;
		for(int i=0; i<dim; i++)
			for(int j=0; j<dim; j++)
				polja[i][j].repaint();
	}
}
