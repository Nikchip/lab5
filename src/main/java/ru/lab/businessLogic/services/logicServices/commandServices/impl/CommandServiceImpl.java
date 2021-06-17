package ru.lab.businessLogic.services.logicServices.commandServices.impl;

import org.apache.log4j.Logger;
import ru.lab.businessLogic.commands.AbstractCommand;
import ru.lab.businessLogic.commands.CommandType;
import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResult;
import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResultStatus;
import ru.lab.businessLogic.domain.interaction.query.Query;
import ru.lab.businessLogic.services.logicServices.commandServices.CommandService;

import java.util.*;
import java.util.stream.Collectors;

public class CommandServiceImpl implements CommandService<CommandResult, Query> {

    private final Logger logger = Logger.getLogger(CommandServiceImpl.class);
    private Map<String, AbstractCommand> commandMap;

    public CommandServiceImpl(List<AbstractCommand> commands) {
        commandMap = new HashMap<>();
        addAllCommands(commands);
        logger.info("Command service initialized.");
    }

    public CommandServiceImpl() {
        commandMap = new HashMap<>();
        logger.info("Empty command service initialize");
    }


    @Override
    public List<AbstractCommand> getAllCommands() {
        return new ArrayList<>(commandMap.values());
    }

    @Override
    public Map<CommandType, List<String>> getCommandMap() {
        Map<CommandType, List<String>> map = new HashMap<>();
        for (CommandType commandType : getCommandTypes()) {
            List<String> currentTypeCommand = commandMap.values().stream()
                    .filter(x -> x.getCommandType().equals(commandType))
                    .map(AbstractCommand::getCommandName)
                    .collect(Collectors.toList());
            map.put(commandType, currentTypeCommand);
        }
        return map;
    }

    private Set<CommandType> getCommandTypes() {
        Set<CommandType> commandTypes = new HashSet<>();
        for (AbstractCommand command : commandMap.values()) {
            commandTypes.add(command.getCommandType());
        }
        return commandTypes;
    }

    @Override
    public AbstractCommand addNewCommand(AbstractCommand command) {
        commandMap.put(command.getCommandName(), command);
        return command;
    }

    @Override
    public List<AbstractCommand> addAllCommands(List<AbstractCommand> commands) {
        for (AbstractCommand command : commands) {
            commandMap.put(command.getCommandName(), command);
        }
        logger.info("Commands uploaded");
        return commands;
    }

    @Override
    public CommandResult execute(Query query) {
        try {
            return commandMap.get(query.getCommandName()).run(query);
        } catch (NullPointerException e) {
            return new CommandResult("Command not found!", CommandResultStatus.COMMAND_EXCEPTION);
        }
    }
}
