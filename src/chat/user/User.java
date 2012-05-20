package chat.user;

import java.io.*;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	protected String login;
	protected String password;
	protected String name;
	protected String mail;
	
	protected Long updateTime;
	
	protected boolean loggedIn;

	/**Default Constructor
	 */
	public User()
	{
		updateTime = 0L;
	}
	
	/** returns the Login name from the user
	 * @return String
	 */
	public String getLogin() {
		if (login == null) {
			return "";
		}
		return login;
	}
	
	/**
	 * sets the login name from the user
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * returns the mail address form the user
	 * @return String
	 */
	public String getMail() {
		if (mail == null) {
			return "";
		}
		return mail;
	}

	/**
	 * sets the mail address form the user
	 * @param mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * returns the name of the user
	 * @return String
	 */
	public String getName() {
		if (name == null) {
			return "";
		}
		return name;
	}

	/**
	 * sets the name of the user
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * return the password of the user
	 * @return String
	 */
	public String getPassword() {
		if (password == null) {
			return "";
		}
		return password;
	}

	/**
	 * sets the password of the user
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * checks if the password is valid
	 * @param password
	 * @return boolean
	 */
	public boolean checkPassword(String password) {
		if (this.password.equals(password)) {
			loggedIn = true;
			return true;
		}
		return false;
	}
	
	/**
	 * returns the last update time
	 * @return Long
	 */
	public Long getUpdateTime()
	{
		if (updateTime == null)
			return 0L;
		return updateTime;
	}
	
	/**
	 * sets the update time
	 * @param updateTime
	 */
	public void setUpdateTime(Long updateTime)
	{
		this.updateTime = updateTime;
	}
	
	/**
	 * return if the user is logged in
	 * @return boolean
	 */
	public boolean isLoggedIn()
	{
		return loggedIn;
	}

	/**
	 * checks if the user entries are valid
	 * @return boolean
	 * @throws UserException
	 */
	public boolean checkEntries() throws UserException {
		if (login == null && password == null && name == null && mail == null)
			return false;
		if (login == null || login.equals(""))
			throw new UserException();
		if (password == null || password.equals(""))
			throw new UserException();
		if (name == null || name.equals(""))
			throw new UserException();
		if (mail == null || mail.equals(""))
			throw new UserException();
		if (mail.indexOf("@") == -1)
			throw new UserException();
		return true;
	}
}
