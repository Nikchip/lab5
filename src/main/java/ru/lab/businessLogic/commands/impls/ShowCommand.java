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

public class ShowCommand extends AbstractCommand {

    private final Logger logger = Logger.getLogger(ClearCommand.class);
    private final CollectionWrapper<Movie> collectionWrapper;

    public ShowCommand(Set<CommandArgs> commandArgsSet,
                       CollectionWrapper<Movie> collectionWrapper) {
        super(CommandType.CLEAR, "show", commandArgsSet, "show all collection");
        this.collectionWrapper = collectionWrapper;
        this.validator = new ClearCommandValidator();
        logger.info("command " + commandName + " initialized");
    }

    @Override
    public CommandResult execute(Query query) throws Exception {
        logger.info("New query to show command, query info: " + query.toString());
        Map<CommandArgs, String> args = query.getArgs();
        validator.validateArgs(args);
        List<Movie> movieList = collectionWrapper.getList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Collection:\n");
        for (Movie movie : movieList) {
            stringBuilder
                    .append(movie.toString())
                    .append("\n");
        }
        logger.info("command show successfully executed!");
        return new CommandResult(
                stringBuilder.toString(),
                CommandResultStatus.SUCCESS);

    }

}
