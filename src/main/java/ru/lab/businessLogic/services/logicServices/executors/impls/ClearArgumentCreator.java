package ru.lab.businessLogic.services.logicServices.executors.impls;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.lab.businessLogic.domain.interaction.query.Query;
import ru.lab.businessLogic.domain.interaction.query.QueryType;
import ru.lab.businessLogic.services.factories.queryFactories.QueryFactory;
import ru.lab.businessLogic.services.logicServices.executors.QueryTypeCreator;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.MissedArgumentException;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.UnexpectedArgumentException;

import java.util.Collections;
import java.util.List;

public class ClearArgumentCreator extends QueryTypeCreator {
    public ClearArgumentCreator(QueryFactory queryFactory) {
        super(queryFactory);
    }

    @Override
    public Query createQuery(List<String> commandData) throws MissedArgumentException, UnexpectedArgumentException, JsonProcessingException {
        if(commandData.size() > 1){
            throw new UnexpectedArgumentException();
        }
        return queryFactory.createDefaultQuery(commandData.get(0), QueryType.COMMAND_REQUEST, Collections.EMPTY_MAP);
    }
}
