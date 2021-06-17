package ru.lab.businessLogic.commands.exceptions;

import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResultStatus;

public class CustomParseException extends Exception {
    private final CommandResultStatus status = CommandResultStatus.OBJECT_EXCEPTION;

    public CustomParseException() {
        super("cannot parse object. Try again");
    }

    @Override
    public String toString() {
        return status.toString();
    }
}
