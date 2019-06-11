package bp.model.resources.type;

import bp.exceptions.UnknownIdentificatorException;
import lombok.Getter;

/**
 * Types of business parameters could be processed by util.
 * If you add new type - add it here and in mapping _sheetNames_ in localize_resources.yml.
 */
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
        throw new UnknownIdentificatorException("Unknown business parameters type: " + str);
    }
}
