package kata.text.exception;

import java.text.MessageFormat;

/**
 * 
 * ExceptionCode
 * 
 * @author Erkan Durmaz
 * @date 17 Mar 2013
 * @time 19:06:46
 *
 */
public class ExceptionCode
{
	private final String key;
	private final String messageTemplate;
	
	public ExceptionCode(String key, String messageTemplate)
	{
		this.key = key;
		this.messageTemplate = messageTemplate;
	}

	public String toString(Object... messageTemplateArgs)
	{
		try
		{
			MessageFormat messageFormat = new MessageFormat(this.messageTemplate);
			
			return messageFormat.format(messageTemplateArgs);
		}
		catch (Exception e)
		{
			return this.key;
		}
	}
	
	@Override
	public String toString()
	{
		Object[] parameters = null;
		return toString(parameters);
	}
}
