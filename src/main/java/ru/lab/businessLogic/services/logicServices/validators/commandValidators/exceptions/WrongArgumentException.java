package ru.lab.businessLogic.services.logicServices.validators.commandValidators.exceptions;

public class WrongArgumentException extends ArgumentValidatorException {
    public WrongArgumentException() {
        super("Get wrong type of argument!");
    }
}
