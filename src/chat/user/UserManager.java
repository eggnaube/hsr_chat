package chat.user;

import java.io.*;
import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import chat.util.FacesUtil;

public class UserManager {

	protected Hashtable<String, User> users = new Hashtable<String, User>();
	protected String userFilename = "c:/hsr/users.ser";
	protected File userFile;

	public UserManager() {
		userFile = new File(userFilename);
		if (userFile.exists()) {
			try {
				FileInputStream fileIn = new FileInputStream(userFile);
				ObjectInputStream objectIn = new ObjectInputStream(fileIn);
				users = (Hashtable<String, User>) objectIn.readObject();
				objectIn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	public String register() throws UserException {
		
		User user = FacesUtil.getSession(new User(), "user");

		boolean result = false;
		try {
			result = register(user);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "wrong", "wrong"));
		}
		if(result)
			return "success";
		return "fail";
	}


	public boolean register(User user) throws UserException {
		if (users.get(user.getLogin()) != null) { // User existiert bereits
			throw new UserException();
		}

		if (user.checkEntries()) { // wirft UserException bei Fehler
			users.put(user.getLogin(), user);
			save();
			return true;
		}
		return false;
	}
	
	public String login() 
	{
		User user = FacesUtil.getSession(new User(), "user");
		
		try {
			user = login(user.getLogin(),user.getPassword());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username or Password wrong", "Your Username or Password is wrong"));
			return null;
		}
		
		FacesUtil.setSession("user", user);
		return "success";
	}

	public User login(String login, String password) throws UserException {
		if ((login == null && password == null)
				|| (login == "" && password == "")) {
			return null;
		}
		User user = users.get(login);
		if (user != null && user.checkPassword(password)) {
			return user;
		}
		throw new UserException();
	}

	public void save() {
		try {
			FileOutputStream fileOut = new FileOutputStream(userFile);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(users);
			objectOut.flush();
			objectOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
