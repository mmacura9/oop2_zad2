package igra;

import java.awt.*;
import java.awt.event.*;

public class Igra extends Frame {

	private Mreza mreza = new Mreza(this);
	private MenuBar menibar;
	private Menu meni;
	private Panel panelDesno;
	private Checkbox trava;
	private Checkbox zid;
	private Panel panelDole;
	private TextField tekst;
	private Label poeni;
	private Button pocni;
	private boolean rezimIzmena = false;

	public Igra() {
		super("Igra");
		setSize(500, 500);
		
		dodajMeniBar();
		dodajPanele();
		dodajListenere();
		mreza.postaviReference(poeni, trava, zid);
		setVisible(true);
	}

	private void dodajMeniBar() {
		menibar = new MenuBar();
		meni = new Menu("Rezim");
		meni.add("Rezim izmena");
		meni.add("Rezim igranja");
		menibar.add(meni);
		setMenuBar(menibar);
	}

	private void dodajPanele() {

		Panel p1 = new Panel();

		p1.setLayout(new BorderLayout());
		p1.add(new Label("Podloga:"), BorderLayout.CENTER);

		Panel p2 = new Panel();
		p2.setLayout(new GridLayout(2, 1));

		CheckboxGroup grupa = new CheckboxGroup();
		trava = new Checkbox("Trava", grupa, true);
		zid = new Checkbox("Zid", grupa, false);
		trava.setEnabled(false);
		zid.setEnabled(false);
		p2.add(trava);
		p2.add(zid);

		trava.setBackground(Color.GREEN);
		zid.setBackground(Color.LIGHT_GRAY);

		panelDesno = new Panel();
		panelDesno.setLayout(new GridLayout(1, 2));

		panelDesno.add(p1);
		panelDesno.add(p2);

		add(panelDesno, BorderLayout.EAST);

		panelDole = new Panel();
		panelDole.setLayout(new GridLayout(1, 4));

		panelDole.add(new Label("Novcici:", Label.CENTER));

		tekst = new TextField("15");
		panelDole.add(tekst);
		tekst.setEnabled(true);

		poeni = new Label("Poeni: 0");
		panelDole.add(poeni);

		pocni = new Button("Pocni");
		pocni.setEnabled(true);
		panelDole.add(pocni);
		add(panelDole, BorderLayout.SOUTH);
	}

	private void dodajListenere() {

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				mreza.zaustavi();
			}
		});

		meni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String komanda = e.getActionCommand();
				if (komanda.equals("Rezim izmena")) {
					rezimIzmena = true;
					mreza.zaustavi();
					mreza.resetuj();
					tekst.setEnabled(false);
					trava.setEnabled(true);
					zid.setEnabled(true);
					pocni.setEnabled(false);
				} else {
					rezimIzmena = false;
					tekst.setEnabled(true);
					trava.setEnabled(false);
					zid.setEnabled(false);
					pocni.setEnabled(true);
				}
			}
		});

		pocni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mreza.zaustavi();
				String x = tekst.getText();
				Dialog d = new Dialog(Igra.this, "Mreza");
				d.setVisible(true);
				d.setSize(300,300);
				d.add(mreza, BorderLayout.CENTER);
				d.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						d.setVisible(false);
					}
				});
				try {
					int n = Integer.parseInt(x);
					mreza.setBrNovcica(n);
				} catch (NumberFormatException g) {
				}
				mreza.pokreni();
			}
		});

	}

	public boolean getRezimIzmena() {
		return rezimIzmena;
	}

	public static void main(String[] args) {
		new Igra();
	}
}
