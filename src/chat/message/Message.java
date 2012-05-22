package chat.message;

import java.io.Serializable;
import java.util.Date;

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
		chatterName = user.getLogin();
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
	
	/**
	 * Returns the Date object of the message
	 * @return Date
	 */
	public Date getMessageDate() {
		return new Date(timeStamp);
	}
}