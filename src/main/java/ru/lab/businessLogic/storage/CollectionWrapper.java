package ru.lab.businessLogic.storage;

import ru.lab.businessLogic.storage.exciptions.CollectionException;
import ru.lab.businessLogic.storage.exciptions.EmptyCollectionException;
import ru.lab.businessLogic.storage.exciptions.NoSuchElementInCollectionException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Интерфейс, который предоставляет специальные методы для работы с коллекцией
 */
public interface CollectionWrapper<Entity> {
    void clear();

    int getSize();

    LocalDate getInitTime();

    void remove(Entity entity) throws NoSuchElementInCollectionException;

    void remove(long id) throws NoSuchElementInCollectionException, EmptyCollectionException;

    Entity get(long id) throws NoSuchElementInCollectionException;

    List<Entity> getList() throws EmptyCollectionException;

    Entity add(Entity value);

    void addAll(Collection<? extends Entity> collection);

    Entity replace(long id, Entity value) throws CollectionException;

    Entity min();

    Entity max();

    List<Entity> sort(boolean descending) throws EmptyCollectionException;

    String getCollectionInfo();

    Entity insertAt(Long position, Entity entity) throws CollectionException;

}
