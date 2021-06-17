package ru.lab.businessLogic.services.factories.queryFactories.impls;

import ru.lab.businessLogic.commands.CommandArgs;
import ru.lab.businessLogic.domain.interaction.query.Query;
import ru.lab.businessLogic.domain.interaction.query.QueryType;
import ru.lab.businessLogic.services.factories.queryFactories.QueryFactory;

import java.util.Map;

public class QueryFactoryImpl implements QueryFactory {
    @Override
    public Query createDefaultQuery(String commandName, QueryType queryType, Map<CommandArgs, String> args) {
        return new Query(commandName, args, queryType);
    }
}
