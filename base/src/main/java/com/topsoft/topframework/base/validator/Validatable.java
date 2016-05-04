package com.topsoft.topframework.base.validator;

import java.util.List;

public interface Validatable {

	public void addValidators(Validator... validators);

	public List<Validator> getValidators();

	public void setRequired(boolean required);

	public boolean isRequired();

	public void validateData();
}
