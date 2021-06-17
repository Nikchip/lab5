package ru.lab.businessLogic.services.logicServices.executors.impls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.lab.businessLogic.commands.CommandArgs;
import ru.lab.businessLogic.commands.CommandType;
import ru.lab.businessLogic.domain.interaction.query.Query;
import ru.lab.businessLogic.domain.interaction.query.QueryType;
import ru.lab.businessLogic.services.factories.movieFactories.MovieFactory;
import ru.lab.businessLogic.services.factories.movieFactories.exceptions.CreateObjectFromInputParametrsException;
import ru.lab.businessLogic.services.factories.queryFactories.QueryFactory;
import ru.lab.businessLogic.services.logicServices.executors.QueryTypeCreator;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.MissedArgumentException;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.UnexpectedArgumentException;
import ru.lab.businessLogic.utils.scriptUtils.exceptions.InternalScriptException;
import ru.lab.businessLogic.utils.scriptUtils.exceptions.RecursionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

//For what, creator ? Delete this cringe, fast
public class ScriptArgumentCreator extends QueryTypeCreator {
    private final ObjectMapper objectMapper;
    private final Map<String, CommandType> commandTypeMap;
    private final MovieFactory movieFactory;
    private Scanner scanner;

    public ScriptArgumentCreator(QueryFactory queryFactory,
                                 ObjectMapper objectMapper,
                                 Map<CommandType, List<String>> commandTypeMap,
                                 MovieFactory movieFactory) {
        super(queryFactory);
        this.objectMapper = objectMapper;
        this.commandTypeMap = initMap(commandTypeMap);
        this.movieFactory = movieFactory;
    }

    @Override
    public Query createQuery(List<String> commandData) throws MissedArgumentException, UnexpectedArgumentException, JsonProcessingException, InternalScriptException {
        try {
            checkRecursion(commandData.get(1));
            List<Query> queryList = extractCommands(commandData);
            return queryFactory.createDefaultQuery(
                    commandData.get(0),
                    QueryType.COMMAND_REQUEST,
                    new HashMap<CommandArgs, String>() {{
                        put(CommandArgs.SIMPLE_ARGUMENT, objectMapper.writeValueAsString(queryList));
                    }});


        } catch (RecursionException e) {
            throw new InternalScriptException("find recursion in script paths!");
        } catch (IOException e) {
            throw new InternalScriptException("file not found!");
        } catch (Exception e) {
            throw new InternalScriptException("internal script exception. " + e.getMessage());
        }

    }


    private List<Query> extractCommands(List<String> commandData) throws Exception {
        scanner = new Scanner(new File(commandData.get(1)));
        List<Query> queryList = new ArrayList<>();
        int currentLine = 0;
        while (scanner.hasNextLine()) {
            currentLine++;
            List<String> command = Arrays.asList(scanner.nextLine().split(" "));
            if (command.isEmpty()) {
                continue;
            }
            if (!commandTypeMap.containsKey(command.get(0))) {
                throw new Exception();
            }
            CommandType commandType = commandTypeMap.get(command.get(0));
            switch (commandType) {
                case CLEAR:
                    queryList.add(queryFactory.createDefaultQuery(
                            command.get(0), QueryType.COMMAND_REQUEST, null));
                    break;
                case SIMPLE_ARG:
                    queryList.add(queryFactory.createDefaultQuery(
                            command.get(0), QueryType.COMMAND_REQUEST, processSimpleArg(command.get(1))));
                    break;
                case OBJECT_ARG:
                    queryList.add(queryFactory.createDefaultQuery(
                            command.get(0), QueryType.COMMAND_REQUEST, processObjectCommand(null)));
                    break;
                case MIXED:
                    queryList.add(queryFactory.createDefaultQuery(
                            command.get(0), QueryType.COMMAND_REQUEST, processObjectCommand(command.get(1))));
                    break;
                case SCRIPT:
                    queryList.addAll(extractCommands(command));
                    break;
            }
        }
        return queryList;
    }

    private Boolean checkRecursion(String path) throws FileNotFoundException, RecursionException {
        Scanner scanner = new Scanner(new File(path));
        Set<String> paths = new HashSet<>();
        paths.add(path);
        while (scanner.hasNextLine()) {
            List<String> data = Arrays.asList(scanner.nextLine().split(" "));
            if (data.get(0).equals("execute_script")) {
                String newFilePath = data.get(1);
                if (paths.contains(newFilePath)) {
                    throw new RecursionException();
                }
                paths.add(newFilePath);
                scanner = new Scanner(new File(newFilePath));
            }
        }
        return true;
    }

    private Map<CommandArgs, String> processObjectCommand(String data) throws JsonProcessingException, CreateObjectFromInputParametrsException {
        Map<CommandArgs, String> argsMap = new HashMap<>();
        List<String> currentFilmData = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            currentFilmData.add(scanner.nextLine());
        }
        String serializedArg = objectMapper.writeValueAsString(movieFactory.createMovie(currentFilmData));
        argsMap.put(CommandArgs.OBJECT_ARGUMENT, serializedArg);
        if (data != null) {
            argsMap.put(CommandArgs.SIMPLE_ARGUMENT, data);
        }
        return argsMap;
    }

    private Map<CommandArgs, String> processSimpleArg(String data) {
        Map<CommandArgs, String> map = new HashMap<>();
        map.put(CommandArgs.SIMPLE_ARGUMENT, data);
        return map;
    }

    private Map<String, CommandType> initMap(Map<CommandType, List<String>> commandTypeMap) {
        Map<String, CommandType> map = new HashMap<>();
        for (CommandType commandType : commandTypeMap.keySet()) {
            for (String commandName : commandTypeMap.get(commandType)) {
                map.put(commandName, commandType);
            }
        }
        return map;
    }

}
