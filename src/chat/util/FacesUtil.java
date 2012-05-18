package chat.util;

import javax.faces.context.FacesContext;

public class FacesUtil {

	@SuppressWarnings("unchecked")
	public static <T> T  getRequest(T a, String name) {
		try 
		{
			return (T) FacesContext.getCurrentInstance().getExternalContext()
					.getRequestMap().get(name);
		}
		catch(Exception e)
		{
			return null;
		}
	}

	public static void setRequest(String name, Object value ) {
		FacesContext.getCurrentInstance().getExternalContext()
		.getRequestMap().put(name, value);

	}

	@SuppressWarnings("unchecked")
	public static <T> T  getSession(T a, String name) {
		try 
		{
			return (T) FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().get(name);
		}
		catch(Exception e)
		{
			return null;
		}
	}

	public static void  setSession(String name, Object value ) {
		FacesContext.getCurrentInstance().getExternalContext()
		.getSessionMap().put(name, value);

	}

	@SuppressWarnings("unchecked")
	public static <T> T  getApplication(T a, String name) {
		return (T) FacesContext.getCurrentInstance().getExternalContext()
				.getApplicationMap().get(name);

	}	

	public static void  setApplication(String name, Object value ) {
		FacesContext.getCurrentInstance().getExternalContext()
		.getApplicationMap().put(name, value);

	}
}
