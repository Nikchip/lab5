package ru.lab.businessLogic.domain.interaction.commandResultData;

import java.time.LocalDate;

/**
 * ДТО для результата командыю Содержит статус результата и сообщение,
 * которое будет выведено для пользователя
 */
public class CommandResult {
    private String resultInfo;
    private CommandResultStatus commandResultStatus;

    public CommandResult(String resultInfo, CommandResultStatus commandResultStatus) {
        this.resultInfo = resultInfo;
        this.commandResultStatus = commandResultStatus;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public CommandResultStatus getCommandResultStatus() {
        return commandResultStatus;
    }

    public void setCommandResultStatus(CommandResultStatus commandResultStatus) {
        this.commandResultStatus = commandResultStatus;
    }

    @Override
    public String toString() {
        return "CommandResult{" +
                "resultInfo='" + resultInfo + '\'' +
                ", commandResultStatus=" + commandResultStatus +
                '}';
    }
}
