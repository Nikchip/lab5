package ru.lab.businessLogic.commands.impls;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class AddCommand extends AbstractCommand {

    private final Logger logger = Logger.getLogger(AddCommand.class);
    private final CollectionWrapper<Movie> collectionWrapper;
    private final ObjectMapper objectMapper;

    public AddCommand(Set<CommandArgs> commandArgsSet,
                      CollectionWrapper<Movie> collectionWrapper,
                      ObjectMapper xmlMapper) {
        super(CommandType.OBJECT_ARG, "add", commandArgsSet, "add element to collection");
        this.collectionWrapper = collectionWrapper;
        this.objectMapper = xmlMapper;
        this.validator = new ArgumentCommandValidator(commandArgsSet);
        logger.info("command " + commandName + " initialized");
    }


    @Override
    public CommandResult execute(Query query) throws Exception {
        try {
            logger.info("New query to add command, query info: " + query.toString());
            Map<CommandArgs, String> args = query.getArgs();
            validator.validateArgs(args);
            Movie movie = objectMapper.readValue(args.get(CommandArgs.OBJECT_ARGUMENT), Movie.class);
            collectionWrapper.add(movie);
            logger.info("Command add successfully executed!");
            return new CommandResult(
                    "Object with name " + movie.getName() + " successfully added",
                    CommandResultStatus.SUCCESS);
        } catch (JacksonException e) {
            throw new CustomCastException();
        }

    }
}
