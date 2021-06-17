package ru.lab.businessLogic.services.logicServices.executors.impls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.lab.businessLogic.commands.CommandArgs;
import ru.lab.businessLogic.domain.interaction.query.Query;
import ru.lab.businessLogic.domain.interaction.query.QueryType;
import ru.lab.businessLogic.services.factories.movieFactories.MovieFactory;
import ru.lab.businessLogic.services.factories.queryFactories.QueryFactory;
import ru.lab.businessLogic.services.logicServices.executors.QueryTypeCreator;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.MissedArgumentException;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.UnexpectedArgumentException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MixedArgumentCreator extends QueryTypeCreator{
    private final ObjectMapper objectMapper;
    private final MovieFactory movieFactory;

    public MixedArgumentCreator(QueryFactory queryFactory, ObjectMapper objectMapper, MovieFactory movieFactory) {
        super(queryFactory);
        this.objectMapper = objectMapper;
        this.movieFactory = movieFactory;
    }

    @Override
    public Query createQuery(List<String> commandData) throws MissedArgumentException, UnexpectedArgumentException, JsonProcessingException {
        if(commandData.size() == 1){
            throw new MissedArgumentException();
        }
        return queryFactory.createDefaultQuery(
                commandData.get(0),
                QueryType.COMMAND_REQUEST,
                processObjectCommand(commandData.get(1)));
    }

    public Map<CommandArgs, String> processObjectCommand(String data) throws JsonProcessingException {
        Map<CommandArgs, String> argsMap = new HashMap<>();
        String serializedArg = objectMapper.writeValueAsString(movieFactory.createMovie());
        argsMap.put(CommandArgs.OBJECT_ARGUMENT, serializedArg);
        argsMap.put(CommandArgs.SIMPLE_ARGUMENT, data);
        return argsMap;
    }
}
