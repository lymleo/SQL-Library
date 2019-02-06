package library;
import java.util.Scanner;



import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReadData 
{	

	
	public static void main(String[] args) throws IOException
	{		
		//read books.csv
		BufferedReader reader =  new BufferedReader(new FileReader("books.csv"));
		Scanner record = null;
		int index = 0;
		String line = null;
		List<Book> bookList = new ArrayList<>();
		
		while ((line = reader.readLine())!= null)
		{
			Book bi = new Book();
			record = new Scanner(line);
			record.useDelimiter("	");
			while (record.hasNext())
			{
				String data = record.next();
				if (index == 0)
					bi.setISBN10(data);
				else if(index == 1)
					bi.setISBN13(data);
				else if (index == 2)
				{
					//data = data.replaceAll("'", "''");
					bi.setTitle(data);
				}
				else if (index == 3)
					bi.setAuthor(data);
				else if (index ==4)
					bi.setCover(data);
				else if (index ==5)
				{
					
					bi.setPublisher(data);
				}
				else if (index ==6)
					bi.setPages(data);
				else System.out.println("Invalid record");
				index++;
			}
			index = 0;
			bookList.add(bi);			
		}
		reader.close();
		//read books ends
		
		//read borrowers.csv
		reader = new BufferedReader(new FileReader("borrowers.csv"));
		record = null;
		index = 0;
		line = null;
		List<Borrower> borrowerList = new ArrayList<>();
		
		while((line = reader.readLine()) != null)
		{
			Borrower bowrrower = new Borrower();
			record = new Scanner(line);
			record.useDelimiter(",");
			
			String Name = null;
			String Address = null;
			while(record.hasNext())
			{
				String data = record.next();
				
				if (index == 0)
					bowrrower.setID(data);
				else if (index == 1)
				{
					data = data.replaceAll("-", "");
					bowrrower.setSSN(data);
				}
				else if (index == 2)
					Name = data;
				else if (index == 3)
				{
					Name = Name +" " + data;
					bowrrower.setName(Name);
				}
				else if (index == 4)
					bowrrower.setEmail(data);
				else if (index == 5)
					Address = data; 
				else if (index == 6)
					Address = Address + " " + data;
				else if (index == 7)
				{
					Address = Address + " " + data;
					bowrrower.setAddress(Address);
				}
				else if (index == 8)
				{
					data = data.replaceAll("[\\(\\s\\)-]", "");
					bowrrower.setPhone(data);
				}
				else System.out.println("Invalid record");
				index++;
			}
			index = 0;
			borrowerList .add(bowrrower);			
		}
		reader.close();
	    //read Borrowers ends
		Connection conn = null;
		
		String ISBN = null;
		String Title = null;
		String Author = null;
		//String Cover = null;
		//String Publisher = null;
		//String Pages = null;
		String borrower_ID = null;
		String SSN = null;
		String Name = null;
		//String Email = null;
		String Address = null;
		String Phone = null;

		//System.out.println(bookList.size());
		
		
		///*		
		try
		{
			//make java-mysql connection
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost/LIB?useSSL=false", "root", "");
			
			//then manipulate on books.csv data
			
			//commands inserts table lib.book
			String 	insertBOOK = "INSERT IGNORE INTO BOOK VALUE (?,?)";
			PreparedStatement s1 = conn.prepareStatement(insertBOOK);
			
			//commands inserts table lib.authors, here we return the last inserted author_id
			String insertAuthors = "INSERT IGNORE INTO AUTHORS(Name) VALUE (?)";
			PreparedStatement s2 = conn.prepareStatement(insertAuthors, Statement.RETURN_GENERATED_KEYS);
			
			//commands inserts table lib.book_authors
			String insertsBOOK_AUTHORS = "insert IGNORE INTO BOOK_AUTHORS VALUE (?,?)";
			PreparedStatement s3 = conn.prepareStatement(insertsBOOK_AUTHORS);

			//catch book data 			
			for (int i = 1; i< bookList.size(); i++)
			{
				ISBN = bookList.get(i).getISBN10();
				Title = bookList.get(i).getTitle();
				Author = bookList.get(i).getAuthor();
				//Cover = bookList.get(i).getCover();
				//Publisher = bookList.get(i).getCover();
				//Pages = bookList.get(i).getPages();
				
				//split co-authors
				String[] Auths = Author.split("[,;]");
							
				//Insert table lib.book
				s1.setString(1,ISBN);
				s1.setString(2, Title);
				s1.executeUpdate();
				///*
				//Insert table lib.authors and BOOK_AUTHORS
				for (int j = 0 ; j < Auths.length; j++)
				{
					s2.setString(1, Auths[j]);
					s2.executeUpdate();
					///*
					ResultSet rs = s2.getGeneratedKeys();
					if (rs.next())
					{
						String Author_id = rs.getString(1); 					
						s3.setString(1, Author_id);
						s3.setString(2, ISBN);
						s3.executeUpdate();
					}
					else System.out.println("error in generating BOOK_AUTHORS");
					//*/
				}
				//*/
			} 	
			
			//manipulate on borrowers.csv data
			//commands insert lib.borrower
			String 	insertBORROWER = "INSERT IGNORE INTO BORROWER  VALUE (?,?,?,?,?)";
			PreparedStatement s4 = conn.prepareStatement(insertBORROWER);
			
			for (int i = 1; i< borrowerList.size();i++)
			{
				borrower_ID = borrowerList.get(i).getborrower_ID();
				SSN =  borrowerList.get(i).getSSN();
				Name = borrowerList.get(i).getName();
				Address = borrowerList.get(i).getAddress();
				Phone = borrowerList.get(i).getPhone();
				s4.setString(1, borrower_ID);
				s4.setString(2, SSN);
				s4.setString(3, Name);
				s4.setString(4, Address);
				s4.setString(5, Phone);
				s4.executeUpdate();
			}
			
			//Insert column book.Availability set default 1
			String bookAvai = "alter table book add column Availability int default 1 not null";
			PreparedStatement pst = conn.prepareStatement(bookAvai);
			pst.executeUpdate();
			//Insert column borrower.Availability set default 3
			String BorrowerAvai = "alter table borrower add column Availability int default 3 not null";
			pst = conn.prepareStatement(BorrowerAvai);		
			pst.executeUpdate();
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
		//*/
		


		//just for testing the correctness of the code upward
		/*
		System.out.println(bookList.get(2).getISBN10());
		System.out.println(bookList.get(1).getISBN13());
		System.out.println(bookList.get(6).getTitle());
		System.out.println(bookList.get(1).getAuthor());
		System.out.println(bookList.get(1).getCover());
		System.out.println(bookList.get(1).getPublisher());
		System.out.println(bookList.get(1).getPages());

		System.out.println(borrowerList.get(1).getID());
		System.out.println(borrowerList.get(1).getSSN());
		System.out.println(borrowerList.get(1).getName());
		System.out.println(borrowerList.get(1).getEmail());
		System.out.println(borrowerList.get(1).getAddress());
		System.out.println(borrowerList.get(2).getPhone());
		*/
	} 			
}

