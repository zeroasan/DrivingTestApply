package zeroasan;

import javax.swing.table.AbstractTableModel;

public class UserTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -609159678224273183L;
	
	private String[] columnNames = {"","姓名","身份证号","登录名","状态","排序"};  
    private User[] users;  
    
    public UserTableModel(User[] users)
    {  
    	super();
    	this.users = users;  
    }  
    
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	public String getColumnName(int i) {
		return columnNames[i];
	}

	@Override
	public int getRowCount() {
		return 10;
	}
	
	@Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
	
	@Override
	public Class<? extends Object> getColumnClass(int column) {
		if(column == 0) {
			return Boolean.class;
		}
		return String.class;
	}

	@Override
	public String getValueAt(int i, int j) {
		if(users == null || users[i] == null) {
			return null;
		}
		User user = users[i];
		if(j == 0) {
			return user.name;
		}
		if(j == 1) {
			return user.idCard;
		}
		if(j == 2) {
			return user.userId;
		}
		return null;
	}

}
