/* File: IsbnValidator
 * Author: Troy Nechanicky, nech5860@mylaurier.ca, 150405860
 * Version: February 1, 2018
 * 
 * Description: Contains a function that validates ISBN-13 numbers
 */

public class IsbnValidator {
	public static boolean validIsbn(String isbn) {
		int total = 0, checksum, digit;

		//remove any hyphens
		isbn = isbn.replaceAll("-", "");

		//must be a 13 digit ISBN
		if (isbn.length() != 13) {
			return false;
		}
		
		//any non-numeric characters will cause exception in Integer.parseInt,
		//and will be caught by catch
		try {	
			for (int i = 0; i < 12; i++) {
				//get ith digit
				digit = Integer.parseInt(isbn.substring(i, i + 1));
				//multiply digit by 3 if odd ith digit (b/c ISBN-13 says multiply by 3 if even, and array base is 0) 
				total += (i % 2 == 0) ? digit : digit * 3;
			}

			checksum = (10 - total % 10) % 10;
			
			//true if checksum = check digit
			return (checksum == Integer.parseInt(isbn.substring(12)));
		}
		//return false if non-numeric character (exception trying to convert to int)
		catch (NumberFormatException nfe) {
			return false;
		}
	}
}
