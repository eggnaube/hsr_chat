package chat.user;

import java.io.*;
import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import chat.util.FacesUtil;

public class UserManager {

	protected Hashtable<String, User> users = new Hashtable<String, User>();
	protected String userFilename = "C:/temp/users.ser";
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

	public String edit() {
		User user = FacesUtil.getSession(User.class, "user");

		boolean result = false;
		try {
			result = register(user, true);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wrong",
							"wrong"));
		}
		if (result)
			return "success";
		return null;

	}

	public String register() throws UserException {

		User user = FacesUtil.getSession(User.class, "user");

		boolean result = false;
		try {
			result = register(user, false);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wrong",
							"wrong"));
		}
		if (result)
			return "success";
		return null;
	}

	public boolean register(User user, boolean override) throws UserException {
		if (users.get(user.getLogin()) != null && !override) {
			throw new UserException();
		}

		if (user.checkEntries()) {
			if (!isAdminDefined())
				user.setAdmin(true);
			users.put(user.getLogin(), user);
			save();
			return true;
		}
		return false;
	}

	public String login() {
		User user = FacesUtil.getSession(User.class, "user");

		try {
			user = login(user.getLogin(), user.getPassword());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"login",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Username or Password wrong",
							"Your Username or Password is wrong"));
			return null;
		}

		FacesUtil.setSession("user", user);
		return "success";
	}

	public User login(String login, String password) throws UserException {
		if ((login == null && password == null)
				|| (login == "" && password == "")) {
			throw new UserException();
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

	public boolean isAdminDefined() {
		for (User u : users.values()) {
			if (u.isAdmin())
				return true;
		}
		return false;
	}

	public User getUser(String login) {
		return users.get(login);
	}

	public User[] getUserArray() {
		return users.values().toArray(new User[] {});
	}
	
	public String editAction(String login) {
		users.get(login).setEditable(true);
		return null;
	}
	
	public String deleteAction(String login) {
		users.remove(login);
		save();
		return null;
	}

	public String saveAction() {
		 
		//get all existing value but set "editable" to false 
		for (User user : users.values()){
			user.setEditable(false);
		}
		save();
		//return to current page
		return null;
	}


	public String logOff() {
		FacesUtil.setSession("user", null);
		return "login.xhtml";
	}
}
