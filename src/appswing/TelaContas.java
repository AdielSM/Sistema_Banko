package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import modelo.Correntista;
import modelo.Conta;
import modelo.ContaEspecial;
import regras_negocio.Fachada;

public class TelaContas {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton criar_button;
	private JButton apagar_button;
	private JButton button_4;
	private JTextField cpf_input;
	private JTextField limite_input;
	private JLabel label;
	private JLabel cpf_label;
	private JLabel label_8;
	private JLabel limite_label;
	private JButton verCorrentistasButton;

	/**
	 * Launch the application.
	 */
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					TelaContas window = new TelaContas();
	//					window.frame.setVisible(true);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Create the application.
	 */
	public TelaContas() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listagem();
			}
		});
		frame.setTitle("Contas");
		frame.setBounds(100, 100, 912, 351);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 42, 844, 120);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


		criar_button = new JButton("Criar");
		criar_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(cpf_input.getText().isEmpty())
					{
						label.setText("Informe o CPF do correntista");
						return;
					}
					String cpf = cpf_input.getText().trim();
					
					if (limite_input.getText().isEmpty()) {
						Fachada.criarConta(cpf);
					}
					else {
						Double limite = Double.parseDouble(limite_input.getText().trim());
						Fachada.criarContaEspecial(cpf, limite);
					}
					label.setText("Conta criada!");
					listagem();
					cpf_input.setText("");
					limite_input.setText("");
					
				}
				catch(Exception ex) {
					System.out.println(ex);
					label.setText(ex.getMessage());
				}
			}
		});
		criar_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		criar_button.setBounds(26, 278, 95, 23);
		frame.getContentPane().add(criar_button);

		apagar_button = new JButton("Remover");
		apagar_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() >= 0){
						int id = Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 0));
						
						Object[] options = { "Confirmar", "Cancelar" };
						int escolha = JOptionPane.showOptionDialog(null, "Deseja remover a conta "+id, "Alerta",
								JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
						if(escolha == 0) {
							Fachada.apagarConta(id);
							label.setText("Conta " + id + " removida!");
							listagem();
						}
					}
					else
						label.setText("selecione uma linha");
				}
				catch(Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		apagar_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		apagar_button.setBounds(775, 174, 95, 23);
		frame.getContentPane().add(apagar_button);

		label = new JLabel("");
		label.setForeground(Color.BLUE);
		label.setBackground(Color.RED);
		label.setBounds(139, 287, 717, 14);
		frame.getContentPane().add(label);

		cpf_label = new JLabel("CPF");
		cpf_label.setHorizontalAlignment(SwingConstants.LEFT);
		cpf_label.setFont(new Font("Dialog", Font.PLAIN, 12));
		cpf_label.setBounds(26, 212, 71, 14);
		frame.getContentPane().add(cpf_label);

		cpf_input = new JTextField();
		cpf_input.setFont(new Font("Dialog", Font.PLAIN, 12));
		cpf_input.setColumns(10);
		cpf_input.setBounds(125, 209, 169, 20);
		frame.getContentPane().add(cpf_input);

		label_8 = new JLabel("selecione");
		label_8.setBounds(26, 163, 561, 14);
		frame.getContentPane().add(label_8);

		limite_label = new JLabel("Limite da Conta");
		limite_label.setHorizontalAlignment(SwingConstants.LEFT);
		limite_label.setFont(new Font("Dialog", Font.PLAIN, 12));
		limite_label.setBounds(26, 241, 87, 14);
		frame.getContentPane().add(limite_label);

		limite_input = new JTextField();
		limite_input.setFont(new Font("Dialog", Font.PLAIN, 12));
		limite_input.setColumns(10);
		limite_input.setBounds(125, 239, 71, 20);
		frame.getContentPane().add(limite_input);

		button_4 = new JButton("Listar");
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		button_4.setBounds(410, 8, 95, 23);
		frame.getContentPane().add(button_4);

		verCorrentistasButton = new JButton("Ver correntistas");
		verCorrentistasButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
//		button_5.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				try {
//					if (table.getSelectedRow() >= 0){
//						String id = (String) table.getValueAt( table.getSelectedRow(), 0);
//						Evento ev = Fachada.localizarEvento(Integer.parseInt(id));
//						String nomes= "Nomes dos participantes:";
//						for(Participante p : ev.getParticipantes())
//							nomes+="\n"+p.getNome();
//
//						JOptionPane.showMessageDialog(frame, nomes);
//					}
//					else
//						label.setText("selecione uma linha");
//				}
//				catch(Exception erro) {
//					label.setText(erro.getMessage());
//				}
//			}
//		});
		verCorrentistasButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		verCorrentistasButton.setBounds(605, 174, 135, 23);
		frame.getContentPane().add(verCorrentistasButton);

	

	}

	//*****************************
	public void listagem() {
		try{
			List<Conta> lista = Fachada.listarContas();

			//model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			//colunas
			model.addColumn("id");
			model.addColumn("data");
			model.addColumn("saldo");
			model.addColumn("limite");
			model.addColumn("correntistas");
			//linhas
			for(Conta cc : lista) {
				if (cc instanceof ContaEspecial cce) {
					model.addRow(new Object[]{cc.getId()+"", cc.getData(), cc.getSaldo(), cce.getLimite(), cc.getCorrentistas()});
				} else {					
					model.addRow(new Object[]{cc.getId()+"", cc.getData(), cc.getSaldo(), "-", cc.getCorrentistas()});
				}
			}

			table.setModel(model);
			label_8.setText("resultados: "+lista.size()+ " contas - selecione uma linha");
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}

	}
}
