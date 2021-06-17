package ru.lab.businessLogic.services.logicServices.executors;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.lab.businessLogic.domain.interaction.query.Query;
import ru.lab.businessLogic.services.factories.queryFactories.QueryFactory;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.MissedArgumentException;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.UnexpectedArgumentException;
import ru.lab.businessLogic.utils.scriptUtils.exceptions.InternalScriptException;

import java.util.List;

/**
 * реализаторы построения запросов для разных типов команд
 */
public abstract class QueryTypeCreator {
    protected final QueryFactory queryFactory;

    public QueryTypeCreator(QueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public abstract Query createQuery(List<String> commandData) throws MissedArgumentException, UnexpectedArgumentException, JsonProcessingException, InternalScriptException;
}
