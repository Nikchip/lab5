package ru.lab.businessLogic.commands.impls;

import org.apache.log4j.Logger;
import ru.lab.businessLogic.commands.AbstractCommand;
import ru.lab.businessLogic.commands.CommandArgs;
import ru.lab.businessLogic.commands.CommandType;
import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResult;
import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResultStatus;
import ru.lab.businessLogic.domain.interaction.query.Query;
import ru.lab.businessLogic.domain.movies.Movie;
import ru.lab.businessLogic.services.logicServices.validators.commandValidators.impls.ClearCommandValidator;
import ru.lab.businessLogic.storage.CollectionWrapper;

import java.util.Map;
import java.util.Set;

public class ClearCommand extends AbstractCommand {

    private final Logger logger = Logger.getLogger(ClearCommand.class);
    private final CollectionWrapper<Movie> collectionWrapper;

    public ClearCommand(Set<CommandArgs> commandArgsSet,
                        CollectionWrapper<Movie> collectionWrapper) {
        super(CommandType.CLEAR, "clear", commandArgsSet, "clear collection"); //todo replace init
        this.collectionWrapper = collectionWrapper;
        this.validator = new ClearCommandValidator();
        logger.info("command " + commandName + " initialized");
    }

    @Override
    public CommandResult execute(Query query) throws Exception {
        logger.info("New query to clear command, query info: " + query.toString());
        Map<CommandArgs, String> args = query.getArgs();
        validator.validateArgs(args);
        collectionWrapper.clear();
        logger.info("command clear successfully executed!");
        return new CommandResult(
                "Collection was successfully cleared",
                CommandResultStatus.SUCCESS);

    }
}
