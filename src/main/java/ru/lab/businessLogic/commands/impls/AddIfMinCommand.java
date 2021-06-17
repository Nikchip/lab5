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
import java.util.Objects;
import java.util.Set;

public class AddIfMinCommand extends AbstractCommand {

    private final Logger logger = Logger.getLogger(AddIfMinCommand.class);
    private final CollectionWrapper<Movie> collectionWrapper;
    private final ObjectMapper objectMapper;

    public AddIfMinCommand(Set<CommandArgs> commandArgsSet,
                           CollectionWrapper<Movie> collectionWrapper,
                           ObjectMapper objectMapper) {
        super(CommandType.OBJECT_ARG, "add_if_min", commandArgsSet, "add new object to collection if count of oscar minimun"); //todo replace init
        this.collectionWrapper = collectionWrapper;
        this.objectMapper = objectMapper;
        this.validator = new ArgumentCommandValidator(commandArgsSet);
        logger.info("command " + commandName + " initialized");
    }

    @Override
    public CommandResult execute(Query query) throws Exception {
        logger.info("New query to add if min command, query info: " + query.toString());
        try {
            Map<CommandArgs, String> args = query.getArgs();
            validator.validateArgs(args);
            Movie movie = objectMapper.readValue(args.get(CommandArgs.OBJECT_ARGUMENT), Movie.class);
            Movie minCollectionMovie = collectionWrapper.min();
            logger.info("Command add successfully executed!");
            if (Objects.isNull(minCollectionMovie)) {
                collectionWrapper.add(movie);
                return new CommandResult(
                        "Collection was empty! Your element was successfully added",
                        CommandResultStatus.SUCCESS
                );
            }
            if (movie.getOscarsCount() < minCollectionMovie.getOscarsCount()) {
                collectionWrapper.add(movie);
                return new CommandResult(
                        "Object with name \"" + movie.getName() + "\" successfully added. Previous min elem: " + minCollectionMovie.getName(),
                        CommandResultStatus.SUCCESS);
            }
            return new CommandResult(
                    "Object with name \"" + movie.getName() + "\" not min! Min object count of oscar: " + minCollectionMovie.getOscarsCount(),
                    CommandResultStatus.SUCCESS);
        } catch (JacksonException e) {
            throw new CustomCastException();
        }

    }
}

