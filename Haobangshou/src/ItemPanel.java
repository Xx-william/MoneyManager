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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ItemPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField_name;
	private JTextField textField_date;
	private JTextField textField_amount;
	private Item item1;
	final DateChooser mp = new DateChooser();
	private ArrayList<Money> moneyList;
	private double[] moneyusedData;
	private double[] moneyspendData;
	private int x,y;
	private Date date;
	JCheckBox chckbxWilliam;
	JCheckBox chckbxDaisy;
	JCheckBox chckbxEnzo;

	/**
	 * Create the panel.
	 */
	public ItemPanel(final JFrame frame, final Item item) {
		setLayout(null);

		JButton button = new JButton("\u8FD4\u56DE");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				History history = new History(frame);
				frame.setContentPane(history);
			}
		});
		button.setBounds(101, 69, 117, 29);
		add(button);

		JLabel label = new JLabel("\u4ED8\u6B3E\u4EBA\uFF1A");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		label.setBounds(254, 22, 85, 35);
		add(label);

		JLabel label_1 = new JLabel("\u65E5\u671F\uFF1A");
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		label_1.setBounds(254, 69, 85, 35);
		add(label_1);

		JLabel label_2 = new JLabel("\u91D1\u989D\uFF1A");
		label_2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		label_2.setBounds(254, 116, 85, 35);
		add(label_2);

		JLabel label_3 = new JLabel("\u53C2\u4E0E\u4EBA\uFF1A");
		label_3.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		label_3.setBounds(254, 163, 85, 35);
		add(label_3);

		chckbxWilliam = new JCheckBox("William");
		chckbxWilliam.setBounds(348, 171, 128, 23);
		add(chckbxWilliam);
		if (item.getParticipant().contains("William")) {
			chckbxWilliam.setSelected(true);
		}
		chckbxDaisy = new JCheckBox("Daisy");
		chckbxDaisy.setBounds(348, 206, 128, 23);
		add(chckbxDaisy);
		if (item.getParticipant().contains("Daisy")) {
			chckbxDaisy.setSelected(true);
		}
		chckbxEnzo = new JCheckBox("Enzo");
		chckbxEnzo.setBounds(348, 241, 128, 23);
		add(chckbxEnzo);

		JButton button_2 = new JButton("\u5220\u9664");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				

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
				moneyusedData = new double[3];
				moneyspendData = new double[3];
				for (int i = 0; i < 3; i++) {
					moneyusedData[i] = moneyList.get(i).getUsed();
					moneyspendData[i] = moneyList.get(i).getSpended();
				}
				ser = new ServerConnector(
						"2DELETE FROM Haobanghsou.Shopping WHERE idShopping="
								+ item.getId() + "#end");
				try {
					ser.Update();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int canyurenshu = 0;
				if (item.getParticipant().contains("William")) {
					canyurenshu++;
				}
				if (item.getParticipant().contains("Daisy")) {
					canyurenshu++;
				}
				if (item.getParticipant().contains("Enzo")) {
					canyurenshu++;
				}
				if (item.getName().equals("William")) {
					moneyusedData[0] = moneyusedData[0] - item.getAmount();
				}
				if (item.getName().equals("Daisy")) {
					moneyusedData[1] = moneyusedData[1] - item.getAmount();
				}
				if (item.getName().equals("Enzo")) {
					moneyusedData[2] = moneyusedData[2] - item.getAmount();
				}

				if (item.getParticipant().contains("William")) {
					moneyspendData[0] = moneyspendData[0] - item.getAmount()
							/ canyurenshu;
				}
				if (item.getParticipant().contains("Daisy")) {
					moneyspendData[1] = moneyspendData[1] - item.getAmount()
							/ canyurenshu;
				}
				if (item.getParticipant().contains("Enzo")) {
					moneyspendData[2] = moneyspendData[2] - item.getAmount()
							/ canyurenshu;
				}
				ser = new ServerConnector("2UPDATE Haobanghsou.Money SET used="
						+ String.valueOf(moneyusedData[0])
						+ " WHERE userId=1#end");
				try {
					ser.Update();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ser = new ServerConnector("2UPDATE Haobanghsou.Money SET used="
						+ String.valueOf(moneyusedData[1])
						+ " WHERE userId=2#end");
				try {
					ser.Update();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ser = new ServerConnector("2UPDATE Haobanghsou.Money SET used="
						+ String.valueOf(moneyusedData[2])
						+ " WHERE userId=3#end");
				try {
					ser.Update();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ser = new ServerConnector(
						"2UPDATE Haobanghsou.Money SET spended="
								+ String.valueOf(moneyspendData[0])
								+ " WHERE userId=1#end");
				try {
					ser.Update();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ser = new ServerConnector(
						"2UPDATE Haobanghsou.Money SET spended="
								+ String.valueOf(moneyspendData[1])
								+ " WHERE userId=2#end");
				try {
					ser.Update();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ser = new ServerConnector(
						"2UPDATE Haobanghsou.Money SET spended="
								+ String.valueOf(moneyspendData[2])
								+ " WHERE userId=3#end");
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
				Warning war = new Warning("������ɾ��");
				war.setVisible(true);
			}

		});
		button_2.setBounds(101, 151, 117, 29);
		add(button_2);
		if (item.getParticipant().contains("Enzo")) {
			chckbxEnzo.setSelected(true);
		}

		textField_name = new JTextField();
		textField_name.setEditable(false);
		textField_name.setBounds(351, 28, 293, 28);
		add(textField_name);
		textField_name.setColumns(10);
		textField_name.setText(item.getName());

		textField_date = new JTextField();
		textField_date.setEditable(false);
		textField_date.setColumns(10);
		textField_date.setBounds(351, 75, 293, 28);
		add(textField_date);
		textField_date.setText(item.getDate());

		textField_amount = new JTextField();
		textField_amount.setColumns(10);
		textField_amount.setBounds(351, 122, 293, 28);
		add(textField_amount);
		textField_amount.setText(String.valueOf(item.getAmount()));

		JButton button_1 = new JButton("\u4FEE\u6539");
		button_1.setBounds(101, 110, 117, 29);
		add(button_1);
		
		JButton button_3 = new JButton("日历");
		button_3.addMouseListener(new MouseListener() {
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
		button_3.addActionListener(new ActionListener() {
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
						String dateStr = df.format(date);
						textField_date.setText(dateStr);
					}
				});
				
				
				
			}
		});
		button_3.setBounds(645, 76, 75, 29);
		add(button_3);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 

				boolean b1 = true;
				if (!chckbxWilliam.isSelected() && !chckbxDaisy.isSelected()
						&& !chckbxEnzo.isSelected()) {
					Warning war = new Warning("请至少选择一个参与人");
					war.setVisible(true);
					b1 = false;
				}
				if (textField_date.getText().equals("")) {
					Warning war = new Warning("请选择日期");
					war.setVisible(true);
					b1 = false;
				}
				try {
					Double.parseDouble(textField_amount.getText());
				} catch (Exception e2) {
					Warning war = new Warning("请输入金额");
					war.setVisible(true);
					b1 = false;
				}
				if (b1) {
					item1 = new Item(item);
					item1.setAmount(Double.parseDouble(textField_amount
							.getText()));
					item1.setDate(textField_date.getText());
					item1.setParticipant("");

					if (chckbxWilliam.isSelected()) {
						item1.addParticipant("William ");
					}
					if (chckbxDaisy.isSelected()) {
						item1.addParticipant("Daisy ");
					}
					if (chckbxEnzo.isSelected()) {
						item1.addParticipant("Enzo ");
					}

					double dbAmount = item1.getAmount();
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

					moneyusedData = new double[3];
					moneyspendData = new double[3];
					for (int i = 0; i < 3; i++) {
						moneyusedData[i] = moneyList.get(i).getUsed();
						moneyspendData[i] = moneyList.get(i).getSpended();
					}

					String strAmount = String.valueOf(dbAmount);
					String strDate = item1.getDate();
					String strParticipant = item1.getParticipant();
					int intid = item1.getId();
					String strId = String.valueOf(intid);
					int canyurenshu = 0;
					if (item.getParticipant().contains("William")) {
						canyurenshu++;
					}
					if (item.getParticipant().contains("Daisy")) {
						canyurenshu++;
					}
					if (item.getParticipant().contains("Enzo")) {
						canyurenshu++;
					}

					if (item.getParticipant().contains("William")) {
						moneyspendData[0] = moneyspendData[0]
								- item.getAmount() / canyurenshu;
					}
					if (item.getParticipant().contains("Daisy")) {
						moneyspendData[1] = moneyspendData[1]
								- item.getAmount() / canyurenshu;
					}
					if (item.getParticipant().contains("Enzo")) {
						moneyspendData[2] = moneyspendData[2]
								- item.getAmount() / canyurenshu;
					}
					if (item.getName().equals("William")) {
						moneyusedData[0] = moneyusedData[0] - item.getAmount();
					}
					if (item.getName().equals("Daisy")) {
						moneyusedData[1] = moneyusedData[1] - item.getAmount();
					}
					if (item.getName().equals("Enzo")) {
						moneyusedData[2] = moneyusedData[2] - item.getAmount();
					}

					//
					int canyurenshu1 = 0;
					if (item1.getName().equals("William")) {
						moneyusedData[0] = moneyusedData[0] + item1.getAmount();
					}
					if (item1.getName().equals("Daisy")) {
						moneyusedData[1] = moneyusedData[1] + item1.getAmount();
					}
					if (item1.getName().equals("Enzo")) {
						moneyusedData[2] = moneyusedData[2] + item1.getAmount();
					}
					if (item1.getParticipant().contains("William")) {
						canyurenshu1++;
					}
					if (item1.getParticipant().contains("Daisy")) {
						canyurenshu1++;
					}
					if (item1.getParticipant().contains("Enzo")) {
						canyurenshu1++;
					}

					if (item1.getParticipant().contains("William")) {
						moneyspendData[0] = moneyspendData[0]
								+ item1.getAmount() / canyurenshu1;
					}
					if (item1.getParticipant().contains("Daisy")) {
						moneyspendData[1] = moneyspendData[1]
								+ item1.getAmount() / canyurenshu1;
					}
					if (item1.getParticipant().contains("Enzo")) {
						moneyspendData[2] = moneyspendData[2]
								+ item1.getAmount() / canyurenshu1;
					}

					ser = new ServerConnector(
							"2UPDATE Haobanghsou.Shopping SET amount="
									+ strAmount + " WHERE idShopping in("
									+ strId + ")#end");
					try {
						ser.Update();
					} catch (UnknownHostException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					ser = new ServerConnector(
							"2UPDATE Haobanghsou.Shopping SET date='" + strDate
									+ "' WHERE idShopping in(" + strId
									+ ")#end");
					try {
						ser.Update();
					} catch (UnknownHostException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					ser = new ServerConnector(
							"2UPDATE Haobanghsou.Shopping SET participant='"
									+ strParticipant + "' WHERE idShopping in("
									+ strId + ")#end");
					try {
						ser.Update();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					ser = new ServerConnector(
							"2UPDATE Haobanghsou.Money SET used="
									+ String.valueOf(moneyusedData[0])
									+ " WHERE userId=1#end");
					try {
						ser.Update();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ser = new ServerConnector(
							"2UPDATE Haobanghsou.Money SET used="
									+ String.valueOf(moneyusedData[1])
									+ " WHERE userId=2#end");
					try {
						ser.Update();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ser = new ServerConnector(
							"2UPDATE Haobanghsou.Money SET used="
									+ String.valueOf(moneyusedData[2])
									+ " WHERE userId=3#end");
					try {
						ser.Update();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					ser = new ServerConnector(
							"2UPDATE Haobanghsou.Money SET spended="
									+ String.valueOf(moneyspendData[0])
									+ " WHERE userId=1#end");
					try {
						ser.Update();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ser = new ServerConnector(
							"2UPDATE Haobanghsou.Money SET spended="
									+ String.valueOf(moneyspendData[1])
									+ " WHERE userId=2#end");
					try {
						ser.Update();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ser = new ServerConnector(
							"2UPDATE Haobanghsou.Money SET spended="
									+ String.valueOf(moneyspendData[2])
									+ " WHERE userId=3#end");
					try {
						ser.Update();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					setVisible(false);
					History history = new History(frame);
					frame.setContentPane(history);

				}

			}

		});

	}
}
