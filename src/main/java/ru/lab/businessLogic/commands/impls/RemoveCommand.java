package ru.lab.businessLogic.commands.impls;

import org.apache.log4j.Logger;
import ru.lab.businessLogic.commands.AbstractCommand;
import ru.lab.businessLogic.commands.CommandArgs;
import ru.lab.businessLogic.commands.CommandType;
import ru.lab.businessLogic.commands.exceptions.CustomCastException;
import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResult;
import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResultStatus;
import ru.lab.businessLogic.domain.interaction.query.Query;
import ru.lab.businessLogic.domain.movies.Movie;
import ru.lab.businessLogic.services.logicServices.validators.commandValidators.impls.ArgumentCommandValidator;
import ru.lab.businessLogic.storage.CollectionWrapper;

import java.util.Map;
import java.util.Set;

public class RemoveCommand extends AbstractCommand {
    private final Logger logger = Logger.getLogger(AddCommand.class);
    private final CollectionWrapper<Movie> collectionWrapper;

    public RemoveCommand(Set<CommandArgs> commandArgsSet,
                         CollectionWrapper<Movie> collectionWrapper) {
        super(CommandType.SIMPLE_ARG, "remove_by_id", commandArgsSet, "remove elem by id");
        this.collectionWrapper = collectionWrapper;
        this.validator = new ArgumentCommandValidator(commandArgsSet);
        logger.info("command " + commandName + " initialized");
    }

    @Override
    public CommandResult execute(Query query) throws Exception {
        logger.info("New query to add command, query info: " + query.toString());
        try {
            Map<CommandArgs, String> args = query.getArgs();
            validator.validateArgs(args);
            long id = Long.parseLong(args.get(CommandArgs.SIMPLE_ARGUMENT));
            collectionWrapper.remove(id);
            logger.info("command remove_by_id successfully executed");
            return new CommandResult(
                    "element with id " + id + " successfully removed",
                    CommandResultStatus.SUCCESS
            );
        } catch (NumberFormatException e){
            throw new CustomCastException();
        }
    }
}
