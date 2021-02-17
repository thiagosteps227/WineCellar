package com.ait.validation;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.ait.wine.Wine;

public class WineValidator {
	Wine wine;
	List<String> badYears = Arrays.asList("2001", "2005", "1998");
	List<String> countries = Arrays.asList("FRANCE", "SPAIN","GREECE");
	private static final int YEARS_OLD = 5;
	

	public void validateWine(Wine wine) throws WineValidationException {
		this.wine = wine;
		checkEmptyFields(wine);
		checkValidCountry(wine, countries);
		checkBadYears(wine, badYears);
		checkIfMature(wine, YEARS_OLD);
	}
	

	private void checkEmptyFields(Wine wine) throws WineValidationException {
		if ((wine.getName().length() == 0) || (wine.getCountry().length() == 0) || (wine.getYear().length() == 0)
				|| (wine.getGrapes().length() == 0)) {
			throw new WineValidationException(ErrorMessages.EMPTY_FIELDS.getMsg());
		}
	}

	private void checkValidCountry(Wine wine, List<String> countries) throws WineValidationException {
		boolean countryOK = false;
		String countryEntered = wine.getCountry().toUpperCase();
		for (String country : countries) {
			if (country.equals(countryEntered)) {
				countryOK = true;
				break;
			}
		}
		if (countryOK == false) {
			throw new WineValidationException(ErrorMessages.INVALID_COUNTRY.getMsg());
		}
	}

	private void checkBadYears(Wine wine, List<String> badYears) throws WineValidationException {
		if (badYears.contains(wine.getYear())) {
			throw new WineValidationException(ErrorMessages.BAD_YEAR.getMsg());
		}
	}

	private void checkIfMature(Wine wine, int yearsOld) throws WineValidationException {
		Calendar now = Calendar.getInstance();
		int currentYear = now.get(Calendar.YEAR);
		int wineYear = Integer.parseInt(wine.getYear());
		if (wineYear + yearsOld >= currentYear) {
			throw new WineValidationException(ErrorMessages.NOT_MATURE.getMsg());
		}
	}

}
