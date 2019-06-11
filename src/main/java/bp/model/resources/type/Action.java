package bp.model.resources.type;

import bp.exceptions.UnknownIdentificatorException;

public enum Action {
    ADD("add"),
    DELETE("delete"),
    REVERT("revert"),
    CHECK("check"),
    UNKNOWN("unknown");

    private String action;

    Action(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public static Action parseAction(String str) {
        if (ADD.action.equals(str)) return ADD;
        if (DELETE.action.equals(str)) return DELETE;
        if (CHECK.action.equals(str)) return CHECK;
        if (REVERT.action.equals(str)) return REVERT;
        throw new UnknownIdentificatorException("Unknown action: " + str);
    }
}
