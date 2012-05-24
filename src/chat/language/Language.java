package chat.language;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

public class Language implements Serializable {

	private static final long serialVersionUID = 1L;

	private String localeCode = "en";

	private static Map<String, Object> countries;
	static {
		countries = new LinkedHashMap<String, Object>();
		countries.put("EN", Locale.ENGLISH);
		countries.put("DE", Locale.GERMAN);
	}

	public Map<String, Object> getCountriesInMap() {
		return countries;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public void setLocaleCodeDe() {
		FacesContext.getCurrentInstance().getViewRoot()
				.setLocale(Locale.GERMAN);
		this.localeCode = "de";
	}
	
	public void setLocaleCodeEn() {
		FacesContext.getCurrentInstance().getViewRoot()
				.setLocale(Locale.ENGLISH);
		this.localeCode = "en";
	}

	public void countryLocaleCodeChanged(ValueChangeEvent e) {

		String newLocaleValue = e.getNewValue().toString();

		for (Map.Entry<String, Object> entry : countries.entrySet()) {

			if (entry.getValue().toString().equals(newLocaleValue)) {

				FacesContext.getCurrentInstance().getViewRoot()
						.setLocale((Locale) entry.getValue());

			}
		}
	}

}
