/* File: RequestException
 * Author: Troy Nechanicky, nech5860@mylaurier.ca, 150405860
 * Group: 08
 * Version: February 4, 2018
 * 
 * Description: Custom exception class used for detecting exceptional cases
 * 	that violate request expectations
 */

@SuppressWarnings("serial")
public class RequestException extends Exception {
	RequestException(String msg) {
		super(msg);
	}
}
