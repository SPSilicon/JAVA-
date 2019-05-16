import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.*;

public class Calculator extends JFrame{
	private JPanel panel;
	private JTextField display;
	private JButton[] buttons;
	private String[] labels = { "Backspace", "", "", "CE", "C", "7", "8", "9",
			"/", "sqrt", "4", "5", "6", "*", "%", "1", "2", "3", "-", "1/x",
			"0", "-/+", ".", "+", "=", };
	private double result = 0;
	private String operator = "=";
	private boolean startOfNumber = true;
	private Stack<String> prev = new Stack<String>();
	public Calculator() {
		display = new JTextField(35);
		panel = new JPanel();
		display.setText("0.0");
		panel.setLayout(new GridLayout(0, 5, 3, 3));
		buttons = new JButton[25];
		int index = 0;
		for (int rows = 0; rows < 5; rows++) {
			for (int cols = 0; cols < 5; cols++) {
				buttons[index] = new JButton(labels[index]);
				if (cols >= 3)
					buttons[index].setForeground(Color.red);
				else
					buttons[index].setForeground(Color.blue);
				buttons[index].setBackground(Color.yellow);
				panel.add(buttons[index]);
				buttons[index].addActionListener(new ActionListener() // 무명함수
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						String command = e.getActionCommand(); //버튼의 text가 command로
						if(command.charAt(0) == 'B') //Backspace일때
						{
							if(!prev.isEmpty())
								display.setText(prev.pop());
						}
						else if (command.charAt(0) == 'C') { //C 혹은 CE일때
							startOfNumber = true;
							result = 0;
							operator = "=";
							prev.clear();
							display.setText("0.0");
						} else if (command.charAt(0) >= '0' && command.charAt(0) <= '9'
								&&!command.equals("1/x")|| command.equals(".")) { //숫자 또는 소수점 일때			
							if (startOfNumber == true)	// 처음시작하는 숫자일경우 기존 display를 지우고 시작
							{
								prev.push(" ");
								display.setText(command);				
							}
							else
							{
								prev.push(display.getText());
								display.setText(display.getText() + command);
							}
							startOfNumber = false; //숫자가 입력됫으므로 false
						} else {// CE,E,숫자,소수점도 아니므로 operator일때의 상황
							if (startOfNumber) {	// 처음시작하는 문자가 operator일 경우
								if (command.equals("-")) { // - 일때 display를 전부 지우고 -출력(음수입력을 의미)
									display.setText(command);
									startOfNumber = false;
								} else
									operator = command;
							} else { // 숫자 다음에 operator가 오므로 계산을 수행합니다.
								double x = Double.parseDouble(display.getText());
								 if(command.equals("sqrt")||command.equals("1/x"))
								 {
									 operator = command;
									 calculate(x);
								 }
								 else
								 {
									calculate(x);		//계산
									operator = command;	//입력한 operator를 저장
								 }
								startOfNumber = true;
								prev.clear();
							}
							
						}
						
					}
				});
				index++;
			}
		}
		 //JFrame에 있는 함수 들
		add(display, BorderLayout.NORTH);		
		add(panel, BorderLayout.CENTER);
		setVisible(true);
		pack();
	}

	private void calculate(double n) {
		if (operator.equals("+"))
			result += n;
		else if (operator.equals("-"))
			result -= n;
		else if (operator.equals("*"))
			result *= n;
		else if (operator.equals("/"))
			result /= n;
		else if (operator.equals("="))
			result = n;
		else if (operator.equals("1/x"))
			result = 1/n;
		else if (operator.equals("-/+"))
			result = -n;
		else if (operator.equals("sqrt"))
			result = Math.sqrt(n);
		else if (operator.equals("%"))
			result %= n; 
		display.setText("" + result);
	}
	
	public static void main(String[] args) 
	{
			Calculator cal = new Calculator();
	}
}

