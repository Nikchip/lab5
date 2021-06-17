package ru.lab.businessLogic.commands.impls;

import org.apache.log4j.Logger;
import ru.lab.businessLogic.commands.AbstractCommand;
import ru.lab.businessLogic.commands.CommandArgs;
import ru.lab.businessLogic.commands.CommandType;
import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResult;
import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResultStatus;
import ru.lab.businessLogic.domain.interaction.query.Query;
import ru.lab.businessLogic.domain.movies.Movie;
import ru.lab.businessLogic.services.logicServices.fileServices.FileService;
import ru.lab.businessLogic.services.logicServices.validators.commandValidators.impls.ClearCommandValidator;
import ru.lab.businessLogic.storage.CollectionWrapper;

import java.util.Map;
import java.util.Set;

public class SaveCommand extends AbstractCommand {

    private final Logger logger = Logger.getLogger(SaveCommand.class);
    private final CollectionWrapper<Movie> collectionWrapper;
    private final FileService<Movie> fileService;

    public SaveCommand(Set<CommandArgs> commandArgsSet,
                       CollectionWrapper<Movie> collectionWrapper,
                       FileService<Movie> fileService) {
        super(CommandType.CLEAR, "save", commandArgsSet, "save collection to file");
        this.collectionWrapper = collectionWrapper;
        this.fileService = fileService;
        this.validator = new ClearCommandValidator();
        logger.info("command " + commandName + " initialized");
    }


    @Override
    public CommandResult execute(Query query) throws Exception {
        logger.info("New query to add command, query info: " + query.toString());
        Map<CommandArgs, String> args = query.getArgs();
        validator.validateArgs(args);
        logger.info("command clear successfully executed!");
        fileService.writeToFile(collectionWrapper.getList());
        return new CommandResult(
                "File successfully save to collection",
                CommandResultStatus.SUCCESS
        );

    }
}
