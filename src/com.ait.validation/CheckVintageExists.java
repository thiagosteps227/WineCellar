package com.ait.validation;

import java.util.List;

import com.ait.wine.Wine;

public class CheckVintageExists {
	Wine wine;
	List<Wine> wines;

	public void checkForVintage(Wine wine, List<Wine> wines) throws WineVintageExistsException {
		this.wine = wine;
		this.wines = wines;

		for (Wine w : wines) {
			if ((w.getName()).equals(wine.getName())) {
				if ((w.getYear()).equals(wine.getYear())) {
					throw new WineVintageExistsException(ErrorMessages.ALREADY_EXISTS.getMsg());
				}
			}

		}
	}
}
