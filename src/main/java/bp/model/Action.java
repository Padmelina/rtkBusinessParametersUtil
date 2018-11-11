package bp.model;

public enum Action {
    ADD("add"),
    DELETE("delete");

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
        return null;
    }
}
