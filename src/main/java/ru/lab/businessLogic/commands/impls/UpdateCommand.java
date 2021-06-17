package ru.lab.businessLogic.commands.impls;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import ru.lab.businessLogic.commands.AbstractCommand;
import ru.lab.businessLogic.commands.CommandArgs;
import ru.lab.businessLogic.commands.CommandType;
import ru.lab.businessLogic.commands.exceptions.CustomCastException;
import ru.lab.businessLogic.commands.exceptions.CustomParseException;
import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResult;
import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResultStatus;
import ru.lab.businessLogic.domain.interaction.query.Query;
import ru.lab.businessLogic.domain.movies.Movie;
import ru.lab.businessLogic.services.logicServices.validators.commandValidators.impls.ArgumentCommandValidator;
import ru.lab.businessLogic.storage.CollectionWrapper;

import java.util.Map;
import java.util.Set;

public class UpdateCommand extends AbstractCommand {

    private final Logger logger = Logger.getLogger(AddCommand.class);
    private final CollectionWrapper<Movie> collectionWrapper;
    private final ObjectMapper xmlMapper;

    public UpdateCommand(Set<CommandArgs> commandArgsSet,
                         CollectionWrapper<Movie> collectionWrapper,
                         ObjectMapper xmlMapper) {
        super(CommandType.MIXED, "update", commandArgsSet, "update element in id");
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
            long id = Long.parseLong(args.get(CommandArgs.SIMPLE_ARGUMENT));
            collectionWrapper.replace(id, movie);
            logger.info("Command update successfully executed!");
            return new CommandResult(
                    "movie " + movie.getName() + " now in " + id,
                    CommandResultStatus.SUCCESS
            );
        } catch (JacksonException e) {
            throw new CustomParseException();
        } catch (NumberFormatException e) {
            throw new CustomCastException();
        }

    }
}
