package zeroasan;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class InfoPanel extends JPanel{
	
	private JButton addButton = getAddButton();
	private JButton deleteButton = getDeleteButton();
	private JButton startButton = getStartButton();
	private JButton stopButton = getStopButton();
	private JLabel waitSecond = new JLabel("60");
	private TableModel tableModel = getUserTableModel();
	

	InfoPanel() {
		super(new BorderLayout(10, 10));
		JPanel toolBar = new JPanel(new BorderLayout());
		JPanel toolbarLeft = new JPanel(new FlowLayout(10));
		JPanel toolbarRight = new JPanel(new FlowLayout(10));
		toolbarLeft.add(addButton);
		toolbarLeft.add(deleteButton);
		
		toolbarRight.add(startButton);
		toolbarRight.add(stopButton);
		toolbarRight.add(waitSecond);
		
		toolBar.add(toolbarLeft, BorderLayout.WEST);
		toolBar.add(toolbarRight, BorderLayout.EAST);
		add(toolBar, BorderLayout.NORTH);
		JScrollPane jsPane = new JScrollPane(new JTable(tableModel));
		jsPane.setPreferredSize(new Dimension(750,180));
		add(jsPane, BorderLayout.CENTER);
	}
	

	/**
	 * 开始申请 
	 */
	public void clickStartApplyButton() {}
	
	/**
	 * 添加用户信息到列表中
	 */
	public void clickAddButton() {}
	
	/**
	 * 添加用户信息到列表中
	 */
	public void clickDeleteButton() {}
	
	
	/**----------private methods--------**/
	private JButton getAddButton() {
		JButton button = new JButton("添加");
		
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				clickDeleteButton();
			}
		});
		return button;
	}
	
	private JButton getDeleteButton() {
		JButton button = new JButton("删除");
		
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				clickAddButton();
			}
		});
		return button;
	}
	
	private JButton getStartButton() {
		JButton button = new JButton("开始");
		
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//clickAddButton();
			}
		});
		return button;
	}
	private JButton getStopButton() {
		JButton button = new JButton("停止");
		
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//clickAddButton();
			}
		});
		return button;
	}
	
	private TableModel getUserTableModel() {
		return new UserTableModel(null);
	}
	
}
