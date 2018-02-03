/* File: Bibliography
 * Author: Troy Nechanicky, nech5860@mylaurier.ca, 150405860
 * Group: 08
 * Version: February 4, 2018
 * 
 * Description: A list of Books
 * 	Methods for adding, updating, removing, and getting Books
 * 	Only 1 thread allowed to access adding, updating, removing, and getting methods at a time
 * 		Could be made more efficient by using 2 locks
 * 	Designed to follow CP372 A1 Protocol RFC
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/* Relies on the following files to be in default package:
 * 	RequestException
 * 	Book
 *  IsbnValidator
 */

public class Bibliography {
	private List<Book> books = new ArrayList<Book>();

	Bibliography() {	}

	//requires unique, valid ISBN
	public synchronized List<String> add(Map<ClientHandler.Field, String> requestContent) throws RequestException {		
		Book newBook;

		//if a duplicate ISBN
		if (books.stream().anyMatch(x -> x.isbn.equals(requestContent.get(ClientHandler.Field.ISBN)))) {
			throw new RequestException("ERROR: Duplicate ISBN");
		}

		//will throw exception if invalid ISBN
		newBook = new Book(requestContent);
		books.add(newBook);

		return Arrays.asList("SUCCESS: Record added\n\n");
	}

	//uses ISBN to find book to update according to params
	public synchronized List<String> update(Map<ClientHandler.Field, String> parameters) throws RequestException {
		List<Book> specifiedBook;

		if (parameters.get(ClientHandler.Field.ISBN) == null) {
			throw new RequestException("ERROR: ISBN required");
		}

		if (parameters.values().stream().filter(x -> x != null).count() == 1) {
			throw new RequestException("ERROR: Missing parameters");
		}

		specifiedBook = books.stream()
				.filter(x -> x.isbn.equals(parameters.get(ClientHandler.Field.ISBN)))
				.collect(Collectors.toList()); 

		if (specifiedBook.size() == 0) {
			throw new RequestException("ERROR: Matching record not found\n");
		}

		specifiedBook.get(0).update(parameters);

		return Arrays.asList("SUCCESS: Record updated\n\n");
	}

	public synchronized List<String> get(Map<ClientHandler.Field, String> parameters) throws RequestException {
		List<Book> specifiedBooks;
		List<String> response = new ArrayList<String>();

		if (parameters.values().stream().filter(x -> x != null).count() == 0) {
			throw new RequestException("ERROR: Missing parameters");
		}

		specifiedBooks = filterBooks(parameters); 

		if (specifiedBooks.size() == 0) {
			throw new RequestException("ERROR: Matching record not found");
		}

		for (Book book : specifiedBooks) {
			response.add(book.toString() + "\n");
		}

		if (response.size() == 0) {
			response.add("ERROR: No matching record(s)\n\n");
		}
		else {
			response.set(response.size()-1, response.get(response.size()-1) + "\n");
		}

		return response;
	}

	public synchronized List<String> getAll() {
		List<String> response = new ArrayList<String>();

		for (Book book : books) {
			response.add(book.toString() + "\n");
		}

		if (response.size() == 0) {
			response.add("ERROR: No matching record(s)\n\n");
		}
		else {
			response.set(response.size()-1, response.get(response.size()-1) + "\n");
		}

		return response;
	}

	public synchronized List<String> remove(Map<ClientHandler.Field, String> parameters) throws RequestException {
		Stream<Book> filteredStream = books.stream(); //holds books not filtered out by params
		int initialCount; //used to check if any records removed

		if (parameters.values().stream().filter(x -> x != null).count() == 0) {
			throw new RequestException("ERROR: Missing parameters");
		}

		initialCount = books.size();

		//filter out given params
		if (parameters.get(ClientHandler.Field.ISBN) != null) {			
			filteredStream = filteredStream
					.filter(x-> !x.isbn.equals(parameters.get(ClientHandler.Field.ISBN)));
		}
		if (parameters.get(ClientHandler.Field.TITLE) != null) {
			filteredStream = filteredStream
					.filter(x-> !x.title.equals(parameters.get(ClientHandler.Field.TITLE)));
		}
		if (parameters.get(ClientHandler.Field.AUTHOR) != null) {
			filteredStream = filteredStream
					.filter(x-> !x.author.equals(parameters.get(ClientHandler.Field.AUTHOR)));
		}
		if (parameters.get(ClientHandler.Field.PUBLISHER) != null) {
			filteredStream = filteredStream
					.filter(x-> !x.publisher.equals(parameters.get(ClientHandler.Field.PUBLISHER)));
		}
		if (parameters.get(ClientHandler.Field.YEAR) != null) {
			filteredStream = filteredStream
					.filter(x-> !x.year.equals(parameters.get(ClientHandler.Field.YEAR)));
		}

		//convert stream back to list
		books = filteredStream
				.collect(Collectors.toList()); 

		if (initialCount - books.size() == 0) {
			throw new RequestException("ERROR: Matching record not found");
		}

		return Arrays.asList("SUCCESS: Record(s) removed\n\n");
	}

