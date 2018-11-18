package bp.model;

public enum FormMessages {
    NoFileMessage("NoFileMessage"),
    FileParsingMessage("FileParsingMessage"),
    FileCorrectMessage("FileCorrectMessage"),
    FileCheckingMessage("FileCheckingMessage"),
    ScriptMessage("ScriptMessage"),
    CheckEndedMessage("CheckEndedMessage"),
    StatisticSuccessMessage("StatisticSuccessMessage"),
    StatisticErrorsMessage("StatisticErrorsMessage"),
    TitleMessage("TitleMessage");

    private String MessageName;

    FormMessages(String MessageName) {
        this.MessageName = MessageName;
    }

    public String getMessageName() {
        return MessageName;
    }

    public static FormMessages parseMessage(String name) {
        if (FileParsingMessage.MessageName.equals(name)) return FileParsingMessage;
        if (NoFileMessage.MessageName.equals(name)) return NoFileMessage;
        if (FileCorrectMessage.MessageName.equals(name)) return FileCorrectMessage;
        if (FileCheckingMessage.MessageName.equals(name)) return FileCheckingMessage;
        if (ScriptMessage.MessageName.equals(name)) return ScriptMessage;
        if (StatisticSuccessMessage.MessageName.equals(name)) return StatisticSuccessMessage;
        if (ScriptMessage.MessageName.equals(name)) return ScriptMessage;
        if (StatisticErrorsMessage.MessageName.equals(name)) return StatisticErrorsMessage;
        if (TitleMessage.MessageName.equals(name)) return TitleMessage;
        return null;
    }
}
