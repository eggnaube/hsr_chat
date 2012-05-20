package chat.room;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import chat.user.User;
import chat.util.FacesUtil;

/**
 * This class is used to manipulate and store ChatRoom objects.
 * It provides methods to store and retrieve ChatRoom objects
 * in this <code>ChatRoomList</code>.
 */
public class ChatRoomList
{
	/**
	 * Stores all the ChatRoom objects
	 */
	private Map<String, ChatRoom> roomList;

	/**
	 */
	public ChatRoomList()
	{
		roomList = new HashMap<String, ChatRoom>();
	}
	
	/**adds a new chat room object from the bean to the list
	 * @return if the adding was successfully
	 */
	public synchronized String addRoom()
	{
		ChatRoom room = FacesUtil.getSession(ChatRoom.class, "chatRoom");
		
		if(!roomList.containsKey(room.getName()))
			addRoom(room);
		else
			room = roomList.get(room.getName());
		
		room.addUser(FacesUtil.getSession(User.class, "user"));
		FacesUtil.setSession("chatRoom", room);
		
		return "success";
	}
	
	/**
	 * Joins the actual user to a chat room.
	 * @param roomName String
	 * @return String
	 */
	public synchronized String joinRoom(String roomName) {
		ChatRoom room = this.getRoom(roomName);
		room.addUser(FacesUtil.getSession(User.class, "user"));
		FacesUtil.setSession("chatRoom", room);
		
		return "chat.xhtml";
	}

	/**
	 * adds new chat room object to a list of Rooms.
	 * @param room ChatRoom object
	 * @return void
	 */
	public synchronized void addRoom(ChatRoom room)
	{
		roomList.put(room.getName(), room);
	}

	/**
	 * Used to remove a ChatRoom object from the
	 * list of ChatRooms.
	 * @param name is a String object is the name of the
	 * room to be removed from the list of rooms.
	 * @return void
	 */
	public synchronized void removeRoom(String name)
	{
		roomList.remove(name);
	}

	/** Returns a ChatRoom object
	 * @param name is the name of the ChatRoom object to be returned.
	 * @return ChatRoom object.
	 */
	public ChatRoom getRoom(String name)
	{
		return roomList.get(name);
	}
	
	/** Finds the room of user having this name.
	 * @param name is the name of the User object.
	 * @return ChatRoom object.
	 */
	public ChatRoom getRoomOfUser(String name)
	{
		ChatRoom[] rooms = this.getRoomListArray();

		for (ChatRoom chatRoom : rooms) {
			if (chatRoom.userExists(name))
				return chatRoom;
		}
		return null;

	}
	
	/** Returns a Set containing all the ChatRoom objects
	 * @return Set
	 */
	public Set<?> getRoomList()
	{
		return roomList.entrySet();
	}

	/** returns an array containing all ChatRoom objects
	 * @return chat.ChatRoom[]
	 */
	public ChatRoom[] getRoomListArray()
	{
		return roomList.values().toArray(new ChatRoom[0]);
	}

	/**
	 * searches each ChatRoom for existence of a user.
	 * @param nickname Name of the user to find.
	 * @return boolean
	 */
	public boolean userExists(String nickname)
	{
		ChatRoom[] rooms = this.getRoomListArray();

		for (ChatRoom chatRoom : rooms) {
			if(chatRoom.userExists(nickname))
				return true;
		}
		return false;
	}
}