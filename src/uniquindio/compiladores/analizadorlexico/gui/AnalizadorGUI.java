package uniquindio.compiladores.analizadorlexico.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import uniquindio.compiladores.analizadorlexico.AnalizadorLexico;
import uniquindio.compiladores.analizadorlexico.ErrorLexico;
import uniquindio.compiladores.analizadorlexico.Token;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Color;

/**
 * Clase que lanza la aplicacion del analizador lexico
 * 
 * @author JHONNY_JORGE_CARLOS
 *
 */
public class AnalizadorGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultTableModel dtm,dtmErrores;
	private Object[] fila,fila2;
	private JTable tablaErrores;
	private JTable tableAnalisis;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnalizadorGUI frame = new AnalizadorGUI();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AnalizadorGUI() {
		setTitle("Analizador L\u00E9xico JORJHO");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 906, 729);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Inicio");
		menuBar.add(mnNewMenu);

		JMenuItem menuAcercade = new JMenuItem("Acerda de");
		mnNewMenu.add(menuAcercade);

		JMenuItem menuSalir = new JMenuItem("Salir");
		menuSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(menuSalir);
		menuAcercade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Analizador L�xico JORJHO V 2.0\n\nDesarrollado por:\nJhonny Sierra\nJorge Mesa\nCarlos Lopez", "Acerca de",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnAnalizar = new JButton("Analizar C\u00F3digo");
		btnAnalizar.setFont(new Font("Nirmala UI", Font.BOLD, 13));

		JScrollPane scrollPane_1 = new JScrollPane();

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		
		JLabel lblCdigoFuente = new JLabel("C\u00F3digo Fuente");
		lblCdigoFuente.setForeground(Color.DARK_GRAY);
		lblCdigoFuente.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 16));
		
		JLabel lblrbolSintctico = new JLabel("\u00C1rbol sint\u00E1ctico");
		lblrbolSintctico.setForeground(Color.DARK_GRAY);
		lblrbolSintctico.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 16));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 854, Short.MAX_VALUE)
						.addComponent(btnAnalizar, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
									.addGap(18))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblCdigoFuente)
									.addGap(322)))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 415, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblrbolSintctico, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnAnalizar)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCdigoFuente)
						.addComponent(lblrbolSintctico, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(16)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_3, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 365, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);

		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("Tokens", null, scrollPane, null);

		// TABLA TOKENS
		tableAnalisis = new JTable();
		tableAnalisis.setFont(new Font("Consolas", Font.PLAIN, 14));
		tableAnalisis.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Palabra", "Categoria", "Fila", "Columna" }) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
				});

		scrollPane.setViewportView(tableAnalisis);
		dtm = (DefaultTableModel) tableAnalisis.getModel();
		
		JTableHeader encabezadoTabla = tableAnalisis.getTableHeader();
		encabezadoTabla.setFont(new Font("Consolas", Font.BOLD, 16));
		encabezadoTabla.setReorderingAllowed(false);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		tabbedPane.addTab("Errores", null, scrollPane_2, null);

		//TABLA ERRORES
		tablaErrores = new JTable();
		tablaErrores.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Palabra", "Categoria", "Fila", "Columna" }) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
				});
		tablaErrores.setFont(new Font("Consolas", Font.PLAIN, 14));
		scrollPane_2.setViewportView(tablaErrores);
		dtmErrores = (DefaultTableModel) tablaErrores.getModel();
		JTableHeader encabezadoTablaErrores = tablaErrores.getTableHeader();
		encabezadoTablaErrores.setFont(new Font("Consolas", Font.BOLD, 16));
		encabezadoTablaErrores.setReorderingAllowed(false);
		

		JTextArea taCodigoFuente = new JTextArea();
		taCodigoFuente.setFont(new Font("Consolas", Font.PLAIN, 16));
		scrollPane_1.setViewportView(taCodigoFuente);

		

		contentPane.setLayout(gl_contentPane);

		setLocationRelativeTo(null);

		btnAnalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String codigoFuente = "";

				codigoFuente = taCodigoFuente.getText();
				// AnalizadorLexico al = new AnalizadorLexico("#A56GH {\n}.7�5?�F\n@7:3�hola�
				// ^hola^");
				if (codigoFuente.equals("")) {
					JOptionPane.showMessageDialog(null, "Ingrese al menos un caracter", "ERROR",
							JOptionPane.WARNING_MESSAGE);
				} else {
					AnalizadorLexico al = new AnalizadorLexico(codigoFuente);
					al.analizar();

					limpiarTablas();
					
					JOptionPane.showMessageDialog(null, "An�lisis terminado", "EXITOSO",
							JOptionPane.INFORMATION_MESSAGE);
					
					tabbedPane.setSelectedIndex(0);
					
					fila = new Object[dtm.getColumnCount()];
					
					fila2 = new Object[dtmErrores.getColumnCount()];

					System.out.printf("%-20s%-20s%-20s%-20s\n", "PALABRA", "CATEGORIA", "FILA", "COLUMNA");

					for (Token token : al.getListaTokens()) {

						fila[0] = token.getPalabra();
						fila[1] = token.getCategoria();
						fila[2] = token.getFila();
						fila[3] = token.getColumna();
						dtm.addRow(fila);

						System.out.printf("%-20s%-20s%-20s%-20s\n", token.getPalabra(), token.getCategoria(),
								token.getFila(), token.getColumna());
					}

					for (ErrorLexico error : al.getListaErrores()) {

						fila2[0] = error.getPalabra();
						fila2[1] = error.getCategoria();
						fila2[2] = error.getFila();
						fila2[3] = error.getColumna();
						dtmErrores.addRow(fila2);

						System.out.printf("%-20s%-20s%-20s%-20s\n", error.getPalabra(), error.getCategoria(),
								error.getFila(), error.getColumna());
					}
					
				}

			}
		});
	}

	/**
	 * Metodo que permite eliminar todas las filas de la tabla donde se muestran los
	 * tokens reconocidos
	 */
	public void limpiarTablas() {

		for (int i = dtm.getRowCount() - 1; i >= 0; i--) {
			dtm.removeRow(i);
		}
		
		for (int i = dtmErrores.getRowCount() - 1; i >= 0; i--) {
			dtmErrores.removeRow(i);
		}
	}
}
