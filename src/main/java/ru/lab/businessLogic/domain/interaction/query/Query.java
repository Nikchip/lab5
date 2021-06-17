package ru.lab.businessLogic.domain.interaction.query;

import ru.lab.businessLogic.commands.CommandArgs;

import java.util.Map;

/**
 * дто для запроса пользователя
 * содержит имя команды, аргументы и тип запроса
 */
public class Query {
    private String commandName;
    private Map<CommandArgs, String> args;
    private QueryType queryType;

    public Query(String commandName, Map<CommandArgs, String> args, QueryType queryType) {
        this.commandName = commandName;
        this.args = args;
        this.queryType = queryType;
    }

    public Query() {
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public Map<CommandArgs, String> getArgs() {
        return args;
    }

    public void setArgs(Map<CommandArgs, String> args) {
        this.args = args;
    }

    public QueryType getQueryType() {
        return queryType;
    }

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    @Override
    public String toString() {
        return "Query{" +
                "commandName='" + commandName + '\'' +
                ", args=" + args +
                ", queryType=" + queryType +
                '}';
    }
}
