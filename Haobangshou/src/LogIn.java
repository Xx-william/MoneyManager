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

public class LogIn extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
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

		textField = new JTextField();
		textField.setBounds(385, 49, 148, 35);
		panel.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(385, 132, 148, 35);
		panel.add(textField_1);
		textField_1.setColumns(10);

		JLabel label_1 = new JLabel("\u5BC6\u7801\uFF1A");
		label_1.setBounds(306, 120, 98, 50);
		panel.add(label_1);
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 23));

		JButton button = new JButton("\u767B\u9646");
		button.setBounds(353, 211, 148, 43);
		panel.add(button);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name = textField.getText();
				String password = textField_1.getText();
				if (name.equalsIgnoreCase(User.name)
						&& password.equalsIgnoreCase(User.password)) {
					b = true;
				} else {
					Warning war = new Warning("用户名/密码 错误");
					war.setVisible(true);
				}
				if (b) {
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
		button.setFont(new Font("Lucida Grande", Font.PLAIN, 17));

	}
}
