package cp372_A01_B;

public class Book{
		private String isbn,title ,author,publisher,year = "";
	    
		public String getIsbn() {
	    	return this.isbn;
	    }
	    
	    
	    public String getTitle() {
	    	return this.title;
	    }
    
	    
	    public String getAuthor() {
	        return this.author;
	     }
	    
	    
	    public String getPublisher() {
	        return this.publisher;
	     }

	    
	    public String getYear() {
	        return this.year;
	     }

	        
	        
	    public void setIsbn(String value){
	        this.isbn = value;
	     }
	        
	    public void setTitle(String value){

	        this.title = value;
	     }
	        
	     public void setAuthor(String value){
	         this.author = value;
	     }
	           
	     public void setPublisher(String value){
	         this.publisher = value;
	     }
	           
	     public void setYear(String value){
	         this.year = value;
	     }

		
	}



