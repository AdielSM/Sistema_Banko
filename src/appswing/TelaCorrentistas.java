package appswing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
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

import modelo.Correntista;
import regras_negocio.Fachada;
import javax.swing.JPasswordField;

public class TelaCorrentistas {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel label;
	private JLabel label_6;
	private JLabel nome_label;
	private JLabel label_2;
	private JTextField filtrar_input;
	private JTextField cpf_input;
	private JButton button;
	private JButton criar_button;
	private JTextField nome_input;
	private JPasswordField senha_input;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	private JButton button_5;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// TelaCorrentistas window = new TelaCorrentistas();
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
	public TelaCorrentistas() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setResizable(false);
		frame.setTitle("Correntistas");
		frame.setBounds(100, 100, 729, 385);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listagem();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 43, 674, 148);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");
		label.setForeground(Color.BLUE);
		label.setBounds(21, 321, 688, 14);
		frame.getContentPane().add(label);

		label_6 = new JLabel("selecione");
		label_6.setBounds(21, 197, 431, 14);
		frame.getContentPane().add(label_6);

		nome_label = new JLabel("Buscar por nome ou CPF");
		nome_label.setHorizontalAlignment(SwingConstants.LEFT);
		nome_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nome_label.setBounds(21, 14, 148, 14);
		frame.getContentPane().add(nome_label);

		filtrar_input = new JTextField();
		filtrar_input.setFont(new Font("Dialog", Font.PLAIN, 12));
		filtrar_input.setColumns(10);
		filtrar_input.setBackground(Color.WHITE);
		filtrar_input.setBounds(167, 9, 137, 20);
		frame.getContentPane().add(filtrar_input);

		label_2 = new JLabel("cpf:");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_2.setBounds(21, 269, 71, 14);
		frame.getContentPane().add(label_2);

		cpf_input = new JTextField();
		cpf_input.setFont(new Font("Dialog", Font.PLAIN, 12));
		cpf_input.setColumns(10);
		cpf_input.setBounds(68, 264, 195, 20);
		frame.getContentPane().add(cpf_input);

		criar_button = new JButton("Criar");
		criar_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cpf_input.setText(cpf_input.getText().trim());
				nome_input.setText(nome_input.getText().trim());
				senha_input.setText(senha_input.getText().trim());

				try {
					if (cpf_input.getText().isEmpty() || nome_input.getText().isEmpty()
							|| senha_input.getText().isEmpty()) {
						label.setText("Verifique se todos os campos obrigatórios foram preenchidos.");
						return;
					}
					String nome = cpf_input.getText();
					String cpf = nome_input.getText();
					String senha = senha_input.getText();
					Fachada.criarCorrentista(nome, cpf, senha);

					label.setText("Corrrentista criado: " + nome + " | " + cpf);
					cpf_input.setText("");
					nome_input.setText("");
					senha_input.setText("");
					listagem();
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		criar_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		criar_button.setBounds(535, 273, 86, 23);
		frame.getContentPane().add(criar_button);

		button = new JButton("Filtrar");
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		button.setBounds(314, 7, 89, 23);
		frame.getContentPane().add(button);

		nome_label = new JLabel("nome:");
		nome_label.setHorizontalAlignment(SwingConstants.LEFT);
		nome_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nome_label.setBounds(281, 269, 63, 14);
		frame.getContentPane().add(nome_label);

		nome_input = new JTextField();
		nome_input.setFont(new Font("Dialog", Font.PLAIN, 12));
		nome_input.setColumns(10);
		nome_input.setBounds(336, 264, 168, 20);
		frame.getContentPane().add(nome_input);

		senha_input = new JPasswordField();
		senha_input.setFont(new Font("Dialog", Font.PLAIN, 12));
		senha_input.setColumns(10);
		senha_input.setBounds(68, 290, 81, 20);
		frame.getContentPane().add(senha_input);

		button_5 = new JButton("Limpar");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrar_input.setText("");
				filtrar_input.requestFocus();
				listagem();
			}
		});
		button_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_5.setBounds(415, 8, 89, 23);
		frame.getContentPane().add(button_5);

		JLabel label_senha = new JLabel("senha:");
		label_senha.setHorizontalAlignment(SwingConstants.LEFT);
		label_senha.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_senha.setBounds(21, 295, 71, 14);
		frame.getContentPane().add(label_senha);

		JButton btnVerId = new JButton("Ver ID");
		btnVerId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int row = table.getSelectedRow();
					if (row != -1) {
						String cpf = table.getValueAt(row, 0).toString();
						
						JOptionPane.showMessageDialog(btnVerId, "ID copiado: " + cpf);

						StringSelection stringSelection = new StringSelection(cpf);
						Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
						clipboard.setContents(stringSelection, null);

					} else {
						label.setText("Nenhum correntista selecionado.");
					}
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(frame, "Erro ao buscar CPF: " + erro.getMessage(), "Erro",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnVerId.setBounds(594, 203, 101, 27);
		frame.getContentPane().add(btnVerId);
	}

	public void listagem() {
		try {
			List<Correntista> lista = Fachada.listarCorrentistas(filtrar_input.getText());

			DefaultTableModel model = new DefaultTableModel();

			// colunas
			model.addColumn("cpf");
			model.addColumn("nome");

			// linhas
			for (Correntista c : lista) {
				model.addRow(new Object[] { c.getCpf(), c.getNome() });

			}

			table.setModel(model);
			label_6.setText("resultados: " + lista.size() + " correntistas - selecione uma linha");
		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}
}
