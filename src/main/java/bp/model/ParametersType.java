package bp.model;

import lombok.Getter;

public enum ParametersType {
    INSTALLER_VISIT ("INSTALLER_VISIT");

    @Getter
    private String type;

    ParametersType(String type) {
        this.type = type;
    }

    public static ParametersType parseType(String str) {
        if (INSTALLER_VISIT.type.equals(str)) return INSTALLER_VISIT;
        return null;
    }
}
