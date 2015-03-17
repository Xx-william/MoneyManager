import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONException;


public class ChangePassword extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField_Name;
	private JTextField textField_OldPassword;
	private JTextField textField_NewPassword;
    private ServerConnector ser;
    private String sql="";
	/**
	 * Create the panel.
	 */
	public ChangePassword(final JFrame frame) {
		setLayout(null);
		
		JLabel label = new JLabel("姓名：");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 23));
		label.setBounds(170, 6, 76, 50);
		add(label);
		
		JLabel label_1 = new JLabel("原密码：");
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 23));
		label_1.setBounds(169, 45, 98, 50);
		add(label_1);
		
		JLabel label_2 = new JLabel("新密码：");
		label_2.setFont(new Font("Lucida Grande", Font.PLAIN, 23));
		label_2.setBounds(169, 88, 98, 50);
		add(label_2);
		
		textField_Name = new JTextField();
		textField_Name.setColumns(10);
		textField_Name.setBounds(264, 13, 148, 35);
		add(textField_Name);
		
		textField_OldPassword = new JTextField();
		textField_OldPassword.setColumns(10);
		textField_OldPassword.setBounds(264, 54, 148, 35);
		add(textField_OldPassword);
		
		textField_NewPassword = new JTextField();
		textField_NewPassword.setColumns(10);
		textField_NewPassword.setBounds(264, 97, 148, 35);
		add(textField_NewPassword);
		
		JButton button_Sure = new JButton("确认");
		button_Sure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName = textField_Name.getText();
				String oldPassword = textField_OldPassword.getText();
				String newPassword = textField_NewPassword.getText();
				String return1 = "";
				boolean b = false;
				sql = "14"+userName + "-" + oldPassword + "#end";
				ser = new ServerConnector(sql);
				try {
					return1 = ser.Con();
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
				}
				else{
					Warning war = new Warning("用户不存在或密码输入错误");
					war.setVisible(true);
				}
				if(b){ //原用户名密码输入正确， 允许修改
					
					sql = "2UPDATE Haobanghsou.User SET userPassWords='" + newPassword
							+ "' WHERE idUser="+User.id+"#end";
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
					Warning war = new Warning("密码修改成功");
					war.setVisible(true);
					
					setVisible(false);
					LogIn login = new LogIn(frame);
					frame.setContentPane(login);
				}
			}
		});
		button_Sure.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		button_Sure.setBounds(144, 150, 148, 43);
		add(button_Sure);
		
		JButton button_Return = new JButton("返回");
		button_Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				LogIn login = new LogIn(frame);
				frame.setContentPane(login);
				
			}
		});
		button_Return.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		button_Return.setBounds(342, 150, 148, 43);
		add(button_Return);

	}
}
