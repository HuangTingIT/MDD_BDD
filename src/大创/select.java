package ��;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class select extends JFrame
{
	Object a[]={"��","��","�� ","��"};
	Object b[]={"S","L","M"};
	Object c[]={"����","����"};
	JButton comfirm;
	JButton cancel;
	JPanel p_button; //װ��ť
	JPanel pp;
	JComboBox aa;
	JComboBox bb;
	JComboBox cc;
	JTextArea area;
	GridLayout grid;
	public select()
	{
		super("��̬��ʾͼ");
		aa = new JComboBox(a);
		bb = new JComboBox(b);
		cc = new JComboBox(c);
		comfirm = new JButton("ȷ��");
		cancel = new JButton("ȡ��");
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
