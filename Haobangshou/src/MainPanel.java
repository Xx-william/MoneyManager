import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class MainPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private JButton button;
	private ArrayList<Item> data;
	private ArrayList<LogInfo> logInfos;
	private JTable table;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MainPanel(final JFrame frame) {

		data = new ArrayList<Item>();
		ServerConnector ser = new ServerConnector(
				"11SELECT * FROM Haobanghsou.Shopping#end");

		try {
			String stempt = ser.Con();
			JsonItems ji = new JsonItems(stempt);
			data = ji.jiexiShopping();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		button = new JButton("\u65B0\u8D26");
		button.setBounds(39, 51, 117, 29);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				setVisible(false);
				Xinzhang xinzhang = new Xinzhang(frame);
				frame.setContentPane(xinzhang);

			}
		});
		setLayout(null);
		add(button);

		textArea = new JTextArea();
		textArea.setBounds(653, 32, 190, 81);
		textArea.setEditable(false);
		add(textArea);

		JButton button_1 = new JButton("\u4ED8\u6B3E\u5386\u53F2");
		button_1.setBounds(39, 92, 117, 29);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				History history = new History(frame);
				frame.setContentPane(history);
			}
		});
		add(button_1);

		JButton button_2 = new JButton("\u7ED3\u8D26");
		button_2.setBounds(39, 134, 117, 29);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				Clear clear = new Clear(frame);
				frame.setContentPane(clear);
			}
		});
		add(button_2);
		
		ArrayList name = new ArrayList();
		ArrayList time = new ArrayList();
		textArea.setText("");

		JLabel label_4 = new JLabel("\u767B\u9646\u4FE1\u606F\uFF1A");
		label_4.setBounds(653, 16, 84, 16);
		add(label_4);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(172, 31, 423, 206);
		add(scrollPane);

		ServerConnector ser1 = new ServerConnector(
				"13SELECT * FROM Haobanghsou.LogInfo#end");
		try {
			String stempt = ser1.Con();
			JsonItems ji = new JsonItems(stempt);
			logInfos = ji.jiexiLogInfo();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Warning war = new Warning("服务器数据库未启用");
			war.setVisible(true);
		}
		for (int i = 0; i < 3; i++) {
			name.add(logInfos.get(i).getName());
			time.add(logInfos.get(i).getTime());
		}

		int numberOfUser1 = name.size();
		for (int i = 0; i < numberOfUser1; i++) {
			textArea.append((String) name.get(i));
			textArea.append("       ");
			textArea.append((String) time.get(i));
			textArea.append("\r\n");
		}

		String[] headers = { "日期", "金额", "付款人", "参与者", "ID" };
		String[][] arr = new String[data.size()][5];
		for (int i = 0,  w = data.size()-1; i < data.size(); i++,w--) {
			arr[w][0] = data.get(i).getDate();
			arr[w][1] = String.valueOf(data.get(i).getAmount());
			arr[w][2] = data.get(i).getName();
			arr[w][3] = data.get(i).getParticipant();
			arr[w][4] = String.valueOf(data.get(i).getId());
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
