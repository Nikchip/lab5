package ru.lab.businessLogic.commands.impls;

import org.apache.log4j.Logger;
import ru.lab.businessLogic.commands.AbstractCommand;
import ru.lab.businessLogic.commands.CommandArgs;
import ru.lab.businessLogic.commands.CommandType;
import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResult;
import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResultStatus;
import ru.lab.businessLogic.domain.interaction.query.Query;
import ru.lab.businessLogic.services.logicServices.validators.commandValidators.impls.ClearCommandValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HelpCommand extends AbstractCommand {

    private final List<String> commandDescription;
    private final Logger logger = Logger.getLogger(HelpCommand.class);

    public HelpCommand(Set<CommandArgs> commandArgsSet,
                       List<AbstractCommand> abstractCommands) {
        super(CommandType.CLEAR, "help", commandArgsSet, "information about commands");
        this.validator = new ClearCommandValidator();
        commandDescription = initCommandList(abstractCommands);
        logger.info("Command " + commandName + " initialized");

    }

    private List<String> initCommandList(List<AbstractCommand> list) {
        List<String> descriptions = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (AbstractCommand command : list) {
            stringBuilder
                    .append(command.getCommandName())
                    .append(": ")
                    .append(command.getDescription())
                    .append("\n");
            descriptions.add(stringBuilder.toString());
            stringBuilder.setLength(0);
        }
        return descriptions;
    }

    private String getAllInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String desc : commandDescription) {
            stringBuilder.append(desc);
        }
        return stringBuilder.toString();
    }

    @Override
    public CommandResult execute(Query query) throws Exception {
        logger.info("New query to help command, query info: " + query.toString());
            Map<CommandArgs, String> args = query.getArgs();
            validator.validateArgs(args);
            logger.info("command help successfully executed!");
            return new CommandResult(
                    getAllInfo(),
                    CommandResultStatus.SUCCESS);
    }
}
