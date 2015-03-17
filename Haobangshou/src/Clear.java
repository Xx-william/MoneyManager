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
	private ArrayList<Money> moneyData;
	double moneyT0 = 0;
	double moneyT1 = 0;
	double moneyT2 = 0;
	private JTable table;
	private JTable table_Money;
	private String textArea_Str = "";

	/**
	 * Create the panel.
	 */
	public Clear(final JFrame frame) {
		setLayout(null);

		JButton button_Return = new JButton("\u8FD4\u56DE");
		button_Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				MainPanel mainPanel = new MainPanel(frame);
				frame.setContentPane(mainPanel);
			}
		});
		button_Return.setBounds(60, 18, 117, 29);
		add(button_Return);

		JTextArea textArea_Clear = new JTextArea();
		textArea_Clear.setEditable(false);
		textArea_Clear.setBounds(208, 7, 261, 182);
		add(textArea_Clear);

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
			moneyData = ji.jiexiMoney();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		moneyspended = new double[moneyData.size()];
		moneyused = new double[moneyData.size()];
		username = new String[moneyData.size()];
		money = new double[moneyData.size()];

		for (int i = 0; i < moneyData.size(); i++) {
			double d = moneyData.get(i).getSpended();
			double d2 = Math.round(d * 100) / 100.0;

			d = moneyData.get(i).getUsed();
			double d3 = Math.round(d * 100) / 100.0;
			username[i] = moneyData.get(i).getName();
			moneyspended[i] = d2;
			moneyused[i] = d3;
		}

		for (int i = 0; i < moneyData.size(); i++) {
			money[i] = moneyused[i] - moneyspended[i];
			money[i] = Math.round(money[i] * 100) / 100.0;
		}

		String[] headers = { "用户", "支出", "消费", "总计" };
		String[][] arr = new String[moneyData.size()][4];

		for (int i = 0; i < moneyData.size(); i++) {
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
		table_Money = new JTable(model1);
		fitTableColumns(table_Money);
		scrollPane.setViewportView(table_Money);

		ArrayList<Money> money_Positive = new ArrayList<Money>();
		ArrayList<Money> money_Negative = new ArrayList<Money>();

		for (int i = 0; i < moneyData.size(); i++) {
			moneyData.get(i).countMoney();
			if (moneyData.get(i).getMoney() > 0) {
				money_Positive.add(moneyData.get(i));
			} else if (moneyData.get(i).getMoney() < 0) {
				money_Negative.add(moneyData.get(i));
			}
		}

		for (int i = 0; i < money_Negative.size() - 1; i++) {
			for (int j = i + 1; j < money_Negative.size(); j++) {
				if (money_Negative.get(j).getMoney() < money_Negative.get(i)
						.getMoney()) {
					Money moneyTempt = new Money(money_Negative.get(j));
					money_Negative.set(j, money_Negative.get(i));
					money_Negative.set(i, moneyTempt);
				}
			}
		}

		for (int i = 0; i < money_Positive.size() - 1; i++) {
			for (int j = i + 1; j < money_Positive.size(); j++) {
				if (money_Positive.get(j).getMoney() < money_Positive.get(i)
						.getMoney()) {
					Money moneyTempt = new Money(money_Positive.get(j));
					money_Positive.set(j, money_Positive.get(i));
					money_Positive.set(i, moneyTempt);
				}
			}
		}
        
		for (int i = 0; i < money_Negative.size(); i++) {
			for (int j = 0; j < money_Positive.size(); j++) {
				double moneyTempt = money_Negative.get(i).getMoney()
						+ money_Positive.get(j).getMoney();
				if (moneyTempt < 0) {     //欠债者i还没还清,收债者j收完
					textArea_Str += money_Negative.get(i).getName() + " 付给 "
							+ money_Positive.get(j).getName() + " "
							+ money_Positive.get(j).getMoney() + "\r\n";
					money_Positive.get(j).setMoney(0);
					money_Negative.get(i).setMoney(moneyTempt);
				} else if (moneyTempt > 0) {  //欠债者i还清，收债者j没收完
					double money_double = 0 - money_Negative.get(i).getMoney();
					textArea_Str += money_Negative.get(i).getName() + " 付给 "
							+ money_Positive.get(j).getName() + " "
							+ money_double + "\r\n";
					money_Negative.get(i).setMoney(0);;
					money_Positive.get(j).setMoney(moneyTempt);
					break;
				} else {           //欠债者i还清，收债者j收完
					textArea_Str += money_Negative.get(i).getName() + " 付给 "
							+ money_Positive.get(j).getName() + " "
							+ money_Positive.get(j).getMoney() + "\r\n";
					money_Positive.get(j).setMoney(0);;
					money_Negative.get(i).setMoney(0);;
					break;
				}
			}
		}
		
		textArea_Clear.setText(textArea_Str);

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
