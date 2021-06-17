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

import java.util.List;
import java.util.Map;
import java.util.Set;

public class PrintDescendingCommand extends AbstractCommand {

    private final Logger logger = Logger.getLogger(AddCommand.class);
    private final CollectionWrapper<Movie> collectionWrapper;

    public PrintDescendingCommand(Set<CommandArgs> commandArgsSet,
                                  CollectionWrapper<Movie> collectionWrapper) {
        super(CommandType.CLEAR, "print_descending", commandArgsSet, "show descending collection");
        this.collectionWrapper = collectionWrapper;
        this.validator = new ClearCommandValidator();
        logger.info("command " + commandName + " initialized");
    }


    @Override
    public CommandResult execute(Query query) throws Exception {
        logger.info("New query to print_descending command, query info: " + query.toString());
        Map<CommandArgs, String> args = query.getArgs();
        validator.validateArgs(args);

        StringBuilder answer = new StringBuilder();
        List<Movie> descending = collectionWrapper.sort(true);
        for (Movie movie : descending) {
            answer.append(movie).append("\n");
        }
        logger.info("command print_descending successfully executed");
        return new CommandResult(
                answer.toString(),
                CommandResultStatus.SUCCESS
        );
    }
}
