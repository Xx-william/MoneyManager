import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class Clear extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double[] moneyused;
	private double[] moneyspended;
	private String[] username;
	private double[] money;
	private ArrayList<Money> moneyList;
	double moneyT0 = 0;
	double moneyT1 = 0;
	double moneyT2 = 0;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public Clear(final JFrame frame) {
		setLayout(null);

		JButton button = new JButton("\u8FD4\u56DE");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				MainPanel mainPanel = new MainPanel(frame);
				frame.setContentPane(mainPanel);
			}
		});
		button.setBounds(60, 18, 117, 29);
		add(button);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(208, 7, 261, 182);
		add(textArea);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(481, 6, 306, 182);
		add(scrollPane);

		table = new JTable();
		scrollPane.setColumnHeaderView(table);

		JTextArea textArea_1 = new JTextArea();
		scrollPane.setViewportView(textArea_1);

		ServerConnector ser = new ServerConnector(
				"12SELECT * FROM Haobanghsou.Money#end");
		try {
			String stempt = ser.Con();
			JsonItems ji = new JsonItems(stempt);
			moneyList = ji.jiexiMoney();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		moneyspended = new double[3];
		moneyused = new double[3];
		username = new String[3];
		money = new double[3];
		for (int i = 0; i < 3; i++) {
			double d = moneyList.get(i).getSpended();
			double d2 = Math.round(d * 100) / 100.0;
			d = moneyList.get(i).getUsed();
			double d3 = Math.round(d * 100) / 100.0;
			username[i] = moneyList.get(i).getName();
			moneyspended[i] = d2;
			moneyused[i] = d3;
		}

		for (int i = 0; i < 3; i++) {
			money[i] = moneyused[i] - moneyspended[i];
			money[i] = Math.round(money[i]*100) / 100.0;
		}

		if (money[0] < 0) { // william 

			moneyT0 = 0 - money[0];
			if (money[1] >= 0 && money[2] >= 0) {
				textArea.append("William 给 Daisy" + " " + money[1]);
				textArea.append("\r\n");
				textArea.append("William 给 Enzo" + "  " + money[2]);

			}
			if (money[1] >= 0 && money[2] < 0) {
				moneyT2 = 0 - money[2];
				textArea.append("William 给 Daisy" + " " + moneyT0);
				textArea.append("\r\n");
				textArea.append("Enzo 给 Daisy" + " " + moneyT2);

			}
			if (money[1] < 0 && money[2] >= 0) {
				moneyT1 = 0 - money[1];

				textArea.append("Wiliam 给 Enzo" + " " + moneyT0);
				textArea.append("\r\n");
				textArea.append("Daisy  给  Enzo" + " " + moneyT1);
			}
		}
		if (money[0] >= 0) {
			if (money[1] >= 0 && money[2] < 0) {
				moneyT2 = 0 - money[2];
				textArea.append("Enzo 给 William" + " " + money[0]);
				textArea.append("\r\n");
				textArea.append("Enzo 给 Daisy  " + " " + money[1]);
			}
			if (money[1] < 0 && money[2] >= 0) {
				moneyT1 = 0 - money[1];
				textArea.append("Daisy 给 William" + " " + money[0]);
				textArea.append("\r\n");
				textArea.append("Daisy 给 Enzo   " + " " + money[2]);
			}
			if (money[1] < 0 && money[2] < 0) {
				moneyT1 = 0 - money[1];
				moneyT2 = 0 - money[2];
				textArea.append("Daisy 给 William" + " " + moneyT1);
				textArea.append("\r\n");
				textArea.append("Enzo  给 William" + " " + moneyT2);
			}
		}

		String[] headers = { "用户", "支出", "消费", "总计" };
		String[][] arr = new String[3][4];

		for (int i = 0; i < 3; i++) {			
			arr[i][0] = username[i];
			arr[i][1] = String.valueOf(moneyused[i]);
			arr[i][2] = String.valueOf(moneyspended[i]);
			arr[i][3] = String.valueOf(money[i]);

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
