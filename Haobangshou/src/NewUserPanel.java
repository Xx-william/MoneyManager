import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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


public class NewUserPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField_Name;
	private JTextField textField_Passwords;
	private JTextField textField_PasswordsAgain;
    private String sql = "";
    private ServerConnector ser;
    
	/**
	 * Create the panel.
	 */
	public NewUserPanel(final JFrame frame) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel label = new JLabel("姓名：");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 23));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 4;
		gbc_label.gridy = 1;
		add(label, gbc_label);
		
		textField_Name = new JTextField();
		GridBagConstraints gbc_textField_Name = new GridBagConstraints();
		gbc_textField_Name.anchor = GridBagConstraints.WEST;
		gbc_textField_Name.insets = new Insets(0, 0, 5, 0);
		gbc_textField_Name.gridx = 5;
		gbc_textField_Name.gridy = 1;
		add(textField_Name, gbc_textField_Name);
		textField_Name.setColumns(10);
		
		JLabel label_1 = new JLabel("密码：");
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 23));
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 4;
		gbc_label_1.gridy = 2;
		add(label_1, gbc_label_1);
		
		textField_Passwords = new JTextField();
		GridBagConstraints gbc_textField_Passwords = new GridBagConstraints();
		gbc_textField_Passwords.anchor = GridBagConstraints.WEST;
		gbc_textField_Passwords.insets = new Insets(0, 0, 5, 0);
		gbc_textField_Passwords.gridx = 5;
		gbc_textField_Passwords.gridy = 2;
		add(textField_Passwords, gbc_textField_Passwords);
		textField_Passwords.setColumns(10);
		
		JLabel label_2 = new JLabel("密码确认：");
		label_2.setFont(new Font("Lucida Grande", Font.PLAIN, 23));
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 4;
		gbc_label_2.gridy = 3;
		add(label_2, gbc_label_2);
		
		textField_PasswordsAgain = new JTextField();
		GridBagConstraints gbc_textField_PasswordsAgain = new GridBagConstraints();
		gbc_textField_PasswordsAgain.anchor = GridBagConstraints.WEST;
		gbc_textField_PasswordsAgain.insets = new Insets(0, 0, 5, 0);
		gbc_textField_PasswordsAgain.gridx = 5;
		gbc_textField_PasswordsAgain.gridy = 3;
		add(textField_PasswordsAgain, gbc_textField_PasswordsAgain);
		textField_PasswordsAgain.setColumns(10);
		
		JButton btnNewButton_Sure = new JButton("确认");
		btnNewButton_Sure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean b = false;
				String name = textField_Name.getText();
				String passwords = textField_Passwords.getText();
				String passwordsAgain = textField_PasswordsAgain.getText();
				String return1 = "";
				if(passwords.equals(passwordsAgain)){
					b = true;
				}
				if(!b){
					Warning warning = new Warning("两次输入的密码不一致");
					warning.setVisible(true);
				}
				else if(b){
					ser = new ServerConnector("16" + name + "#end");
					try {
						return1 = ser.Con();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JsonItems js = new JsonItems(return1);
					try {
						return1 = js.jiexiXinyonghu();
					} catch (JSONException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					if(return1.equals("false")){
						b = false;
						Warning war = new Warning("用户已存在!");
						war.setVisible(true);
					}
					
					
				}
				if(b){
					sql = "2INSERT INTO Haobanghsou.User(userName,userPassWords) VALUES('"+name+"','"
							+ passwords +"')#end";
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
						sql = "2INSERT INTO Haobanghsou.Money(userName,used,spended) VALUES('"+name+"',"
								+ 0 + "," + 0 +")#end";
						ser = new ServerConnector(sql);
						try {
							ser.Update();
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							Warning warning = new Warning("注册失败1");
							warning.setVisible(true);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							Warning warning = new Warning("注册失败2");
							warning.setVisible(true);
						}
						setVisible(false);
						LogIn login = new LogIn(frame);
						frame.setContentPane(login);
						Warning warning = new Warning("注册成功");
						warning.setVisible(true);
				}
				
				
				
			}
		});
		GridBagConstraints gbc_btnNewButton_Sure = new GridBagConstraints();
		gbc_btnNewButton_Sure.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_Sure.gridx = 4;
		gbc_btnNewButton_Sure.gridy = 5;
		add(btnNewButton_Sure, gbc_btnNewButton_Sure);
		
		JButton btnNewButton_Cancel = new JButton("取消");
		btnNewButton_Cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				LogIn login = new LogIn(frame);
				frame.setContentPane(login);
				
			}
		});
		GridBagConstraints gbc_btnNewButton_Cancel = new GridBagConstraints();
		gbc_btnNewButton_Cancel.gridx = 5;
		gbc_btnNewButton_Cancel.gridy = 5;
		add(btnNewButton_Cancel, gbc_btnNewButton_Cancel);

	}

}
