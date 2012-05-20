package chat.message;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import chat.user.User;
import chat.util.FacesUtil;

/**
Represents a Message sent by a user.
*/
public class Message implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	* String used to store the name of a chatter
	*/
	private String chatterName = null;
	
	private static final DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
	
	/**
	* String containing message
	*/
	private String message = null;

	/**
	* long containing the time when message was delivered
	*/
	private long timeStamp;
	
	
	/**
	* Constructor of the Message
	*/
	public Message()
	{
		User user = FacesUtil.getSession(User.class, "user");
		chatterName = user.getName();
	}
	
	/**
	* Returns name of the Chatter
	* @return String
	*/
	public String getChatterName()
	{
		return chatterName;
	}
	
	/**
	 * Sets the Message to the Message :-)
	 * @param message The Message to set
	 */
	public void setMessage(String message)
	{
		this.timeStamp = System.currentTimeMillis();
		this.message = message;
	}
	
	/**
	* Returns message of the chatter
	* @return String
	*/
	public String getMessage()
	{
		return message;
	}
	/**
	* Returns time of the message
	* @return long
	*/
	public long getTimeStamp()
	{
		return timeStamp;
	}
	
	public String getMessageTime()
	{
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        
        return formatter.format(calendar.getTime());

	}
	
	
}