import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ItemPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField_name;
	private JTextField textField_date;
	private JTextField textField_amount;
	private JTextArea textArea_Comment;
	final DateChooser mp = new DateChooser();
	private ArrayList<Money> moneyData;
	private ArrayList<UserTempt> users;
	private int x, y;
	private Date date;
	private ServerConnector ser;
	private String sql;
	private JsonItems ji;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public ItemPanel(final JFrame frame, final Item item) {
		setLayout(null);

		JButton button_Return = new JButton("\u8FD4\u56DE");
		button_Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				History history = new History(frame);
				frame.setContentPane(history);
			}
		});
		button_Return.setBounds(101, 53, 117, 29);
		add(button_Return);

		JLabel label = new JLabel("\u4ED8\u6B3E\u4EBA\uFF1A");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		label.setBounds(254, 6, 85, 35);
		add(label);

		JLabel label_1 = new JLabel("\u65E5\u671F\uFF1A");
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		label_1.setBounds(254, 53, 85, 35);
		add(label_1);

		JLabel label_2 = new JLabel("\u91D1\u989D\uFF1A");
		label_2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		label_2.setBounds(254, 100, 85, 35);
		add(label_2);

		JLabel label_3 = new JLabel("\u53C2\u4E0E\u4EBA\uFF1A");
		label_3.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		label_3.setBounds(254, 147, 85, 35);
		add(label_3);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(324, 139, 128, 107);
		add(scrollPane);

		sql = "15SELECT * FROM Haobanghsou.User#end";
		ser = new ServerConnector(sql);

		String usersStemptStr;
		try {
			usersStemptStr = ser.Con();
			ji = new JsonItems(usersStemptStr);
			users = ji.jiexiUsers();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		String[] head = { "小伙伴", "参与" };
		int sizeUsers = users.size();
		Object[][] data1 = new Object[sizeUsers][2];
		for (int i = 0; i < sizeUsers; i++) {
			data1[i][0] = users.get(i).getName();
			if (item.getParticipant().contains((CharSequence) data1[i][0])) {
				data1[i][1] = new Boolean(true);
			} else
				data1[i][1] = new Boolean(false);
		}

		@SuppressWarnings("rawtypes")
		Class[] typeArray = { String.class, Boolean.class };

		DefaultTableModel model1 = new DefaultTableModel(data1, head) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int columnIndex) { // 重写getColumnClass返回一个boolean的class，
															// jtable会自动画一个Jcheckbox给你
				return typeArray[columnIndex];// 返回每一列的数据类型
			}

			public boolean isCellEditable(int row, int column) {
				return true;
			}
		};
		table = new JTable(model1);
		scrollPane.setViewportView(table);

		JButton button_Delete = new JButton("\u5220\u9664");
		button_Delete.addActionListener(new ActionListener() { // 删除按钮
					public void actionPerformed(ActionEvent arg0) {

						sql = "12SELECT * FROM Haobanghsou.Money#end";
						ser = new ServerConnector(sql);
						try {
							String stempt = ser.Con();
							JsonItems ji = new JsonItems(stempt);
							moneyData = ji.jiexiMoney();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						String participants_Str = item.getParticipant();
						String participants_Name = "";
						ArrayList<String> participants_Names = new ArrayList<String>();
						int participants_Num = 0;
						for (int i = 0; i < moneyData.size(); i++) { // 获取参与者姓名并存入participants_Names
							participants_Name = moneyData.get(i).getName();
							if (participants_Str.contains(participants_Name)) {
								participants_Num++;
								participants_Names.add(participants_Name);
							}
						}
						int[] participants_id = new int[participants_Num];
						for (int i = 0; i < participants_Num; i++) { // 获取参与者ID并存入participants_id
							for (int j = 0; j < moneyData.size(); j++) {
								if (participants_Names.get(i).equals(
										moneyData.get(j).getName())) {
									participants_id[i] = moneyData.get(j)
											.getId();
								}
							}

						}

						double[] moneyspend = new double[participants_Num];
						double moneyused = moneyData.get(User.id - 1).getUsed()
								- item.getAmount();
						double moneySpendSeparate = item.getAmount()
								/ participants_Num;

						for (int i = 0; i < participants_Num; i++) {
							moneyspend[i] = moneyData.get(
									participants_id[i] - 1).getSpended()
									- moneySpendSeparate;
						}

						sql = "2DELETE FROM Haobanghsou.Shopping WHERE idShopping="
								+ item.getId() + "#end"; // 要求服务器删除此项购买
						ser = new ServerConnector(sql);

						try {
							ser.Update();
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						for (int i = 0; i < participants_Num; i++) {
							sql = "2UPDATE Haobanghsou.Money SET spended="
									+ String.valueOf(moneyspend[i])
									+ " WHERE userId=" + participants_id[i]
									+ "#end";
							ser = new ServerConnector(sql);
							try {
								ser.Update();
							} catch (UnknownHostException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						sql = "2UPDATE Haobanghsou.Money SET used="
								+ String.valueOf(moneyused) + " WHERE userId="
								+ User.id + "#end";
						ser = new ServerConnector(sql);
						try {
							ser.Update();
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						setVisible(false);
						History history = new History(frame);
						frame.setContentPane(history);
						Warning war = new Warning("数据删除已删除");
						war.setVisible(true);
					}

				});
		button_Delete.setBounds(101, 135, 117, 29);
		add(button_Delete);

		textArea_Comment = new JTextArea();
		textArea_Comment.setBounds(516, 147, 204, 99);
		add(textArea_Comment);
		textArea_Comment.setText(item.getComment());

		textField_name = new JTextField();
		textField_name.setEditable(false);
		textField_name.setBounds(351, 12, 117, 28);
		add(textField_name);
		textField_name.setColumns(10);
		textField_name.setText(item.getName());

		textField_date = new JTextField();
		textField_date.setEditable(false);
		textField_date.setColumns(10);
		textField_date.setBounds(351, 59, 117, 28);
		add(textField_date);
		textField_date.setText(item.getDate());

		textField_amount = new JTextField();
		textField_amount.setColumns(10);
		textField_amount.setBounds(351, 106, 117, 28);
		add(textField_amount);
		textField_amount.setText(String.valueOf(item.getAmount()));

		JButton button_Change = new JButton("\u4FEE\u6539");
		button_Change.setBounds(101, 94, 117, 29);
		add(button_Change);

		JButton button_Calender = new JButton("日历");
		button_Calender.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {

				Point p = e.getLocationOnScreen();
				x = p.x;
				y = p.y;
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		button_Calender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFrame jf = new JFrame("日期");
				jf.getContentPane().add(mp);
				jf.pack();
				jf.setVisible(true);
				jf.setLocation(x, y);
				jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				jf.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						date = mp.getDate();
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						String daHaobanghsour = df.format(date);
						textField_date.setText(daHaobanghsour);
					}
				});

			}
		});
		button_Calender.setBounds(475, 60, 75, 29);
		add(button_Calender);

		JLabel label_4 = new JLabel("备注：");
		label_4.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		label_4.setBounds(464, 147, 60, 35);
		add(label_4);

		button_Change.addActionListener(new ActionListener() {//修改按钮
			public void actionPerformed(ActionEvent e) {
				//

				sql = "12SELECT * FROM Haobanghsou.Money#end"; // 获取Money表格数据并存入moneyData
				ser = new ServerConnector(sql);
				try {
					String stempt = ser.Con();
					JsonItems ji = new JsonItems(stempt);
					moneyData = ji.jiexiMoney();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				String participants_Str = item.getParticipant();
				String participants_Name = "";
				ArrayList<String> participants_Names = new ArrayList<String>();
				int participants_Num = 0;
				for (int i = 0; i < moneyData.size(); i++) { // 获取原参与者姓名并存入participants_Names
					participants_Name = moneyData.get(i).getName();
					if (participants_Str.contains(participants_Name)) {
						participants_Num++;
						participants_Names.add(participants_Name);
					}
				}
				int[] participants_id = new int[participants_Num];
				for (int i = 0; i < participants_Num; i++) { // 获取原参与者ID并存入participants_id
					for (int j = 0; j < moneyData.size(); j++) {
						if (participants_Names.get(i).equals(
								moneyData.get(j).getName())) {
							participants_id[i] = moneyData.get(j).getId();
						}
					}
				}

				double[] moneySpend = new double[participants_Num];
				double moneyUsed = moneyData.get(User.id - 1).getUsed()
						- item.getAmount();
				double moneySpendSeparate = item.getAmount() / participants_Num;

				for (int i = 0; i < participants_Num; i++) { // 
					moneySpend[i] = moneyData.get(participants_id[i] - 1)
							.getSpended() - moneySpendSeparate;
				}

				int participants_Num_Now = 0;
				ArrayList<String> participants_Names_Now = new ArrayList<String>();
				ArrayList<Integer> participants_id_Now = new ArrayList<Integer>();
				int tableColumNum = table.getRowCount(); // 获取现参与者人数,姓名，ID
				for (int i = 0; i < tableColumNum; i++) {
					boolean b = (boolean) table.getValueAt(i, 1);
					if (b) {
						participants_Num_Now++;
						participants_Names_Now.add((String) table.getValueAt(i,
								0));
						participants_id_Now.add(moneyData.get(i).getId());
					}
				}

				boolean form_Boolean = true;
				double amount_now = 0;
				try {
					amount_now = Double.parseDouble(textField_amount.getText());

				} catch (Exception e1) {
					Warning war = new Warning("金额输入错误");
					war.setVisible(true);
					form_Boolean = false;
				}

				if (form_Boolean) {
					double amountSeparate_Now = amount_now
							/ participants_Num_Now;
					for (int i = 0; i < participants_Num; i++) {
						moneyData.get(participants_id[i] - 1).setSpended(
								moneySpend[i]);
					}
					for (int i = 0; i < participants_Num_Now; i++) {
						double moneySpended_tempt = moneyData.get(
								participants_id_Now.get(i)-1).getSpended();
						moneyData.get(participants_id_Now.get(i)-1).setSpended(
								moneySpended_tempt + amountSeparate_Now);
					}

					for (int i = 1; i < moneyData.size()+1; i++) {
						double moneySpended_tempt = moneyData.get(i-1)
								.getSpended();

						sql = "2UPDATE Haobanghsou.Money SET spended="      //更新参与用户消费金额
								+ String.valueOf(moneySpended_tempt)
								+ " WHERE userId=" + i + "#end";
						ser = new ServerConnector(sql);
						try {
							ser.Update();
						} catch (UnknownHostException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
					moneyUsed += amount_now;
					sql = "2UPDATE Haobanghsou.Money SET used="        //更新当前用户使用金额
							+ String.valueOf(moneyUsed) + " WHERE userId="
							+ User.id + "#end";
					ser = new ServerConnector(sql);
					try {
						ser.Update();
					} catch (UnknownHostException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					} catch (IOException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}

					sql = "2UPDATE Haobanghsou.Shopping SET amount="             //更新此单金额
							+ String.valueOf(amount_now) + " WHERE idShopping="
							+ item.getId() + "#end";
					ser = new ServerConnector(sql);
					try {
						ser.Update();
					} catch (UnknownHostException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					} catch (IOException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}

					String participantNameNow_Str = "";
					for (int i = 0; i < participants_Names_Now.size(); i++) {
						participantNameNow_Str += participants_Names_Now.get(i)
								+ " ";
					}
					sql = "2UPDATE Haobanghsou.Shopping SET participant='"   //更新参与者
							+ participantNameNow_Str + "' WHERE idShopping="
							+ item.getId() + "#end";
					ser = new ServerConnector(sql);
					try {
						ser.Update();
					} catch (UnknownHostException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					} catch (IOException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}

					String comment_Now = textArea_Comment.getText();    //更新评论
					sql = "2UPDATE Haobanghsou.Shopping SET comment='" + comment_Now
							+ "' WHERE idShopping=" + item.getId() + "#end";
					ser = new ServerConnector(sql);
					try {
						ser.Update();
					} catch (UnknownHostException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					} catch (IOException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					
					String strDate = textField_date.getText();       //更新日期
					sql = "2UPDATE Haobanghsou.Shopping SET date='" + strDate
							+ "' WHERE idShopping=" + item.getId() + "#end";
					ser = new ServerConnector(sql);
					try {
						ser.Update();
					} catch (UnknownHostException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					} catch (IOException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					Warning war = new Warning("修改成功");
					war.setVisible(true);
					setVisible(false);
					History history = new History(frame);
					frame.setContentPane(history);
				}
			}

		});

	}
}
