package ru.lab.businessLogic.commands.exceptions;

import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResultStatus;

public class CustomCastException extends Exception{
    private final CommandResultStatus status = CommandResultStatus.ARGUMENT_EXCEPTION;

    public CustomCastException() {
        super("invalid argument received");
    }

    @Override
    public String toString() {
        return status.toString();
    }
}
