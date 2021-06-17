package ru.lab.businessLogic.services.logicServices.validators.commandValidators.exceptions;

public class WrongCountOfArgumentsException extends ArgumentValidatorException{
    public WrongCountOfArgumentsException() {
        super("Got wrong count of arguments");
    }
}
