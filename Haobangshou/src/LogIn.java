import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONException;

public class LogIn extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField_Name;
	private JTextField textField_PassWord;
	private boolean b = false;

	/**
	 * Create the panel.
	 */
	public LogIn(final JFrame frame) {
		setLayout(null);
		JPanel panel = new JPanel();
		// panel.setBackground(Color.decode("#4c4e4145"));
		panel.setBounds(0, 0, 900, 350);
		add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("\u59D3\u540D\uFF1A");
		label.setBounds(306, 41, 98, 50);
		panel.add(label);
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 23));

		textField_Name = new JTextField();
		textField_Name.setBounds(385, 49, 148, 35);
		panel.add(textField_Name);
		textField_Name.setColumns(10);

		textField_PassWord = new JTextField();
		textField_PassWord.setBounds(385, 132, 148, 35);
		panel.add(textField_PassWord);
		textField_PassWord.setColumns(10);

		JLabel label_1 = new JLabel("\u5BC6\u7801\uFF1A");
		label_1.setBounds(306, 120, 98, 50);
		panel.add(label_1);
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 23));

		JButton button_Login = new JButton("\u767B\u9646");
		button_Login.setBounds(282, 211, 148, 43);
		panel.add(button_Login);

		button_Login.addActionListener(new ActionListener() { //登陆按钮
			public void actionPerformed(ActionEvent e) {
				
				String name = textField_Name.getText();
				String password = textField_PassWord.getText();
				String return1 = "";
				
				ServerConnector ser1 = new ServerConnector("14"+name + "-" + password + "#end");
				try {
					return1 = ser1.Con();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				JsonItems js = new JsonItems(return1);
				try {
					return1 = js.jiexiDenglu();
				} catch (JSONException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				if (return1.equals("true")) {
					b = true;
					
				} else if(return1.equals("false")){
					Warning war = new Warning("用户名/密码 错误");
					war.setVisible(true);
				}
				if (b) {
					User.name = textField_Name.getText();
					User.password = textField_PassWord.getText();					
					System.out.println(User.name);
					System.out.println(User.password);
					System.out.println(User.id);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String date = df.format(new Date());
					ServerConnector ser = new ServerConnector(
							"2UPDATE Haobanghsou.LogInfo SET logTime='" + date
									+ "' WHERE userName='" + User.name
									+ "'#end");
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
					MainPanel mainPanel = new MainPanel(frame);
					frame.setContentPane(mainPanel);
				}
			}
		});
		button_Login.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		
		JButton button_ChangePassword = new JButton("修改密码");
		button_ChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				ChangePassword changePassword = new ChangePassword(frame);
				frame.setContentPane(changePassword);
				
				
			}
		});
		button_ChangePassword.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		button_ChangePassword.setBounds(466, 211, 148, 43);
		panel.add(button_ChangePassword);
		
		JButton button_NewUser = new JButton("新用户");
		button_NewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				NewUserPanel newUserPanel = new NewUserPanel(frame);
				frame.setContentPane(newUserPanel);
				
				
			}
		});
		button_NewUser.setBounds(639, 225, 117, 29);
		panel.add(button_NewUser);

	}
}
