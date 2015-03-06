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

@SuppressWarnings("serial")
public class Xinzhang extends JPanel {
	public JTextField textField;
	private int x, y;
	private Date date = new Date();
	final DateChooser mp = new DateChooser();
	private JTextField textField_1;
	private JCheckBox chckbxDaisy;
	private JCheckBox chckbxWilliam;
	private JCheckBox chckbxEnzo;
	private String dateStmp;
	private double moneyStmp;
	private boolean[] participantStmp;
	private String participant = "";
	private ArrayList<Money> data;
	private String sql = "";
	private SimpleDateFormat date12 = new SimpleDateFormat("yyyy-MM-dd");
	private String dateStr12 = date12.format(new Date());

	/**
	 * Create the panel.
	 */
	public Xinzhang(final JFrame frame) {

		JButton btnNewButton = new JButton("返回");
		btnNewButton.setBounds(283, 214, 98, 29);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				MainPanel mainPanel = new MainPanel(frame);
				frame.setContentPane(mainPanel);
			}
		});
		setLayout(null);
		add(btnNewButton);

		JLabel label = new JLabel("日期");
		label.setBounds(208, 21, 75, 29);
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		add(label);

		textField = new JTextField();
		textField.setBounds(273, 21, 296, 28);
		textField.setEditable(false);
		add(textField);
		textField.setColumns(10);

		dateStr12 = date12.format(new Date());
		textField.setText(dateStr12);

		JButton button = new JButton("日历");
		button.setBounds(581, 21, 117, 29);
		button.addMouseListener(new MouseListener() {
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

		button.addActionListener(new ActionListener() {
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
						dateStr12 = date12.format(date);
						textField.setText(dateStr12);
					}
				});
			}
		});
		add(button);

		JLabel label_1 = new JLabel("\u91D1\u989D");
		label_1.setBounds(208, 62, 75, 29);
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		add(label_1);

		textField_1 = new JTextField();
		textField_1.setBounds(273, 62, 134, 28);
		add(textField_1);
		textField_1.setColumns(10);

		JLabel label_2 = new JLabel("\u4EBA\u5458");
		label_2.setBounds(208, 103, 75, 29);
		label_2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		add(label_2);

		chckbxWilliam = new JCheckBox("William");
		chckbxWilliam.setBounds(273, 108, 128, 23);
		add(chckbxWilliam);

		chckbxEnzo = new JCheckBox("Enzo");
		chckbxEnzo.setBounds(273, 144, 128, 23);
		add(chckbxEnzo);

		chckbxDaisy = new JCheckBox("Daisy");
		chckbxDaisy.setBounds(273, 179, 128, 23);
		add(chckbxDaisy);

		JButton btnNewButton_1 = new JButton("\u786E\u8BA4");
		btnNewButton_1.setBounds(427, 214, 98, 29);
		btnNewButton_1.addActionListener(new ActionListener() {

			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				boolean b = true;

				if (!chckbxWilliam.isSelected() && !chckbxDaisy.isSelected()
						&& !chckbxEnzo.isSelected()) {
					Warning war = new Warning("请勾选至少一个参与者");
					war.setVisible(true);
					b = false;
				}

				if (textField.getText().equals("")) {
					Warning war = new Warning("请选择日期");
					war.setVisible(true);
					b = false;
				}
				try {
					Double.parseDouble(textField_1.getText());
				} catch (Exception e3) {
					Warning war = new Warning("请输入金额");
					war.setVisible(true);
					b = false;
				}

				if (b) {
					SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
					dateStmp = df1.format(date);
					moneyStmp = Double.parseDouble(textField_1.getText());
					participantStmp = new boolean[3];
					participantStmp[0] = chckbxWilliam.isSelected();
					participantStmp[1] = chckbxDaisy.isSelected();
					participantStmp[2] = chckbxEnzo.isSelected();

					int count = 0;
					double spend = 0;
					for (int i = 0; i < 3; i++) {
						if (participantStmp[i] == true) {
							count++;
						}
					}
					double money = moneyStmp / count;

					double[] moneyUsedEarly = new double[3];
					double[] moneySpendedEarly = new double[3];
					int shushu = 0;

					data = new ArrayList<Money>();
					ServerConnector ser = new ServerConnector(
							"12SELECT * FROM Haobanghsou.Money#end");
					try {
						String stempt = ser.Con();
						JsonItems ji = new JsonItems(stempt);
						data = ji.jiexiMoney();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						Warning war = new Warning("Xingzhang.java 错误代码：1");
						war.setVisible(true);
						e1.printStackTrace();
					}
					for (int i = 0; i < 3; i++) {
						moneySpendedEarly[i] = data.get(i).getSpended();
						moneyUsedEarly[i] = data.get(i).getUsed();
					}
					double moneyS1 = moneySpendedEarly[0] + money, moneyS2 = moneySpendedEarly[1]
							+ money, moneyS3 = moneySpendedEarly[2] + money;
					double moneyU1 = moneyUsedEarly[0] + moneyStmp, moneyU2 = moneyUsedEarly[1]
							+ moneyStmp, moneyU3 = moneyUsedEarly[2]
							+ moneyStmp;

					if (participantStmp[0] == true) { // william
						participant += "William ";
						if (User.name.equals("William")) {
							spend = moneyStmp;
							sql = "2UPDATE Haobanghsou.Money SET used="
									+ moneyU1 + " WHERE userId=1#end";
							ser = new ServerConnector(sql);
							try {
								ser.Update();
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								Warning war = new Warning("Xingzhang.java 错误代码：2");
								war.setVisible(true);
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								Warning war = new Warning("Xingzhang.java 错误代码：3");
								war.setVisible(true);
								e1.printStackTrace();
							}

							sql = "2UPDATE Haobanghsou.Money SET spended="
									+ moneyS1 + " WHERE userId=1#end";
							ser = new ServerConnector(sql);
							try {
								ser.Update();
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								Warning war = new Warning("Xingzhang.java 错误代码：4");
								war.setVisible(true);
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								Warning war = new Warning("Xingzhang.java 错误代码：5");
								war.setVisible(true);
								e1.printStackTrace();
							}
						} 
						else if(!User.name.equals("William")){
						sql = "2UPDATE Haobanghsou.Money SET spended="
									+ moneyS1 + " WHERE userId=1#end";
						ser = new ServerConnector(sql);
						try {
							ser.Update();
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							Warning war = new Warning("Xingzhang.java 错误代码：6");
							war.setVisible(true);
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							Warning war = new Warning("Xingzhang.java 错误代码：7");
							war.setVisible(true);
							e1.printStackTrace();
						}
						}
					} 
					
					
					else if(participantStmp[0] == false){
						if (User.name.equals("William")) {
							sql = "2UPDATE Haobanghsou.Money SET used="
									+ moneyU1 + " WHERE userId=1#end";
							ser = new ServerConnector(sql);
							try {
								ser.Update();
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								Warning war = new Warning("Xingzhang.java 错误代码：8");
								war.setVisible(true);
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								Warning war = new Warning("Xingzhang.java 错误代码：9");
								war.setVisible(true);
								e1.printStackTrace();
							}
						}
					}
					
					
					
					if (participantStmp[1] == true) { // daisy
						participant += "Daisy ";
						if (User.name.equals("Daisy")) {
							spend = moneyStmp;

							sql = "2UPDATE Haobanghsou.Money SET used="
									+ moneyU2 + " WHERE userId=2#end";
							ser = new ServerConnector(sql);
							try {
								ser.Update();
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								Warning war = new Warning("Xingzhang.java 错误代码：10");
								war.setVisible(true);
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								Warning war = new Warning("Xingzhang.java 错误代码：11");
								war.setVisible(true);
								e1.printStackTrace();
							}

							sql = "2UPDATE Haobanghsou.Money SET spended="
									+ moneyS2 + " WHERE userId=2#end";
							ser = new ServerConnector(sql);
							try {
								ser.Update();
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								Warning war = new Warning("Xingzhang.java 错误代码：12");
								war.setVisible(true);
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								Warning war = new Warning("Xingzhang.java 错误代码：13");
								war.setVisible(true);
								e1.printStackTrace();
							}
						} else if(!User.name.equals("Daisy")){

							sql = "2UPDATE Haobanghsou.Money SET spended="
									+ moneyS2 + " WHERE userId=2#end";
						ser = new ServerConnector(sql);
						try {
							ser.Update();
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							Warning war = new Warning("Xingzhang.java 错误代码：14");
							war.setVisible(true);
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							Warning war = new Warning("Xingzhang.java 错误代码：15");
							war.setVisible(true);
							e1.printStackTrace();
						}
						}
					} else if(participantStmp[1] == false){
						if (User.name.equals("Daisy")) {
							sql = "2UPDATE Haobanghsou.Money SET used="
									+ moneyU2 + " WHERE userId=2#end";
							ser = new ServerConnector(sql);
							try {
								ser.Update();
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								Warning war = new Warning("Xingzhang.java 错误代码：16");
								war.setVisible(true);
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								Warning war = new Warning("Xingzhang.java 错误代码：17");
								war.setVisible(true);
								e1.printStackTrace();
							}
						}
					}
					
					
					
					if (participantStmp[2] == true) { // enzo
						participant += "Enzo ";
						if (User.name.equals("Enzo")) {
							spend = moneyStmp;

							sql = "2UPDATE Haobanghsou.Money SET used="
									+ moneyU3 + " WHERE userId=3#end";
							ser = new ServerConnector(sql);
							try {
								ser.Update();
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								Warning war = new Warning("Xingzhang.java 错误代码：18");
								war.setVisible(true);
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								Warning war = new Warning("Xingzhang.java 错误代码：19");
								war.setVisible(true);
								e1.printStackTrace();
							}

							sql = "2UPDATE Haobanghsou.Money SET spended="
									+ moneyS3 + " WHERE userId=3#end";
							ser = new ServerConnector(sql);
							try {
								ser.Update();
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								Warning war = new Warning("Xingzhang.java 错误代码：20");
								war.setVisible(true);
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								Warning war = new Warning("Xingzhang.java 错误代码：21");
								war.setVisible(true);
								e1.printStackTrace();
							}
						} else if(!User.name.equals("Enzo")){

							sql = "2UPDATE Haobanghsou.Money SET spended="
									+ moneyS3 + " WHERE userId=3#end";
						ser = new ServerConnector(sql);
						try {
							ser.Update();
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							Warning war = new Warning("Xingzhang.java 错误代码：22");
							war.setVisible(true);
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							Warning war = new Warning("Xingzhang.java 错误代码：23");
							war.setVisible(true);
							e1.printStackTrace();
						}		
						}
					} else if(participantStmp[2] == false){
						if (User.name.equals("Enzo")) {
							sql = "2UPDATE Haobanghsou.Money SET used="
									+ moneyU3 + " WHERE userId=3#end";
							ser = new ServerConnector(sql);
							try {
								ser.Update();
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								Warning war = new Warning("Xingzhang.java 错误代码：26");
								war.setVisible(true);
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								Warning war = new Warning("Xingzhang.java 错误代码：27");
								war.setVisible(true);
								e1.printStackTrace();
							}
						}
					}
					
					sql = "2INSERT INTO Haobanghsou.Shopping(userName,amount,date,participant) VALUES('"
							+ User.name
							+ "',"
							+ moneyStmp
							+ ",'"
							+ dateStmp + "','" + participant + "')#end";
					ser = new ServerConnector(sql);
					try {
						ser.Update();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						Warning war = new Warning("Xingzhang.java 错误代码：24");
						war.setVisible(true);
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						Warning war = new Warning("Xingzhang.java 错误代码：25");
						war.setVisible(true);
						e1.printStackTrace();
					}
					
					setVisible(false);
					MainPanel mainPanel = new MainPanel(frame);
					frame.setContentPane(mainPanel);

				}
			}
		});
		add(btnNewButton_1);

	}
}
