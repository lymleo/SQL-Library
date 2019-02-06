package library;

public class Book
{
	private String ISBN10;
	private String ISBN13;
	private String Title;
	private String Author;
	private String Cover;
	private String Publisher;
	private String Pages;		
	private int Availability;
	
	public String getISBN10()
	{
		return ISBN10;
	}
	public void setISBN10(String ISBN10)
	{
		this.ISBN10= ISBN10;
	}
	
	public String getISBN13()
	{
		return ISBN13;
	}
	public void setISBN13(String ISBN13)
	{
		this.ISBN13= ISBN13;
	}
	
	public String getTitle()
	{
		return Title;
	}
	public void setTitle(String Title)
	{
		this.Title = Title;
	}
	
	public String getAuthor()
	{
		return Author;
	}
	public void setAuthor(String Author)
	{
		this.Author = Author;
	}
	
	public String getCover()
	{
		return Cover;
	}
	public void setCover(String Cover)
	{
		this.Cover = Cover;
	}
	
	public String getPublisher()
	{
		return Publisher;
	}
	public void setPublisher(String Publisher)
	{
		this.Publisher = Publisher;
	}
	
	public String getPages()
	{
		return Pages;
	}
	public void setPages(String Pages)
	{
		this.Pages =Pages ;
	}
	
	public int getAvailability()
	{
		return Availability;
	}
	public void setAvailability(int Availability)
	{
		this.Availability = Availability ;
	}

}