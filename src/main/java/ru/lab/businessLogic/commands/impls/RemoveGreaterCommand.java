package ru.lab.businessLogic.commands.impls;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import ru.lab.businessLogic.commands.AbstractCommand;
import ru.lab.businessLogic.commands.CommandArgs;
import ru.lab.businessLogic.commands.CommandType;
import ru.lab.businessLogic.commands.exceptions.CustomParseException;
import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResult;
import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResultStatus;
import ru.lab.businessLogic.domain.interaction.query.Query;
import ru.lab.businessLogic.domain.movies.Movie;
import ru.lab.businessLogic.services.logicServices.validators.commandValidators.impls.ArgumentCommandValidator;
import ru.lab.businessLogic.storage.CollectionWrapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RemoveGreaterCommand extends AbstractCommand {

    private final Logger logger = Logger.getLogger(AddCommand.class);
    private final CollectionWrapper<Movie> collectionWrapper;
    private final ObjectMapper xmlMapper;

    public RemoveGreaterCommand(Set<CommandArgs> commandArgsSet,
                                CollectionWrapper<Movie> collectionWrapper,
                                ObjectMapper xmlMapper) {
        super(CommandType.OBJECT_ARG, "remove_greater", commandArgsSet, "remove all greater elements");
        this.collectionWrapper = collectionWrapper;
        this.xmlMapper = xmlMapper;
        this.validator = new ArgumentCommandValidator(commandArgsSet);
        logger.info("command " + commandName + " initialized");
    }


    @Override
    public CommandResult execute(Query query) throws Exception {
        logger.info("New query to add command, query info: " + query.toString());
        try {
            Map<CommandArgs, String> args = query.getArgs();
            validator.validateArgs(args);
            Movie movie = xmlMapper.readValue(args.get(CommandArgs.OBJECT_ARGUMENT), Movie.class);
            List<Movie> movieList = collectionWrapper.getList();
            int count = 0;
            for (Movie movies : movieList) {
                if (movies.getOscarsCount() > movie.getOscarsCount()) {
                    collectionWrapper.remove(movies.getId());
                    count++;
                }
            }
            logger.info("command remove_greater successfully executed");
            return new CommandResult(
                    "deleting objects with oscar count more than " + movie.getOscarsCount() + " finished. Number of deleted objects: " + count,
                    CommandResultStatus.SUCCESS
            );
        } catch (JacksonException e){
            throw new CustomParseException();
        }
    }
}
