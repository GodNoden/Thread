package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Hilos extends Frame implements WindowListener, ActionListener {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Hilos hilos = new Hilos();
		hilos.setVisible(true);
	}

	// 7. El hilo carezca de atributos públicos
	private TextArea area1, area2;
	private Button button;
	private Hilo hilo1, hilo2;

	public Hilos() {
		super("HILOS");
		setLayout(new BorderLayout());

		area1 = new TextArea();
		area2 = new TextArea();
		area1.setEditable(false);
		area2.setEditable(false);
		button = new Button("Accion");
		button.addActionListener(this); // 3. Incluir un botón para asociarlo en tiempo compilación a uno cualquiera de
										// los hilos

		add("North", area1);
		add("Center", area2);
		add("South", button);

		setSize(350, 350);

		hilo1 = new Hilo(area1, 1000); // 1. Ambos hilos impriman un contador en áreas de texto particulares
		hilo2 = new Hilo(area2, 2000); // 2. Un hilo imprima cada segundo y el otro cada 2 segundos

		hilo1.start();
		hilo2.start();

		addWindowListener(this);

	}

	class Hilo extends Thread {
		public TextArea textArea;
		public long time;
		public int count;
		public boolean flag; // 9. Para lograr el efecto de la suspensión del hilo se requiera de una bandera

		public Hilo(TextArea textArea, long time) {
			this.textArea = textArea;
			this.time = time;
			count = 0;
			flag = false;
		}

		public void pausar() {
			flag = true;
		}

		public synchronized void play() {
			flag = false;
			//cuando se vuelva a presionar el botón se ejecute notify() sobre la variable asociada
			notify();
		}

		public void run() {
			while (true) {
				if (flag) {
					synchronized (this) {
						try {
							// b) Si la bandera indica suspender, entonces mediante wait() y una variable de
							// instancia asociada ejecutar wait() para evitar momentáneamente (en lo que se
							// presiona de nuevo el botón) que imprima el incremente el contador;
							wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} // a) Si la bandera indica que el hilo imprima, entonces se imprime e incrementa
					// el contador
				count++;
				textArea.append(count + "\n");

				try {
					sleep(time);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 8. El evento del botonazo, provoque el paso de solo un mensaje al objeto
		// hilo, indicándole solo que ha ocurrido un botonazo, por lo que el método a
		// invocar no deberá recibir argumento alguno
		// TODO Auto-generated method stub
		if (hilo1.flag) {
			hilo1.play(); // 5. El hilo reanude su ejecución tras otro botonazo con el mismo botón que el
			// punto anterior
		} else {
			hilo1.pausar(); // 4. Al presionar el botón, que el hilo correspondiente suspenda su ejecución
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
