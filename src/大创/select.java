package 大创;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class select extends JFrame
{
	Object a[]={"黑","白","红 ","蓝"};
	Object b[]={"S","L","M"};
	Object c[]={"黑人","鲸鱼"};
	JButton comfirm;
	JButton cancel;
	JPanel p_button; //装按钮
	JPanel pp;
	JComboBox aa;
	JComboBox bb;
	JComboBox cc;
	JTextArea area;
	GridLayout grid;
	public select()
	{
		super("动态演示图");
		aa = new JComboBox(a);
		bb = new JComboBox(b);
		cc = new JComboBox(c);
		comfirm = new JButton("确定");
		cancel = new JButton("取消");
		area = new JTextArea();
		pp = new JPanel(new GridLayout(1,3,5,5));
		p_button = new JPanel(new FlowLayout());
		grid = new GridLayout(2,1);
		pp.add(aa);
		pp.add(bb);
		pp.add(cc);
		p_button.add(comfirm);
		p_button.add(cancel);
		this.setLayout(new BorderLayout());
		this.add(pp,BorderLayout.NORTH);
		this.add(p_button,BorderLayout.SOUTH);
		this.add(area,BorderLayout.CENTER);
		this.setSize(500,400);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	public void selectGUI()
	{
		pp.add(aa);
		pp.add(bb);
		pp.add(cc);
		p_button.add(comfirm);
		p_button.add(cancel);
		this.setLayout(new BorderLayout());
		this.add(pp,BorderLayout.NORTH);
		this.add(p_button,BorderLayout.SOUTH);
		this.add(area,BorderLayout.CENTER);
		this.setSize(500,400);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
