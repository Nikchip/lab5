package ru.lab.businessLogic.storage.exciptions;

import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResultStatus;

public class EmptyCollectionException extends CollectionException{
    public EmptyCollectionException(){
        super("Collection is empty!");
    }
}
