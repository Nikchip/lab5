package ru.lab.businessLogic.storage.exciptions;

import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResultStatus;

public class CollectionException extends Exception{

    private final CommandResultStatus status = CommandResultStatus.COLLECTION_EXCEPTION;

    public CollectionException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return status.toString();
    }
}
