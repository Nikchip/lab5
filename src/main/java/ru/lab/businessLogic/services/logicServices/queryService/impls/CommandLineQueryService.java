package ru.lab.businessLogic.services.logicServices.queryService.impls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.lab.businessLogic.commands.CommandArgs;
import ru.lab.businessLogic.commands.CommandType;
import ru.lab.businessLogic.domain.interaction.query.Query;
import ru.lab.businessLogic.services.factories.movieFactories.MovieFactory;
import ru.lab.businessLogic.services.factories.queryFactories.QueryFactory;
import ru.lab.businessLogic.services.logicServices.executors.QueryTypeCreator;
import ru.lab.businessLogic.services.logicServices.executors.impls.*;
import ru.lab.businessLogic.services.logicServices.queryService.QueryService;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.CommandDoesNotExistException;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.MissedArgumentException;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.NoSuchCommandTypeException;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.UnexpectedArgumentException;
import ru.lab.businessLogic.utils.scriptUtils.exceptions.InternalScriptException;
import ru.lab.businessLogic.utils.scriptUtils.exceptions.RecursionException;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandLineQueryService implements QueryService {

    private final Map<CommandType, List<String>> commandMap;
    private final MovieFactory movieFactory;
    private final QueryFactory queryFactory;
    private final ObjectMapper xmlMapper;
    private final Map<CommandType, QueryTypeCreator> executor;


    public CommandLineQueryService(Map<CommandType, List<String>> commandMap,
                                   MovieFactory movieFactory,
                                   QueryFactory queryFactory,
                                   ObjectMapper xmlMapper) {
        this.commandMap = commandMap;
        this.movieFactory = movieFactory;
        this.queryFactory = queryFactory;
        this.xmlMapper = xmlMapper;
        executor = new HashMap<>();
        executor.put(CommandType.SIMPLE_ARG, new SimpleArgumentCreator(queryFactory));
        executor.put(CommandType.MIXED, new MixedArgumentCreator(queryFactory, xmlMapper, movieFactory));
        executor.put(CommandType.OBJECT_ARG, new ObjectArgumentCreator(queryFactory, xmlMapper, movieFactory));
        executor.put(CommandType.CLEAR, new ClearArgumentCreator(queryFactory));
        executor.put(CommandType.SCRIPT, new ScriptArgumentCreator(queryFactory, xmlMapper, commandMap, movieFactory));

    }

    @Override
    public Query createQuery(String commandData) throws CommandDoesNotExistException, JsonProcessingException, NoSuchCommandTypeException, UnexpectedArgumentException, MissedArgumentException, FileNotFoundException, InternalScriptException, RecursionException {
        List<String> splitData = Arrays.asList(commandData.split(" "));
        String commandName = splitData.get(0);
        boolean isCommandExist = false;
        CommandType queryCommandType = null;
        for (CommandType commandType : commandMap.keySet()) {
            if (commandMap.get(commandType).contains(commandName)) {
                isCommandExist = true;
                queryCommandType = commandType;
                break;
            }
        }
        if (!isCommandExist) {
            throw new CommandDoesNotExistException();
        }
        QueryTypeCreator queryService = executor.get(queryCommandType);
        return queryService.createQuery(splitData);
    }
}
