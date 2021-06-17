package ru.lab.businessLogic.services.logicServices.executors.impls;

import ru.lab.businessLogic.commands.CommandArgs;
import ru.lab.businessLogic.domain.interaction.query.Query;
import ru.lab.businessLogic.domain.interaction.query.QueryType;
import ru.lab.businessLogic.services.factories.queryFactories.QueryFactory;
import ru.lab.businessLogic.services.logicServices.executors.QueryTypeCreator;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.MissedArgumentException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleArgumentCreator extends QueryTypeCreator {
    public SimpleArgumentCreator(QueryFactory queryFactory) {
        super(queryFactory);
    }

    @Override
    public Query createQuery(List<String> commandData) throws MissedArgumentException {
        if(commandData.size() == 1){
            throw new MissedArgumentException();
        }
        return queryFactory.createDefaultQuery(
                commandData.get(0),
                QueryType.COMMAND_REQUEST,
                processSimpleCommand(commandData.get(1)));
    }

    private Map<CommandArgs, String> processSimpleCommand(String arg){
        Map<CommandArgs, String> args = new HashMap<>();
        args.put(CommandArgs.SIMPLE_ARGUMENT, arg);
        return args;
    }
}
