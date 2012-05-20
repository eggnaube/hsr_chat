package chat.room;

import java.io.Serializable;
import java.util.Map;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.event.ActionEvent;

import org.icefaces.application.PushRenderer;

import chat.message.Message;
import chat.user.User;
import chat.util.FacesUtil;

/** This class represents a chat room in the Chat System
 */
public class ChatRoom implements Serializable
{

	private static final long serialVersionUID = 1L;
	/*
	 * used to store name of the room
	 */
	private String name = null;
	/*
	 * used to store description of the room
	 */
	private String description = null;
	/*
	 * Map to store User objects
	 */
	private Map<String, User> users = new HashMap<String, User>();
	/*
	 * Linked list to store Message object
	 */
	private List<Message> messages = new ArrayList<Message>();
	/*
	 * Used to set the maximum no of messages
	 */
	private int messages_size = 25;
	
	/**
	 * Default Constructor
	 */
	public ChatRoom()
	{
		PushRenderer.addCurrentSession(name);
	}
	
	/**
	 * Sets the Name of the room
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Returns name of the room
	 * @return java.lang.String
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Sets the description of the room
	 * @param description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	/**
	 * Returns description of the room
	 * @return java.lang.String
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * adds a User object to list of Users
	 * @param user User object
	 * @return void
	 */
	public synchronized void addUser(User user)
	{
		Message message = new Message();
		message.setMessage(user.getName() + " has joned");
		messages.add(message);
		
		users.put(user.getName(), user);
	}
	
	/**
	 * removes a User object from list of Users
	 * @return void
	 */
	public synchronized String removeUser()
	{
		removeUser(FacesUtil.getSession(User.class, "user"));
		FacesUtil.setSession("chatRoom", null);
		return "success";
	}
	/**
	 * removes a User object from list of Users
	 * @param userName name of the user.
	 * @return void
	 */
	public synchronized Object removeUser(User user)
	{
		Message message = new Message();
		message.setMessage(user.getName() + " has left");
		messages.add(message);
		
		return users.remove(user.getName());
	}
	
	/**
	 * returns a User object from users list.
	 * @param userName name of the user
	 * @return chat.User
	 */
	public User getUser(String userName)
	{
		return users.get(userName);
	}
	
	/**
	 * checks whether a user exists or not
	 * @param userName name of the user to check
	 * @return boolean
	 */
	public boolean userExists(String userName)
	{
		return users.containsKey(userName);
	}
	
	/**
	 * returns total number of users in this room
	 * @return int
	 */
	public int getNoOfUsers()
	{
		return users.size();
	}
	
	/**
	 * returns a Set containing all the Users in the room
	 * @return java.util.Set
	 */
	public Set<Entry<String, User>> getUsers()
	{
		return users.entrySet();
	}
	
	/** returns an array containing all User objects
	 * @return chat.User[]
	 */
	public User[] getUsersArray()
	{
		return users.values().toArray(new User[0]); 
	}
	
	/**
	 * Add a Message to the room
	 * @param evt
	 */
	public synchronized void addMessage(ActionEvent evt)
	{
		Message msg = FacesUtil.getRequest(Message.class, "message");
		addMessage(msg);
		PushRenderer.render(name);
	}
	
	
	/** adds the message to the messages list
	 * @param msg A Message Object
	 * @return void
	 */
	public synchronized void addMessage(Message msg)
	{
		if(messages.size()==messages_size)
		{
			((LinkedList<Message>)messages).removeFirst();
		}
		messages.add(msg);
	}
	
	/**
	 * returns an array of messages sent after user updatetime
	 * @return array of messages
	 */
	public Message[] getMessages()
	{
		User user = FacesUtil.getSession(User.class, "user");
		return getMessages(user.getUpdateTime());
	}
	
	
	/**
	 * returns an array of messages sent after given time
	 * @param afterTimeStamp Time in milliseconds.
	 * @return array of messages
	 */	
	public Message[] getMessages(long afterTimeStamp)
	{
		ListIterator<Message> li = messages.listIterator();
		List<Message> temp = new ArrayList<Message>();
		Message m;
		while (li.hasNext())
		{
			m = li.next();
			if (m.getTimeStamp() >= afterTimeStamp)
			{
				temp.add(m);
			}
		}
		Object o[] = temp.toArray();
		Message[] arr = new Message[o.length];
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = (Message)o[i];
		}
		return arr;
	}
	
	
	/**
	 * returns total number of messages in the messages List
	 * @return int
	 */
	public int getNoOfMessages()
	{
		return messages.size();
	}

	/**
	 * sets maxmium number of messages to this number.
	 * @param size the maximum no of messages to hold at a time.
	 * @return void
	 */
	public void setMaximumNoOfMessages(int size)
	{
		messages_size = size;
	}

	/**
	 * returns maxmium number of messages set.
	 * @return int
	 */
	public int getMaxiumNoOfMessages()
	{
		return messages_size;
	}
}