import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class History extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Item> buyed;
	private Item item1;
	private JTable table;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public History(final JFrame frame) {
		setLayout(null);

		DefaultListModel model = new DefaultListModel();
		buyed = new ArrayList<Item>();
		ServerConnector ser = new ServerConnector(
				"11SELECT * FROM Haobanghsou.Shopping WHERE userName in('"
						+ User.name + "')#end");
		try {
			String stempt = ser.Con();
			JsonItems ji = new JsonItems(stempt);
			buyed = ji.jiexiShopping();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int numberOfBuyed = buyed.size();
		for (int i = numberOfBuyed - 1; i >= 0; i--) {

			String tempt = buyed.get(i).getId() + " " + buyed.get(i).getDate()
					+ "  " + buyed.get(i).getAmount() + "  "
					+ buyed.get(i).getName() + "  "
					+ buyed.get(i).getParticipant();
			model.addElement(tempt);
		}

		JButton btnNewButton = new JButton("\u8FD4\u56DE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				MainPanel mainPanel = new MainPanel(frame);
				frame.setContentPane(mainPanel);
			}
		});
		btnNewButton.setBounds(141, 82, 117, 29);
		add(btnNewButton);

		JLabel label = new JLabel("\u5386\u53F2\u8BB0\u5F55");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		label.setBounds(270, 6, 108, 51);
		add(label);

		JLabel label_1 = new JLabel(
				"\u53CC\u51FB\u8BB0\u5F55\u8FDB\u884C\u4FEE\u6539");
		label_1.setBounds(380, 16, 160, 38);
		add(label_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(270, 58, 422, 189);
		add(scrollPane);

		String[] headers = { "日期", "金额", "付款人", "参与者", "ID" };
		String[][] arr = new String[buyed.size()][5];
		for (int i = 0,j=buyed.size()-1; i < buyed.size(); i++,j--) {
			arr[j][0] = buyed.get(i).getDate();
			arr[j][1] = String.valueOf(buyed.get(i).getAmount());
			arr[j][2] = buyed.get(i).getName();
			arr[j][3] = buyed.get(i).getParticipant();
			arr[j][4] = String.valueOf(buyed.get(i).getId());
		}
		DefaultTableModel model1 = new DefaultTableModel(arr, headers) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model1);
		fitTableColumns(table);
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					DefaultTableModel tableModel = (DefaultTableModel) table
							.getModel();

					int row = table.getSelectedRow();
					String[] str = new String[5];
					for (int i = 0; i < 5; i++) {
						str[i] = (String) tableModel.getValueAt(row, i);
					}

					item1 = new Item(str[2], Double.parseDouble(str[1]),
							str[0], str[3], Integer.parseInt(str[4]));
					setVisible(false);
					ItemPanel itempanel = new ItemPanel(frame, item1);
					frame.setContentPane(itempanel);
				}
			}
		});

	}

	@SuppressWarnings("rawtypes")
	public void fitTableColumns(JTable myTable) {
		myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JTableHeader header = myTable.getTableHeader();
		int rowCount = myTable.getRowCount();
		Enumeration columns = myTable.getColumnModel().getColumns();
		while (columns.hasMoreElements()) {
			TableColumn column = (TableColumn) columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(
					column.getIdentifier());
			int width = (int) header
					.getDefaultRenderer()
					.getTableCellRendererComponent(myTable,
							column.getIdentifier(), false, false, -1, col)
					.getPreferredSize().getWidth();
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) myTable
						.getCellRenderer(row, col)
						.getTableCellRendererComponent(myTable,
								myTable.getValueAt(row, col), false, false,
								row, col).getPreferredSize().getWidth();
				width = Math.max(width, preferedWidth);
			}
			header.setResizingColumn(column); 
			column.setWidth(width + myTable.getIntercellSpacing().width + 19);
		}
	}
}