	public synchronized List<String> removeAll() {
		books.clear();

		return Arrays.asList("SUCCESS: Record(s) removed\n\n");
	}

	private List<Book> filterBooks(Map<ClientHandler.Field, String> parameters) throws RequestException {
		List<Book> filteredBooks;	
		Stream<Book> filteredStream = books.stream();

		//filter by given params
		if (parameters.get(ClientHandler.Field.ISBN) != null) {			
			filteredStream = filteredStream
					.filter(x-> x.isbn.equals(parameters.get(ClientHandler.Field.ISBN)));
		}
		if (parameters.get(ClientHandler.Field.TITLE) != null) {
			filteredStream = filteredStream
					.filter(x-> x.title.equals(parameters.get(ClientHandler.Field.TITLE)));
		}
		if (parameters.get(ClientHandler.Field.AUTHOR) != null) {
			filteredStream = filteredStream
					.filter(x-> x.author.equals(parameters.get(ClientHandler.Field.AUTHOR)));
		}
		if (parameters.get(ClientHandler.Field.PUBLISHER) != null) {
			filteredStream = filteredStream
					.filter(x-> x.publisher.equals(parameters.get(ClientHandler.Field.PUBLISHER)));
		}
		if (parameters.get(ClientHandler.Field.YEAR) != null) {
			filteredStream = filteredStream
					.filter(x-> x.year.equals(parameters.get(ClientHandler.Field.YEAR)));
		}
		
		//convert stream back to list
		filteredBooks = filteredStream
				.collect(Collectors.toList());

		return filteredBooks;
	}

	private static Map<ClientHandler.Field, String> replaceNull(Map<ClientHandler.Field, String> parameters) {
		//replace null values with empty strings
		for(Map.Entry<ClientHandler.Field, String> param : parameters.entrySet()){
			if (param.getValue() == null) param.setValue("");
		}

		return parameters;
	}

	private class Book {
		private String isbn, title, author, publisher, year;

		//null params set to empty strings
		//must have valid ISBN
		Book(Map<ClientHandler.Field, String> parameters) throws RequestException {
			parameters = replaceNull(parameters);

			//replace null values with empty strings
			for(Map.Entry<ClientHandler.Field, String> param : parameters.entrySet()){
				if (param.getValue() == null) param.setValue("");
			}

			//ensure ISBN is given and valid
			if (parameters.get(ClientHandler.Field.ISBN).equals("")) {
				throw new RequestException("ERROR: ISBN required");
			}
			if (!IsbnValidator.validIsbn(parameters.get(ClientHandler.Field.ISBN))) {
				throw new RequestException("ERROR: Invalid ISBN");
			}

			//set attributes
			isbn = parameters.get(ClientHandler.Field.ISBN);
			title = parameters.get(ClientHandler.Field.TITLE);
			author = parameters.get(ClientHandler.Field.AUTHOR);
			publisher = parameters.get(ClientHandler.Field.PUBLISHER);
			year = parameters.get(ClientHandler.Field.YEAR);
		}

		//update according to non-null params
		//doesn't update ISBN
		private void update(Map<ClientHandler.Field, String> parameters) throws RequestException {		
			if (parameters.get(ClientHandler.Field.TITLE) != null) {
				title = parameters.get(ClientHandler.Field.TITLE);
			}
			if (parameters.get(ClientHandler.Field.AUTHOR) != null) {
				author = parameters.get(ClientHandler.Field.AUTHOR);
			}
			if (parameters.get(ClientHandler.Field.PUBLISHER) != null) {
				publisher = parameters.get(ClientHandler.Field.PUBLISHER);
			}
			if (parameters.get(ClientHandler.Field.YEAR) != null) {
				year = parameters.get(ClientHandler.Field.YEAR);
			}

			return;
		}

		public String toString() {
			String str;

			str = "ISBN " + isbn + "\n" 
					+ "TITLE " + title + "\n"
					+ "AUTHOR " + author + "\n"
					+ "PUBLISHER " + publisher + "\n"
					+ "YEAR " + year;

			return str;
		}
	}
}