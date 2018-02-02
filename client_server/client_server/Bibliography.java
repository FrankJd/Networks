/* File: Bibliography
 * Author: Troy Nechanicky, nech5860@mylaurier.ca, 150405860
 * Version: February 4, 2018
 * 
 * Description:
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bibliography {
	private List<Book> books = new ArrayList<Book>();

	Bibliography() {	}

	public String[] add(HashMap<ClientHandler.Field, String> parameters) {
		//ensure ISBN is given and valid
		if (parameters.get(ClientHandler.Field.ISBN).equals("")) {
			throw new RequestException("ERROR: ISBN required");
		}
		else if (!validIsbn(parameters.get(ClientHandler.Field.ISBN))) {
			throw new RequestException("ERROR: Invalid ISBN");
		}
		
		
	}

	public String[] update(HashMap<ClientHandler.Field, String> parameters) {

	}

	public String[] get(HashMap<ClientHandler.Field, String> parameters) {

	}

	public String[] getAll() {

	}

	public String[] remove() {

	}

	public String[] removeAll() {

	}



	private class Book {
		private String isbn, title, author, publisher, year;


		/*getters*/

		public String getIsbn() {
			return isbn;
		}


		public String getTitle() {
			return title;
		}


		public String getAuthor() {
			return author;
		}


		public String getPublisher() {
			return publisher;
		}


		public String getYear() {
			return year;
		}

		/*setters*/    

		public void setIsbn(String isbn){
			this.isbn = isbn;
		}

		public void setTitle(String title){

			this.title = title;
		}

		public void setAuthor(String author){
			this.author = author;
		}

		public void setPublisher(String publisher){
			this.publisher = publisher;
		}

		public void setYear(String year){
			this.year = year;
		}

	}
}

