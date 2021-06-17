package ru.lab.businessLogic.services.factories.queryFactories;

import ru.lab.businessLogic.commands.CommandArgs;
import ru.lab.businessLogic.domain.interaction.query.Query;
import ru.lab.businessLogic.domain.interaction.query.QueryType;

import java.util.Map;


/**
 * Фабрика для создания запросов
 */
public interface QueryFactory {
    Query createDefaultQuery(String commandName, QueryType queryType, Map<CommandArgs, String> args);
}
