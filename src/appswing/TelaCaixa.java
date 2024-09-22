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
import javax.swing.JPasswordField;

public class TelaCaixa {
	private JDialog frame;
	private JScrollPane scrollPane;
	private JButton transferencia_button;
	private JLabel label;
	private JLabel label_8;
	private JLabel nome_label;
	private JTextField filtro_input;
	private JButton limpar_button;
	private JLabel nome_label_1;
	private JTextField valorTransferenciaInput;
	private JTextField idContaInput;
	private JButton filtro_button;
	private JLabel id_conta_label;
	private JLabel valor_transferencia_label;
	private JButton debitar_button;
	private JButton creditar_button;
	private JTextField valor_input;
	private JLabel valor_label;
	private JTextField cpf_input;
	private JPasswordField senha_input;
	private JLabel senha_label;
	private JLabel cpf_label;
	private JTable table;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// TelaContas window = new TelaContas();
	// window.frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the application.
	 */
	public TelaCaixa() {
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
		frame.setTitle("Caixa");
		frame.setBounds(100, 100, 912, 351);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 42, 844, 120);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setShowGrid(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(true);
		table.setRequestFocusEnabled(false);
		table.setGridColor(Color.BLACK);
		table.setFont(new Font("Dialog", Font.PLAIN, 12));
		table.setFocusable(false);
		table.setFillsViewportHeight(true);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setBackground(Color.WHITE);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrollPane.setViewportView(table);

		label = new JLabel("");
		label.setForeground(Color.BLUE);
		label.setBackground(Color.RED);
		label.setBounds(26, 285, 783, 20);
		frame.getContentPane().add(label);

		label_8 = new JLabel("selecione");
		label_8.setBounds(26, 163, 561, 14);
		frame.getContentPane().add(label_8);

		transferencia_button = new JButton("Transferir");
		transferencia_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Double valor;
					String senha = senha_input.getText().trim();
					String cpf = cpf_input.getText().trim();
					int idDestino;
					
					int row = table.getSelectedRow();
					if (valorTransferenciaInput.getText().isEmpty()) {
						label.setText("Informe o valor para realizar a operação.");
						return;
					}
					valor = Double.parseDouble(valorTransferenciaInput.getText().trim());
					if (valor <= 0) {
						label.setText("Informe um valor maior que 0 para realizar a operação.");
						return;
					}
					if (idContaInput.getText().isEmpty()) {
						label.setText("Informe o id da Conta de destino para realizar a operação.");
						return;
					}
					idDestino = Integer.parseInt(idContaInput.getText().trim());
					
					if (cpf.isEmpty()) {
						label.setText("Informe o CPF do correntista para realizar a operação.");
						return;
					}
					
					if (senha.isEmpty()) {
						label.setText("Informe a senha do correntista para realizar a operação.");
						return;
					}
					
					if (row != -1) {
						int id = Integer.parseInt(table.getValueAt(row, 0).toString());
						Fachada.transferirValor(id, cpf, senha, valor, idDestino);
						label.setText(valor + " transferido da conta " + id + " para a conta " + idDestino);
						valorTransferenciaInput.setText("");
						senha_input.setText("");
						cpf_input.setText("");
						idContaInput.setText("");
						listagem();
					} else {
						label.setText("Selecione uma conta.");
					}
				} catch (Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		transferencia_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		transferencia_button.setBounds(226, 219, 95, 45);
		frame.getContentPane().add(transferencia_button);
		
		nome_label = new JLabel("Buscar por ID da conta ou CPF do correntista");
		nome_label.setHorizontalAlignment(SwingConstants.LEFT);
		nome_label.setFont(new Font("Dialog", Font.PLAIN, 12));
		nome_label.setBounds(12, 14, 259, 14);
		frame.getContentPane().add(nome_label);
		
		filtro_input = new JTextField();
		filtro_input.setFont(new Font("Dialog", Font.PLAIN, 12));
		filtro_input.setColumns(10);
		filtro_input.setBackground(Color.WHITE);
		filtro_input.setBounds(271, 10, 137, 20);
		frame.getContentPane().add(filtro_input);
		
		limpar_button = new JButton("Limpar");
		limpar_button.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent e) {
				filtro_input.setText("");
				listagem();
			}
		});
		limpar_button.setFont(new Font("Dialog", Font.PLAIN, 12));
		limpar_button.setBounds(527, 7, 89, 23);
		frame.getContentPane().add(limpar_button);
		
		nome_label_1 = new JLabel("Transferir para outra Conta:");
		nome_label_1.setHorizontalAlignment(SwingConstants.LEFT);
		nome_label_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		nome_label_1.setBounds(26, 193, 162, 14);
		frame.getContentPane().add(nome_label_1);
		
		valorTransferenciaInput = new JTextField();
		valorTransferenciaInput.setFont(new Font("Dialog", Font.PLAIN, 12));
		valorTransferenciaInput.setColumns(10);
		valorTransferenciaInput.setBackground(Color.WHITE);
		valorTransferenciaInput.setBounds(131, 244, 83, 20);
		frame.getContentPane().add(valorTransferenciaInput);
		
		idContaInput = new JTextField();
		idContaInput.setFont(new Font("Dialog", Font.PLAIN, 12));
		idContaInput.setColumns(10);
		idContaInput.setBackground(Color.WHITE);
		idContaInput.setBounds(131, 220, 83, 20);
		frame.getContentPane().add(idContaInput);
		
		filtro_button = new JButton("Listar");
		filtro_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		filtro_button.setFont(new Font("Dialog", Font.PLAIN, 12));
		filtro_button.setBounds(420, 7, 95, 23);
		frame.getContentPane().add(filtro_button);
		
		id_conta_label = new JLabel("ID Conta destino");
		id_conta_label.setHorizontalAlignment(SwingConstants.LEFT);
		id_conta_label.setFont(new Font("Dialog", Font.PLAIN, 12));
		id_conta_label.setBounds(26, 219, 101, 20);
		frame.getContentPane().add(id_conta_label);
		
		valor_transferencia_label = new JLabel("Valor");
		valor_transferencia_label.setHorizontalAlignment(SwingConstants.LEFT);
		valor_transferencia_label.setFont(new Font("Dialog", Font.PLAIN, 12));
		valor_transferencia_label.setBounds(88, 244, 35, 20);
		frame.getContentPane().add(valor_transferencia_label);
		
		debitar_button = new JButton("Debitar");
		debitar_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Double valor;
					String senha = senha_input.getText().trim();
					String cpf = cpf_input.getText().trim();
					
					int row = table.getSelectedRow();
					if (valor_input.getText().isEmpty()) {
						label.setText("Informe o valor para realizar a operação.");
						return;
					}
					valor = Double.parseDouble(valor_input.getText().trim());
					if (valor <= 0) {
						label.setText("Informe um valor maior que 0 para realizar a operação.");
						return;
					}
					
					if (cpf.isEmpty()) {
						label.setText("Informe o CPF do correntista para realizar a operação.");
						return;
					}
					
					if (senha.isEmpty()) {
						label.setText("Informe a senha do correntista para realizar a operação.");
						return;
					}
					
					if (row != -1) {
						int id = Integer.parseInt(table.getValueAt(row, 0).toString());
						Fachada.debitarValor(id, cpf, senha, valor);
						label.setText(valor + " debitado da conta " + id);
						valor_input.setText("");
						senha_input.setText("");
						cpf_input.setText("");
						listagem();
					} else {
						label.setText("Selecione uma conta.");
					}
				} catch (Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		debitar_button.setFont(new Font("Dialog", Font.PLAIN, 12));
		debitar_button.setBounds(761, 174, 95, 23);
		frame.getContentPane().add(debitar_button);
		
		creditar_button = new JButton("Creditar");
		creditar_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String senha = senha_input.getText().trim();
					String cpf = cpf_input.getText().trim();
					Double valor;
					
					int row = table.getSelectedRow();
					if (valor_input.getText().isEmpty()) {
						label.setText("Informe o valor para realizar a operação.");
						return;
					}
					valor = Double.parseDouble(valor_input.getText().trim());
					if (valor <= 0) {
						label.setText("Informe um valor maior que 0 para realizar a operação.");
						return;
					}
					
					if (cpf.isEmpty()) {
						label.setText("Informe o CPF do correntista para realizar a operação.");
						return;
					}
					
					if (senha.isEmpty()) {
						label.setText("Informe a senha do correntista para realizar a operação.");
						return;
					}

					if (row != -1) {
						int id = Integer.parseInt(table.getValueAt(row, 0).toString());
						Fachada.creditarValor(id, cpf, senha, valor);
						label.setText(valor + " creditado na conta " + id);
						valor_input.setText("");
						senha_input.setText("");
						cpf_input.setText("");
						listagem();
					} else {
						label.setText("Selecione uma conta.");
					}
				} catch (Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		creditar_button.setFont(new Font("Dialog", Font.PLAIN, 12));
		creditar_button.setBounds(652, 174, 95, 23);
		frame.getContentPane().add(creditar_button);
		
		valor_input = new JTextField();
		valor_input.setFont(new Font("Dialog", Font.PLAIN, 12));
		valor_input.setColumns(10);
		valor_input.setBackground(Color.WHITE);
		valor_input.setBounds(557, 175, 83, 20);
		frame.getContentPane().add(valor_input);
		
		valor_label = new JLabel("Valor");
		valor_label.setHorizontalAlignment(SwingConstants.LEFT);
		valor_label.setFont(new Font("Dialog", Font.PLAIN, 12));
		valor_label.setBounds(522, 175, 29, 20);
		frame.getContentPane().add(valor_label);
		
		cpf_input = new JTextField();
		cpf_input.setFont(new Font("Dialog", Font.PLAIN, 12));
		cpf_input.setColumns(10);
		cpf_input.setBackground(Color.WHITE);
		cpf_input.setBounds(719, 218, 137, 20);
		frame.getContentPane().add(cpf_input);
		
		senha_input = new JPasswordField();
		senha_input.setFont(new Font("Dialog", Font.PLAIN, 12));
		senha_input.setColumns(10);
		senha_input.setBackground(Color.WHITE);
		senha_input.setBounds(719, 241, 137, 20);
		frame.getContentPane().add(senha_input);
		
		senha_label = new JLabel("Senha");
		senha_label.setHorizontalAlignment(SwingConstants.LEFT);
		senha_label.setFont(new Font("Dialog", Font.PLAIN, 12));
		senha_label.setBounds(676, 241, 35, 20);
		frame.getContentPane().add(senha_label);
		
		cpf_label = new JLabel("CPF");
		cpf_label.setHorizontalAlignment(SwingConstants.LEFT);
		cpf_label.setFont(new Font("Dialog", Font.PLAIN, 12));
		cpf_label.setBounds(682, 218, 29, 20);
		frame.getContentPane().add(cpf_label);

	}

	// *****************************
	public void listagem() {
		try {
			List<Conta> lista = Fachada.listarContas(filtro_input.getText());

			// model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			// colunas
			model.addColumn("id");
			model.addColumn("data");
			model.addColumn("saldo");
			model.addColumn("limite");
			model.addColumn("correntistas");
			// linhas
			for (Conta cc : lista) {
				if (cc instanceof ContaEspecial cce) {
					model.addRow(new Object[] { cc.getId() + "", cc.getData(), cc.getSaldo(), cce.getLimite(),
							cc.exibirCpfCorrentistas() });
				} else {
					model.addRow(new Object[] { cc.getId() + "", cc.getData(), cc.getSaldo(), "-",
							cc.exibirCpfCorrentistas() });
				}
			}

			table.setModel(model);

			table.getColumnModel().getColumn(0).setPreferredWidth(30); // id
			table.getColumnModel().getColumn(1).setPreferredWidth(75); // data
			table.getColumnModel().getColumn(2).setPreferredWidth(100); // saldo
			table.getColumnModel().getColumn(3).setPreferredWidth(80); // limite
			table.getColumnModel().getColumn(4).setPreferredWidth(556); // correntistas
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			label_8.setText("resultados: " + lista.size() + " contas - selecione uma linha");
		} catch (Exception erro) {
			System.out.println(erro);
			label.setText(erro.getMessage());
		}

	}
}
