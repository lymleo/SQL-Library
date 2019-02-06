package library;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Pages {

	private JFrame frame;
	private JTextField textField_Search_ISBN;
	private JTextField textField_Search_BookTitle;
	private JTextField textField_Search_BookAuthor;
	private JTextField textField_Out_ISBN;
	private JTextField textField_Out_Card_ID;
	private JTextField textField_In_bid;
	private JTextField textField_In_cn;
	private JTextField textField_In_bn;
	private JTextField textField_Fname;
	private JTextField textField_Lname;
	private JTextField textField_SSN;
	private JTextField textField_Address;
	private JTextField textField_City;
	private JTextField textField_State;
	private JTextField textField_Phone;
	private JTextField textField_cn;
	private JTable tableAvailability;
	private JTable tableFines;
	private JTable table_Loans;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try 
				{
					Pages window = new Pages();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	 Connection conn = null;
	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public Pages() throws SQLException {
		
		initialize();		
	    conn = DriverManager.getConnection("jdbc:mysql://localhost/LIB", "root", "");
	    String schema = "Use lib;";
	    PreparedStatement s = conn.prepareStatement(schema);
	    s.executeUpdate();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 739, 641);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 37, 663, 532);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel_Search = new JPanel();
		tabbedPane.addTab("Book Search and Availability", null, panel_Search, null);
		panel_Search.setLayout(null);
		
		JLabel lblIsbn = new JLabel("ISBN10");
		lblIsbn.setBounds(217, 30, 84, 14);
		panel_Search.add(lblIsbn);
		
		JLabel lblBookTitle = new JLabel("Book Title");
		lblBookTitle.setBounds(217, 80, 62, 14);
		panel_Search.add(lblBookTitle);
		
		JLabel lblBookAuthor = new JLabel("Book Author");
		lblBookAuthor.setBounds(217, 130, 84, 14);
		panel_Search.add(lblBookAuthor);
		
		textField_Search_ISBN = new JTextField();
		textField_Search_ISBN.setBounds(334, 27, 86, 20);
		panel_Search.add(textField_Search_ISBN);
		textField_Search_ISBN.setColumns(10);
		
		textField_Search_BookTitle = new JTextField();
		textField_Search_BookTitle.setBounds(334, 77, 86, 20);
		panel_Search.add(textField_Search_BookTitle);
		textField_Search_BookTitle.setColumns(10);
		
		textField_Search_BookAuthor = new JTextField();
		textField_Search_BookAuthor.setBounds(334, 127, 86, 20);
		panel_Search.add(textField_Search_BookAuthor);
		textField_Search_BookAuthor.setColumns(10);

		JButton btnSearchBook = new JButton("Search Book");
		btnSearchBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					String ISBN = textField_Search_ISBN.getText();
					String title = textField_Search_BookTitle.getText();
					String author = textField_Search_BookAuthor.getText();
					String Search = null;
					java.sql.Statement st = conn.createStatement();
					ResultSet rs = null;
					if(ISBN.equals("") && title.equals("") && author.equals(""))
						JOptionPane.showMessageDialog(null, "Fill at leat one field");
					else
					{
						if(!ISBN.equals("") && !title.equals("") && !author.equals(""))
						{
							Search = (" SELECT  BOOK.ISBN  as ISBN10, BOOK.TITLE as Title, group_concat(TEMP.NAME) as Author, BOOK.Availability  FROM BOOK, (SELECT authors.Name as name, authors.Author_id, book_authors.Isbn FROM BOOK_AUTHORS LEFT JOIN AUTHORS ON BOOK_AUTHORS.AUTHOR_ID = AUTHORS.AUTHOR_ID) AS TEMP WHERE BOOK.ISBN = TEMP.ISBN and book.isbn like '%"+ ISBN +"%' and BOOK.TITLE like '%" + title + "%' and Temp.Name like '%"+ author +"%' group by book.ISBN;");
							rs = st.executeQuery(Search);

							tableAvailability.setModel(DbUtils.resultSetToTableModel(rs));
							
						}
						else if (ISBN.equals("") && !title.equals("") && !author.equals(""))
						{
							Search = (" SELECT  BOOK.ISBN as ISBN10, BOOK.TITLE  as Title, group_concat(TEMP.NAME) as Author, BOOK.Availability FROM BOOK, (SELECT authors.Name as name, authors.Author_id, book_authors.Isbn FROM BOOK_AUTHORS LEFT JOIN AUTHORS ON BOOK_AUTHORS.AUTHOR_ID = AUTHORS.AUTHOR_ID) AS TEMP WHERE BOOK.ISBN = TEMP.ISBN and  BOOK.TITLE like '%" + title + "%' and Temp.Name like '%"+ author +"%' group by book.ISBN;");
							rs = st.executeQuery(Search);

							tableAvailability.setModel(DbUtils.resultSetToTableModel(rs));
						}
						else if(!ISBN.equals("") && title.equals("") && !author.equals(""))
						{
							Search = (" SELECT  BOOK.ISBN  as ISBN10, BOOK.TITLE  as Title, group_concat(TEMP.NAME) as Author, BOOK.Availability FROM BOOK, (SELECT authors.Name as name, authors.Author_id, book_authors.Isbn FROM BOOK_AUTHORS LEFT JOIN AUTHORS ON BOOK_AUTHORS.AUTHOR_ID = AUTHORS.AUTHOR_ID) AS TEMP WHERE BOOK.ISBN = TEMP.ISBN and book.isbn like '%"+ ISBN +"%' and Temp.Name like '%"+ author +"%' group by book.ISBN;");
							rs = st.executeQuery(Search);

							tableAvailability.setModel(DbUtils.resultSetToTableModel(rs));
							
						}
						else if(!ISBN.equals("") && !title.equals("") && author.equals(""))
						{
							Search = (" SELECT  BOOK.ISBN  as ISBN10, BOOK.TITLE  as Title, group_concat(TEMP.NAME) as Author, BOOK.Availability FROM BOOK, (SELECT authors.Name as name, authors.Author_id, book_authors.Isbn FROM BOOK_AUTHORS LEFT JOIN AUTHORS ON BOOK_AUTHORS.AUTHOR_ID = AUTHORS.AUTHOR_ID) AS TEMP WHERE BOOK.ISBN = TEMP.ISBN and book.isbn like '%"+ ISBN +"%' and BOOK.TITLE like '%" + title + "%'  group by book.ISBN;");
							rs = st.executeQuery(Search);

							tableAvailability.setModel(DbUtils.resultSetToTableModel(rs));
							
						}
						else if (ISBN.equals("") && title.equals("") && !author.equals(""))
						{
							Search = (" SELECT  BOOK.ISBN  as ISBN10, BOOK.TITLE  as Title, group_concat(TEMP.NAME) as Author, BOOK.Availability FROM BOOK, (SELECT authors.Name as name, authors.Author_id, book_authors.Isbn FROM BOOK_AUTHORS LEFT JOIN AUTHORS ON BOOK_AUTHORS.AUTHOR_ID = AUTHORS.AUTHOR_ID) AS TEMP WHERE BOOK.ISBN = TEMP.ISBN and  Temp.Name like '%"+ author +"%' group by book.ISBN;");
							rs = st.executeQuery(Search);

							tableAvailability.setModel(DbUtils.resultSetToTableModel(rs));
							
						}
						else if(ISBN.equals("") && !title.equals("") && author.equals(""))
						{
							Search = (" SELECT  BOOK.ISBN  as ISBN10, BOOK.TITLE as Title, group_concat(TEMP.NAME) as Author, BOOK.Availability  FROM BOOK, (SELECT authors.Name as name, authors.Author_id, book_authors.Isbn FROM BOOK_AUTHORS LEFT JOIN AUTHORS ON BOOK_AUTHORS.AUTHOR_ID = AUTHORS.AUTHOR_ID) AS TEMP WHERE BOOK.ISBN = TEMP.ISBN and BOOK.TITLE like '%" + title + "%' group by book.ISBN;");
							rs = st.executeQuery(Search);

							tableAvailability.setModel(DbUtils.resultSetToTableModel(rs));
							
						}
						else if(!ISBN.equals("") && title.equals("") && author.equals(""))
						{
							Search = (" SELECT  BOOK.ISBN  as ISBN10, BOOK.TITLE as Title, group_concat(TEMP.NAME) as Author, BOOK.Availability  FROM BOOK, (SELECT authors.Name as name, authors.Author_id, book_authors.Isbn FROM BOOK_AUTHORS LEFT JOIN AUTHORS ON BOOK_AUTHORS.AUTHOR_ID = AUTHORS.AUTHOR_ID) AS TEMP WHERE BOOK.ISBN = TEMP.ISBN and book.isbn like '%"+ ISBN +"%' group by book.ISBN;");
							rs = st.executeQuery(Search);

							tableAvailability.setModel(DbUtils.resultSetToTableModel(rs));
							
						}
						else 
							JOptionPane.showMessageDialog(null, "Please provide information on at least one item!");
					}
				}
				catch(Exception e3)
				{
					JOptionPane.showMessageDialog(null, e3);
				}
			}
		});
		btnSearchBook.setBounds(184, 184, 124, 23);
		panel_Search.add(btnSearchBook);
		
		JButton btnClearResult = new JButton("Clear Result");
		btnClearResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model=(DefaultTableModel)tableAvailability.getModel();
				model.setRowCount(0);
			}
		});
		btnClearResult.setBounds(371, 184, 124, 23);
		panel_Search.add(btnClearResult);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 218, 658, 275);
		panel_Search.add(scrollPane);
		
		tableAvailability = new JTable();
		scrollPane.setViewportView(tableAvailability);
		
		JTabbedPane tabbedPane_Book_Loans = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Book Loans", null, tabbedPane_Book_Loans, null);
		
		JPanel panel_Out = new JPanel();
		tabbedPane_Book_Loans.addTab("Checking Out Books", null, panel_Out, null);
		panel_Out.setLayout(null);
		
		JLabel lblIsbn_1 = new JLabel("ISBN10");
		lblIsbn_1.setBounds(42, 61, 46, 14);
		panel_Out.add(lblIsbn_1);
		
		JLabel lblCardId = new JLabel("Card ID");
		lblCardId.setBounds(42, 156, 46, 14);
		panel_Out.add(lblCardId);
		
		textField_Out_ISBN = new JTextField();
		textField_Out_ISBN.setBounds(108, 55, 86, 20);
		panel_Out.add(textField_Out_ISBN);
		textField_Out_ISBN.setColumns(10);
		
		textField_Out_Card_ID = new JTextField();
		textField_Out_Card_ID.setBounds(108, 153, 86, 20);
		panel_Out.add(textField_Out_Card_ID);
		textField_Out_Card_ID.setColumns(10);
		
		JButton btnCheckOutThe = new JButton("Check out Book");
		btnCheckOutThe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					String ISBN = textField_Out_ISBN.getText();
					String Card_ID = textField_Out_Card_ID.getText();
					int bookCanHold = -1;
					String checkBookAvailability = ("SELECT AVAILABILITY FROM BOOK WHERE ISBN = ?");
					PreparedStatement pst = conn.prepareStatement(checkBookAvailability);
					pst.setString(1, ISBN);
					ResultSet rs = pst.executeQuery();
					int Availability = -1;
					if(rs.next())
						Availability = rs.getInt(1);
					if (Availability == 0)
						JOptionPane.showMessageDialog(null, "No Available Book");
					else if (Availability == 1 )
					{
						String CheckBorrowed = ("SELECT AVAILABILITY FROM BORROWER WHERE CARD_ID = ?");
						pst = conn.prepareStatement(CheckBorrowed);
						pst.setString(1, Card_ID);
						rs = pst.executeQuery();
						if (rs.next())
							bookCanHold = rs.getInt(1);
						if (bookCanHold < 1)
							JOptionPane.showMessageDialog(null, "You can not hold more than 3 books");
						else
						{
							String checkOut =("INSERT INTO BOOK_LOANS(Isbn, Card_id, Date_out, Due_date) VALUE(?,?, CURDATE(), ADDDATE(CURDATE(), INTERVAL 14 DAY))");
							pst = conn.prepareStatement(checkOut);				
							pst.setString(1, ISBN);
							pst.setString(2, Card_ID);pst.executeUpdate();
							String out = ("UPDATE BOOK SET Availability = Availability - 1 where ISBN = ?");
							pst = conn.prepareStatement(out);
							pst.setString(1, ISBN);
							pst.executeUpdate();
							String updateHold = ("UPDATE BORROWER SET AVAILABILITY = AVAILABILITY - 1 WHERE Card_ID = ?");
							pst = conn.prepareStatement(updateHold);
							pst.setString(1, Card_ID);
							pst.executeUpdate();
							JOptionPane.showMessageDialog(null, "Check out success!");
						}
					}
					else
						JOptionPane.showMessageDialog(null, "No such book exists!");
					
				
				}
				catch(Exception e2){
				JOptionPane.showMessageDialog(null, e2);
				}
			}
		});
		btnCheckOutThe.setBounds(108, 236, 136, 28);
		panel_Out.add(btnCheckOutThe);
		
		JPanel panel_In = new JPanel();
		tabbedPane_Book_Loans.addTab("Checking In Books ", null, panel_In, null);
		panel_In.setLayout(null);
		
		JLabel lblBookId = new JLabel("BOOK ID");
		lblBookId.setBounds(45, 33, 122, 14);
		panel_In.add(lblBookId);
		
		JLabel lblCardNumber = new JLabel("Card Number");
		lblCardNumber.setBounds(45, 86, 122, 14);
		panel_In.add(lblCardNumber);
		
		JLabel lblBorrowerName = new JLabel("Borrower Name");
		lblBorrowerName.setBounds(45, 139, 122, 14);
		panel_In.add(lblBorrowerName);
		
		textField_In_bid = new JTextField();
		textField_In_bid.setBounds(177, 30, 115, 20);
		panel_In.add(textField_In_bid);
		textField_In_bid.setColumns(10);
		
		textField_In_cn = new JTextField();
		textField_In_cn.setBounds(177, 83, 115, 20);
		panel_In.add(textField_In_cn);
		textField_In_cn.setColumns(10);
		
		textField_In_bn = new JTextField();
		textField_In_bn.setBounds(177, 136, 115, 20);
		panel_In.add(textField_In_bn);
		textField_In_bn.setColumns(10);
		
		JButton btnNewButton = new JButton("Search Book");
		btnNewButton.setBounds(414, 29, 120, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String Book_id = textField_In_bid.getText();
					String Card_id = textField_In_cn.getText();
					String Borrower_Name= textField_In_bn.getText();
					String Search = null;
					java.sql.Statement st = conn.createStatement();
					ResultSet rs = null;
					if( Book_id.equals("") && Card_id.equals("") && Borrower_Name.equals(""))
						JOptionPane.showMessageDialog(null, "Fill at leat one field");
					else
					{
						if( !Book_id.equals("") && !Card_id.equals("") && !  Borrower_Name.equals(""))
						{
							Search = ("SELECT book_loans.Isbn as ISBN10, book_loans.Card_id as Card_ID, borrower.Bname as Borrower_Name FROM book_loans, borrower where Date_in is null AND book_loans.Card_id = borrower.Card_id AND book_loans.Isbn LIKE '%" + Book_id +  "%' AND book_loans.Card_id LIKE '%" + Card_id + "%' AND borrower.Bname LIKE '%" + Borrower_Name + "%';");
							rs = st.executeQuery(Search);
							table_Loans.setModel(DbUtils.resultSetToTableModel(rs));
						}
						else if( Book_id.equals("") && !Card_id.equals("") && !  Borrower_Name.equals(""))
						{
							Search = ("SELECT book_loans.Isbn as ISBN10, book_loans.Card_id as Card_ID, borrower.Bname as Borrower_Name FROM book_loans, borrower where Date_in is null AND book_loans.Card_id = borrower.Card_id AND book_loans.Card_id LIKE '%" + Card_id + "%' AND borrower.Bname LIKE '%" + Borrower_Name + "%';");
							rs = st.executeQuery(Search);
							table_Loans.setModel(DbUtils.resultSetToTableModel(rs));
						}
						else if( !Book_id.equals("") && Card_id.equals("") && !  Borrower_Name.equals(""))
						{
							Search = ("SELECT book_loans.Isbn as ISBN10, book_loans.Card_id as Card_ID, borrower.Bname as Borrower_Name FROM book_loans, borrower where Date_in is null AND book_loans.Card_id = borrower.Card_id AND book_loans.Isbn LIKE '%" + Book_id +  "%' AND borrower.Bname LIKE '%" + Borrower_Name + "%';");
							rs = st.executeQuery(Search);
							table_Loans.setModel(DbUtils.resultSetToTableModel(rs));
						}
						else if( !Book_id.equals("") && !Card_id.equals("") &&  Borrower_Name.equals(""))
						{
							Search = ("SELECT book_loans.Isbn as ISBN10, book_loans.Card_id as Card_ID, borrower.Bname as Borrower_Name FROM book_loans, borrower where Date_in is null AND book_loans.Card_id = borrower.Card_id AND book_loans.Isbn LIKE '%" + Book_id +  "%' AND book_loans.Card_id LIKE '%" + Card_id + "%';");
							rs = st.executeQuery(Search);
							table_Loans.setModel(DbUtils.resultSetToTableModel(rs));
						}
						
						/////
						else if( Book_id.equals("") && Card_id.equals("") && !  Borrower_Name.equals(""))
						{
							Search = ("SELECT book_loans.Isbn as ISBN10, book_loans.Card_id as Card_ID, borrower.Bname as Borrower_Name FROM book_loans, borrower where Date_in is null AND book_loans.Card_id = borrower.Card_id AND  borrower.Bname LIKE '%" + Borrower_Name + "%';");
							rs = st.executeQuery(Search);
							table_Loans.setModel(DbUtils.resultSetToTableModel(rs));
						}			
						else if( Book_id.equals("") && !Card_id.equals("") && Borrower_Name.equals(""))
						{
							Search = ("SELECT book_loans.Isbn as ISBN10, book_loans.Card_id as Card_ID, borrower.Bname as Borrower_Name FROM book_loans, borrower where Date_in is null AND book_loans.Card_id = borrower.Card_id AND  book_loans.Card_id LIKE '%" + Card_id + "%';");
							rs = st.executeQuery(Search);
							table_Loans.setModel(DbUtils.resultSetToTableModel(rs));
						}
						else if( !Book_id.equals("") && Card_id.equals("") &&  Borrower_Name.equals(""))
						{
							Search = ("SELECT book_loans.Isbn as ISBN10, book_loans.Card_id as Card_ID, borrower.Bname as Borrower_Name FROM book_loans, borrower where Date_in is null AND book_loans.Card_id = borrower.Card_id AND book_loans.Isbn LIKE '%" + Book_id +  "%';");
							rs = st.executeQuery(Search);
							table_Loans.setModel(DbUtils.resultSetToTableModel(rs));
						}
					}	
				}
				catch(Exception e4)
				{
					JOptionPane.showMessageDialog(null,e4);
				}
			}
		});
		panel_In.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Clear Result");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model=(DefaultTableModel)table_Loans.getModel();
				model.setRowCount(0);
			}
		});
		btnNewButton_1.setBounds(414, 82, 120, 23);
		panel_In.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Check In");
		btnNewButton_2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					int row = table_Loans.getSelectedRow();
					table_Loans.getSelectionModel() ;
					String F_ISBN = (table_Loans.getModel().getValueAt(row, 0)).toString();
					String F_cID = (table_Loans.getModel().getValueAt(row, 1)).toString();
					
					PreparedStatement pst = null;
					
					String query = "SELECT Date_in from book_loans WHERE Card_id = ?  AND ISBN = ? ";
					pst = conn.prepareStatement(query);
					pst.setString(1,  F_cID);
					pst.setString(2, F_ISBN);
					ResultSet rs = pst.executeQuery();
					if (!rs.next() )
						JOptionPane.showMessageDialog(null, "Book is not borrowed");
					else
					{
						String in = "UPDATE book_loans SET Date_in = CURDATE()  WHERE Card_id = ?  AND ISBN = ? ";
						pst = conn.prepareStatement(in);
						pst.setString(1,  F_cID);
						pst.setString(2, F_ISBN);
						pst.executeUpdate();
						in = "UPDATE BOOK SET AVAILABILITY  = 1  WHERE ISBN = ?";
						pst = conn.prepareStatement(in);
						pst.setString(1, F_ISBN);
						pst.executeUpdate();
						String holdAvai = "UPDATE BORROWER SET AVAILABILITY = AVAILABILITY + 1 WHERE CARD_ID = ?";
						pst = conn.prepareStatement(holdAvai);
						pst.setString(1, F_cID);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Check in sucess!");
					}
					
				}
				catch(Exception e3)
				{
					JOptionPane.showMessageDialog(null, e3);
				}
							
			}
		});
		btnNewButton_2.setBounds(414, 139, 120, 23);
		panel_In.add(btnNewButton_2);
		
		JScrollPane scrollPane_In = new JScrollPane();
		scrollPane_In.setBounds(10, 173, 653, 280);
		panel_In.add(scrollPane_In);
		table_Loans = new JTable();
		table_Loans.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		scrollPane_In.setViewportView(table_Loans);
		
		JLabel lblToRefreshClear = new JLabel("* To refresh, clear result and search agin");
		lblToRefreshClear.setBounds(338, 0, 272, 14);
		panel_In.add(lblToRefreshClear);
		
		JPanel panel_Borrower_Management = new JPanel();
		tabbedPane.addTab("Borrower Management", null, panel_Borrower_Management, null);
		panel_Borrower_Management.setLayout(null);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(53, 45, 70, 14);
		panel_Borrower_Management.add(lblFirstName);
		
		JLabel lblLastbName = new JLabel("Last Name");
		lblLastbName.setBounds(53, 85, 70, 14);
		panel_Borrower_Management.add(lblLastbName);
		
		JLabel lblSsn = new JLabel("SSN");
		lblSsn.setBounds(53, 125, 46, 14);
		panel_Borrower_Management.add(lblSsn);
		
		textField_Fname = new JTextField();
		textField_Fname.setBounds(145, 42, 93, 20);
		panel_Borrower_Management.add(textField_Fname);
		textField_Fname.setColumns(10);
		
		textField_Lname = new JTextField();
		textField_Lname.setBounds(145, 82, 93, 20);
		panel_Borrower_Management.add(textField_Lname);
		textField_Lname.setColumns(10);
		
		textField_SSN = new JTextField();
		textField_SSN.setBounds(145, 122, 93, 20);
		panel_Borrower_Management.add(textField_SSN);
		textField_SSN.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(53, 165, 82, 14);
		panel_Borrower_Management.add(lblAddress);
		
		JLabel lblNewLabel = new JLabel("City");
		lblNewLabel.setBounds(53, 205, 46, 14);
		panel_Borrower_Management.add(lblNewLabel);
		
		JLabel lblState = new JLabel("State");
		lblState.setBounds(53, 245, 46, 14);
		panel_Borrower_Management.add(lblState);
		
		textField_Address = new JTextField();
		textField_Address.setBounds(145, 162, 93, 20);
		panel_Borrower_Management.add(textField_Address);
		textField_Address.setColumns(10);
		
		textField_City = new JTextField();
		textField_City.setBounds(145, 202, 93, 20);
		panel_Borrower_Management.add(textField_City);
		textField_City.setColumns(10);
		
		textField_State = new JTextField();
		textField_State.setBounds(145, 242, 93, 20);
		panel_Borrower_Management.add(textField_State);
		textField_State.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Phone");
		lblNewLabel_1.setBounds(53, 285, 46, 14);
		panel_Borrower_Management.add(lblNewLabel_1);
		
		textField_Phone = new JTextField();
		textField_Phone.setBounds(145, 282, 93, 20);
		panel_Borrower_Management.add(textField_Phone);
		textField_Phone.setColumns(10);
		
		JButton btnNewBorrower = new JButton("Create New Borrower");
		
	
		//Insert new record to Borrower Table;
		btnNewBorrower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					String Card_ID = null;
					String Name = textField_Fname.getText() + " " + textField_Lname.getText();
					String SSN = textField_SSN.getText();
					//full Address combines address, city, and state together;
					String Address = textField_Address.getText() + " " + textField_City.getText() + " " + textField_State.getText();
					String Phone = textField_Phone.getText();
					
					//check all text field is filled in
					if (! textField_Fname.getText().equals("") && ! textField_Lname.getText().equals("") 
							&& ! textField_SSN.getText().equals("") 
							&& ! textField_Address.getText().equals("") && !textField_City.getText().equals("") && !textField_State.getText().equals("")
							&& ! textField_Phone.getText().equals(""))
					{
						String getMaxID= "SELECT MAX(Card_id) FROM BORROWER";
						PreparedStatement pst = conn.prepareStatement(getMaxID);
						ResultSet rs = pst.executeQuery();
						if(rs.next())
							Card_ID = String.format("%06d", ((rs.getInt(1) + 1)));						 
						String newBorrower = "INSERT INTO BORROWER VALUE (?, ?, ?, ?, ?, ?)"; 
						pst = conn.prepareStatement(newBorrower);
						
						pst.setString(1, Card_ID);
						pst.setString(2, SSN);
						pst.setString(3, Name);
						pst.setString(4, Address);
						pst.setString(5, Phone);
						pst.setInt(6, 3);
						pst.executeUpdate();
						
						JOptionPane.showMessageDialog(null, "Success");
						
					}
					
					else JOptionPane.showMessageDialog(null, "Information is incomplete!");
					
					
				} 
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnNewBorrower.setBounds(53, 350, 185, 23);
		panel_Borrower_Management.add(btnNewBorrower);
		
		JPanel panel_Fines = new JPanel();
		tabbedPane.addTab("Fines", null, panel_Fines, null);
		panel_Fines.setLayout(null);
		
		JLabel lblCardNumber_1 = new JLabel("Card Number");
		lblCardNumber_1.setBounds(29, 52, 89, 14);
		panel_Fines.add(lblCardNumber_1);
		
		textField_cn = new JTextField();
		textField_cn.setBounds(128, 49, 115, 20);
		panel_Fines.add(textField_cn);
		textField_cn.setColumns(10);
		
		JButton btnRefreshFine = new JButton("Refresh Fines");
		btnRefreshFine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					String s1 = "INSERT IGNORE INTO fines (fines.Loan_id)  SELECT book_loans.Loan_id from book_loans where Due_date < curdate()";
					String s2 = "update FINES, BOOK_LOANS SET Fine_amt =Datediff(curdate(), BOOK_LOANS.Due_date)*0.25 where BOOK_LOANS.loan_id=FINES.loan_id and paid = 0 and Date_in IS NULL";
					String s3 = "UPDATE FINES,BOOK_LOANS SET FINES.fine_amt=Datediff(BOOK_LOANS.Date_in,BOOK_LOANS.Due_date)*0.25 WHERE BOOK_LOANS.loan_id=FINES.loan_id AND FINES.paid=0 AND Date_in IS NOT NULL;";
					String s4 = "UPDATE FINES SET fine_amt=0.00 WHERE fine_amt<0.00";
					PreparedStatement pst = conn.prepareStatement(s1);
					pst.executeUpdate(s1);
					pst = conn.prepareStatement(s2);
					pst.executeUpdate(s2);
					pst = conn.prepareStatement(s3);
					pst.executeUpdate(s3);
					pst = conn.prepareStatement(s4);
					pst.executeUpdate(s4);					
					JOptionPane.showMessageDialog(null, "Fines Updated");
				}
				catch(Exception e5)
				{
					JOptionPane.showMessageDialog(null, e5);
				}
			}
		});
		btnRefreshFine.setBounds(420, 48, 120, 23);
		panel_Fines.add(btnRefreshFine);
		
		JLabel lblPleaseRefresh = new JLabel("* Please refresh fines before checking or paying fines ");
		lblPleaseRefresh.setBounds(0, 482, 332, 14);
		panel_Fines.add(lblPleaseRefresh);
		
		JButton btnPayFine = new JButton("Pay Fine");
		btnPayFine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					int row = tableFines.getSelectedRow();
					tableFines.getSelectionModel();
					String cid = (tableFines.getModel().getValueAt(row, 0)).toString();
					String update = "Update fines, book_loans set Fine_amt = 0.00, paid = 1 where Card_id = ? and BOOK_LOANS.loan_id = FINES.loan_id AND Date_in IS NOT NULL;";
					PreparedStatement pst = conn.prepareStatement(update);
					pst.setString(1, cid);
					pst.executeUpdate();
				}
				catch(Exception e6)
				{
					JOptionPane.showMessageDialog(null, "No fines could be paid, please check in the book first");
				}
			}
		});
		btnPayFine.setBounds(420, 117, 120, 23);
		panel_Fines.add(btnPayFine);
		
		JButton btnCheckFine = new JButton("Check Fine");
		btnCheckFine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					String card_Number = textField_cn.getText();
					String query = "select card_id, fine_amt from book_loans, fines where book_loans.Loan_id = fines.Loan_id and paid = 0 and  Card_id = ?";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1, card_Number);
					ResultSet rs = pst.executeQuery();
					tableFines.setModel(DbUtils.resultSetToTableModel(rs));
					JOptionPane.showMessageDialog(null, "Fines checked");
				}
				catch(Exception e5)
				{
					JOptionPane.showMessageDialog(null, e5);
				}
			}
		});
		btnCheckFine.setBounds(128, 117, 120, 23);
		panel_Fines.add(btnCheckFine);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 173, 658, 298);
		panel_Fines.add(scrollPane_1);
		
		tableFines = new JTable();
		scrollPane_1.setViewportView(tableFines);
	}
}
