/* File: RequestException
 * Author: Troy Nechanicky, nech5860@mylaurier.ca, 150405860
 * Version: February 4, 2018
 * 
 * Description:
 */

@SuppressWarnings("serial")
public class RequestException extends Exception {
	RequestException(String msg) {
		super(msg);
	}
	
	RequestException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	@Override
	public void printStackTrace() {
		if (super.getCause() != null) {
			super.getCause().printStackTrace();
		}
		else {
			super.printStackTrace();
		}
		
		return;
	}
}
