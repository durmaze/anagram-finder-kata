package kata.text.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import kata.text.anagram.exception.AnagramExceptionCodes;
import kata.text.exception.KataException;

public class SimpleHttpClient implements IHttpClient
{
	// dependencies
	private final int connectTimeout;
	private final int readTimeout;

	public SimpleHttpClient(int connectTimeout, int readTimeout)
	{
		// inject dependencies
		if (connectTimeout < 0)
		{
			  throw new IllegalArgumentException("connectTimeout can't be negative");
		}
		
		if (readTimeout < 0)
		{
			  throw new IllegalArgumentException("readTimeout can't be negative");
		}

		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
	}
	
	@Override
	public String getContent(URL resourceUrl)
	{
		if (resourceUrl == null)
		{
			throw new NullPointerException("resourceUrl is null");
		}
		
		if (!resourceUrl.getProtocol().equalsIgnoreCase("http"))
		{
			throw new IllegalArgumentException("Only HTTP URLs are supported");
		}
		
		HttpURLConnection urlConnection = null;
		
		try
		{
			// init connection
			urlConnection = createHttpUrlConnection(resourceUrl);
			
			urlConnection.setRequestMethod("GET");
			
			// read and close InputStream
			String textResponse = readHttpInputStream(urlConnection.getInputStream());
			
			return textResponse;
		}
		catch (IOException ex)
		{
			// It is an HTTP connection, read the error stream
			if (urlConnection != null)
			{
				int[] responseCode = new int[] { -1 };
				String errorResponse = readHttpErrorStream(urlConnection, responseCode, ex);
				
				throw new KataException(ex, AnagramExceptionCodes.HTTPCLIENT_COMMUNICATION_IOEXCEPTION, resourceUrl, responseCode[0], errorResponse);
			}
			
			throw new KataException(ex, AnagramExceptionCodes.HTTPCLIENT_COMMUNICATION_EXCEPTION, resourceUrl);
		}
		catch (Exception ex)
		{
			throw new KataException(ex, AnagramExceptionCodes.HTTPCLIENT_COMMUNICATION_EXCEPTION, resourceUrl);
		}
	}

	private HttpURLConnection createHttpUrlConnection(URL uri) throws IOException, MalformedURLException
	{
		HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
		
		urlConnection.setRequestProperty("User-Agent", "SimpleHttpClient");
		urlConnection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);
		urlConnection.setDefaultUseCaches(false);
		
		if (this.connectTimeout != 0)
		{
			urlConnection.setConnectTimeout(this.connectTimeout);
		}
		
		if (this.readTimeout != 0)
		{
			urlConnection.setReadTimeout(this.readTimeout);
		}
		
		return urlConnection;
	}
	
	private String readHttpInputStream(InputStream inputStream) throws IOException
	{
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
		
		StringBuilder textResponse = new StringBuilder();
		String buffer = null;
		
		while ((buffer = inputReader.readLine()) != null)
		{
			textResponse.append(buffer).append("\n");
		}
		
		inputReader.close();
		
		return textResponse.toString();
	}
	
	private String readHttpErrorStream(HttpURLConnection httpURLConnection, int[] responseCode, IOException ioException) 
	{
		try
		{
			if (responseCode != null)
			{
				responseCode[0] = httpURLConnection.getResponseCode(); 
			}
			
			return readHttpInputStream(httpURLConnection.getErrorStream());
		}
		catch (IOException ex)
		{
			throw new KataException(ex, AnagramExceptionCodes.HTTPCLIENT_COMMUNICATION_HTTP_ERRORSTREAM_EXCEPTION, ioException.getMessage(), httpURLConnection.getURL());
		}
	}
}
