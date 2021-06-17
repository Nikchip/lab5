package ru.lab.businessLogic.services.logicServices.validators.commandValidators.exceptions;

import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResultStatus;

public class ArgumentValidatorException extends Exception{

    private final CommandResultStatus status = CommandResultStatus.ARGUMENT_EXCEPTION;


    public ArgumentValidatorException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return status.toString();
    }
}
