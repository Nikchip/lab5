package ru.lab.businessLogic.commands.impls;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import ru.lab.businessLogic.commands.AbstractCommand;
import ru.lab.businessLogic.commands.CommandArgs;
import ru.lab.businessLogic.commands.CommandType;
import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResult;
import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResultStatus;
import ru.lab.businessLogic.domain.interaction.query.Query;
import ru.lab.businessLogic.domain.movies.Movie;
import ru.lab.businessLogic.services.logicServices.commandServices.CommandService;
import ru.lab.businessLogic.services.logicServices.validators.commandValidators.impls.ArgumentCommandValidator;
import ru.lab.businessLogic.storage.CollectionWrapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExecuteScriptCommand extends AbstractCommand {
    private final Logger logger = Logger.getLogger(AddCommand.class);
    private final ObjectMapper objectMapper;
    private final CommandService<CommandResult, Query> commandService;

    public ExecuteScriptCommand(Set<CommandArgs> commandArgsSet,
                                ObjectMapper objectMapper,
                                CommandService commandService) {
        super(CommandType.SCRIPT, "execute_script", commandArgsSet, "execute commands from script");
        this.objectMapper = objectMapper;
        this.commandService = commandService;
        this.validator = new ArgumentCommandValidator(commandArgsSet);
        logger.info("command " + commandName + " initialized");
    }

    @Override
    public CommandResult execute(Query query) throws Exception {
        logger.info("New query to script command, query info: " + query.toString());
        Map<CommandArgs, String> args = query.getArgs();
        validator.validateArgs(args);
        List<Query> queries = objectMapper.readValue(args.get(CommandArgs.SIMPLE_ARGUMENT), new TypeReference<List<Query>>(){});
        StringBuilder answers = new StringBuilder();
        for(Query q : queries){
            answers.append(commandService.execute(q).getResultInfo());
            answers.append("\n");
        }
        return new CommandResult(
                answers.toString(),
                CommandResultStatus.SUCCESS
        );
    }
}
