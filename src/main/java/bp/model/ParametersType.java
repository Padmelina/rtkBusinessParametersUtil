package bp.model;

import lombok.Getter;

public enum ParametersType {
    INSTALLER_VISIT ("INSTALLER_VISIT"),
    ONLINE_TRANSFER ("ONLINE_TRANSFER");

    @Getter
    private String type;

    ParametersType(String type) {
        this.type = type;
    }

    public static ParametersType parseType(String str) {
        if (INSTALLER_VISIT.type.equals(str)) return INSTALLER_VISIT;
        if (ONLINE_TRANSFER.type.equals(str)) return ONLINE_TRANSFER;
        return null;
    }
}
