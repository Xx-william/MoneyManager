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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class Xinzhang extends JPanel {
	public JTextField textField_Date;
	private int x, y;
	private Date date = new Date();
	final DateChooser mp = new DateChooser();
	private JTextField textField_MoneyTotal;	
	private ArrayList<Money> moneyData;
	private ArrayList<UserTempt> users;

	private String sql = "";
	private SimpleDateFormat date12 = new SimpleDateFormat("yyyy-MM-dd");
	private String daHaobanghsour12 = date12.format(new Date());
	private JTextField textField_Comment;
	private JTable table;
	private ServerConnector ser;
	private JsonItems ji;

	/**
	 * Create the panel.
	 */
	public Xinzhang(final JFrame frame) {

		JButton button_Return = new JButton("返回");
		button_Return.setBounds(225, 225, 98, 29);
		button_Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				MainPanel mainPanel = new MainPanel(frame);
				frame.setContentPane(mainPanel);
			}
		});
		
		setLayout(null);
		add(button_Return);

		JLabel label = new JLabel("日期");
		label.setBounds(208, 21, 75, 29);
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		add(label);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(261, 102, 140, 123);
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
			data1[i][1] = new Boolean(true);
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
		
		
		textField_Date = new JTextField();
		textField_Date.setBounds(261, 21, 146, 28);
		textField_Date.setEditable(false);
		add(textField_Date);
		textField_Date.setColumns(10);

		daHaobanghsour12 = date12.format(new Date());
		textField_Date.setText(daHaobanghsour12);

		textField_Comment = new JTextField();
		textField_Comment.setBounds(469, 98, 296, 94);
		add(textField_Comment);
		textField_Comment.setColumns(10);

		JLabel label_3 = new JLabel("备注");
		label_3.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		label_3.setBounds(413, 103, 51, 29);
		add(label_3);

		JButton button_Calender = new JButton("日历");
		button_Calender.setBounds(419, 21, 117, 29);
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
						daHaobanghsour12 = date12.format(date);
						textField_Date.setText(daHaobanghsour12);
					}
				});
			}
		});
		add(button_Calender);

		JLabel label_1 = new JLabel("\u91D1\u989D");
		label_1.setBounds(208, 62, 75, 29);
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		add(label_1);

		textField_MoneyTotal = new JTextField();
		textField_MoneyTotal.setBounds(261, 62, 146, 28);
		add(textField_MoneyTotal);
		textField_MoneyTotal.setColumns(10);

		JLabel label_2 = new JLabel("\u4EBA\u5458");
		label_2.setBounds(208, 103, 75, 29);
		label_2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		add(label_2);

		
		
		JButton button_Sure = new JButton("\u786E\u8BA4");
		button_Sure.setBounds(346, 225, 98, 29);
		button_Sure.addActionListener(new ActionListener() {

		
			public void actionPerformed(ActionEvent e) { // button 确认
				boolean b = true; // 判断位
                double moneyTotal = 0; //金额
                double moneySeparate = 0; //每人花费
				

				if (textField_Date.getText().equalsIgnoreCase("")) {
					Warning war = new Warning("请选择日期");
					war.setVisible(true);
					b = false;
				}
				try {
					moneyTotal = Double.parseDouble(textField_MoneyTotal.getText());
				} catch (Exception e3) {
					Warning war = new Warning("请输入金额");
					war.setVisible(true);
					b = false;
				}

				if (b) {
					SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
					String daHaobanghsoump = df1.format(date);
					int numberUsersInTable = users.size();
					Boolean participant = false;
					ArrayList<String> Participants_names = new ArrayList<String>();
					
					for(int i =0 ;i<numberUsersInTable;i++){
						participant = (Boolean)model1.getValueAt(i, 1);
						if(participant){
							String name = (String)model1.getValueAt(i, 0);
							Participants_names.add(name);
						}
					}
                    int[] participants_id = new int[Participants_names.size()];//参与者ID
                    
					for(int i=0;i<Participants_names.size();i++){ 
						for(int j = 0;j<users.size();j++){
							if((Participants_names.get(i).equals(users.get(j).getName()))){
								participants_id[i] = users.get(j).getId();
								System.out.println(users.get(j).getId());
							}
						}
					}
					
					moneySeparate = moneyTotal / Participants_names.size();
					
					moneyData = new ArrayList<Money>();           
					sql = "12SELECT * FROM Haobanghsou.Money#end";    //从服务器获取消费情况存入moneyData
					ser = new ServerConnector(sql);
					try {
						String stempt = ser.Con();
						ji = new JsonItems(stempt);
						moneyData = ji.jiexiMoney();
					} catch (Exception e1) {
						// TODO Auto-generated catch block						
						e1.printStackTrace();
					}
					
                double moneyUsedEarly = 0;                 //从moneyData 获取当前用户使用金额总值存入moneyUsedEarly
                  for(int i =0;i<moneyData.size();i++){
                	  if(User.id == moneyData.get(i).getId()){
                		  moneyUsedEarly = moneyData.get(i).getUsed();
                	  }
                  }
                double moneyUsedNow = moneyUsedEarly + moneyTotal;
                
                sql = "2UPDATE Haobanghsou.Money SET used=" + moneyUsedNow  //更新服务器数据（当前用户使用金额总值）
						+ " WHERE userId="+ User.id+"#end";
                ser = new ServerConnector(sql);
				try {
					ser.Update();
				}catch (UnknownHostException e1) {
					// TODO Auto-generated catch block					
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block					
					e1.printStackTrace();
				}
               
				for(int i = 0;i<Participants_names.size();i++){       
					double participant_spended_early = moneyData.get(participants_id[i]-1).getSpended();
					double participant_spended_now = participant_spended_early + moneySeparate;
					sql = "2UPDATE Haobanghsou.Money SET spended=" + participant_spended_now   //更新所有参与者消费数据；
							+ " WHERE userId="+participants_id[i]+"#end";    
					ser = new ServerConnector(sql);
					
						try {
							ser.Update();
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}					
				}
					
				String participantsStr = "";
				for(int i=0;i<Participants_names.size();i++){      // 将参与者名字数组转换成String
					participantsStr += Participants_names.get(i)+"  ";
				}
				String comment = textField_Comment.getText();
				
				sql = "2INSERT INTO Haobanghsou.Shopping(userName,amount,date,participant,comment) VALUES('"
				+ User.name
				+ "',"
				+ moneyTotal          //将这一单数据上传到服务器
				+ ",'" 
				+ daHaobanghsoump
				+ "','" + participantsStr + "','" + comment + "')#end";
		        ser = new ServerConnector(sql);
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
		        Warning war = new Warning("保存成功");
		        war.setVisible(true);
		        MainPanel mainpanel = new MainPanel(frame);
		        frame.setContentPane(mainpanel);
		        
				}
			}
		});
		add(button_Sure);

		
		
		

	}
}
