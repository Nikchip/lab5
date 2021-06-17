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
import ru.lab.businessLogic.domain.movies.MpaaRating;
import ru.lab.businessLogic.services.logicServices.validators.commandValidators.impls.ArgumentCommandValidator;
import ru.lab.businessLogic.storage.CollectionWrapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RemoveAllByCommand extends AbstractCommand {

    private final Logger logger = Logger.getLogger(AddCommand.class);
    private final CollectionWrapper<Movie> collectionWrapper;

    public RemoveAllByCommand(Set<CommandArgs> commandArgsSet,
                              CollectionWrapper<Movie> collectionWrapper) {
        super(CommandType.SIMPLE_ARG, "remove_by_mpaa", commandArgsSet, "remove all by mpaa rating");
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
            MpaaRating mpaaRating = MpaaRating.valueOf(args.get(CommandArgs.SIMPLE_ARGUMENT));
            List<Movie> movieList = collectionWrapper.getList();
            int count = 0;
            for (Movie movie : movieList) {
                if (movie.getMpaaRating().equals(mpaaRating)) {
                    count++;
                    collectionWrapper.remove(movie.getId());
                }
            }
            logger.info("command remove_by_mpaa successfully executed");
            if(count == 0){
                return new CommandResult(
                        "Not found any elements with mpaa " + mpaaRating,
                        CommandResultStatus.SUCCESS
                );
            }
            return new CommandResult(
                    "deleting objects with the mpaa parameter " + mpaaRating + " finished. Number of deleted objects: " + count,
                    CommandResultStatus.SUCCESS
            );
        } catch (IllegalArgumentException e){
            throw new CustomCastException();
        }
    }
}
