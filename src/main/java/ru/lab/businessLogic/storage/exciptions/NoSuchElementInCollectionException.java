package ru.lab.businessLogic.storage.exciptions;

public class NoSuchElementInCollectionException extends CollectionException{


    public NoSuchElementInCollectionException() {
        super("No such element in collection!");
    }
}
