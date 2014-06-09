package kata.text.anagram.exception;

import kata.text.exception.ExceptionCode;


/**
 * AnagramExceptionCodes centralizes ExceptionCodes of the App, and lets reuse of Exception strings
 * 
 * @author Erkan Durmaz
 * @date 17 Mar 2013
 * @time 18:04:08
 *
 */
public class AnagramExceptionCodes
{
	//---------------------ANAGRAM FINDER APP EXCEPTION CODES------------------// 
	
	// HttpClient
	public static final ExceptionCode HTTPCLIENT_COMMUNICATION_EXCEPTION = new ExceptionCode("HTTPCLIENT_COMMUNICATION_EXCEPTION", "Communication error in accessing URI {0}.");
	public static final ExceptionCode HTTPCLIENT_COMMUNICATION_IOEXCEPTION = new ExceptionCode("HTTPCLIENT_COMMUNICATION_IOEXCEPTION", "I/O exception occurred in accessing URI {0}. ResponseCode: {1}, ErrorResponse: {2}");
	public static final ExceptionCode HTTPCLIENT_COMMUNICATION_HTTP_ERRORSTREAM_EXCEPTION = new ExceptionCode("HTTPCLIENT_COMMUNICATION_HTTP_ERRORSTREAM_EXCEPTION", "I/O exception (IOException: {0}) occurred in accessing URI {1}. HTTP ErrorStream cannot be read.");
	
	// WordRepository
	public static final ExceptionCode WORDREPOSITORY_INVALID_CONTENT = new ExceptionCode("WORDREPOSITORY_INVALID_CONTENT", "Parsing error: WordRepository contains invalid data");
	public static final ExceptionCode WORDREPOSITORY_REPOSITORY_FILE_CANNOT_BE_LOCATED = new ExceptionCode("WORDREPOSITORY_REPOSITORY_FILE_CANNOT_BE_LOCATED", "Repository file cannot be found at path {0}");
	
	// Logging
	public static final ExceptionCode LOGGING_CANNOT_FIND_LOGGING_CONFIGURATION_FILE = new ExceptionCode("LOGGING_CANNOT_FIND_LOGGING_CONFIGURATION_FILE", "No logging configuration file is found at {0}.");
	public static final ExceptionCode LOGGING_CANNOT_CONFIGURE_LOGGING_FRAMEWORK = new ExceptionCode("LOGGING_CANNOT_CONFIGURE_LOGGING_FRAMEWORK", "Cannot configure logging framework.");

	//---------------------ANAGRAM FINDER APP EXCEPTION CODES------------------// 
}
