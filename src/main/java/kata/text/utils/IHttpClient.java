package kata.text.utils;

import java.net.URL;

public interface IHttpClient
{
	public String getContent(URL resourceUrl);
}
