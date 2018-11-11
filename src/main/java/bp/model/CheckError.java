package bp.model;

public enum CheckError {
    Ok("Success"),
    IncorrectTechnology ("Incorrect tehnology"),
    IncorrectCaseTypes ("Incorrect case types mapping"),
    IncorrectTerritoryId ("Incorrect MRF ID"),
    IncorrectProduct ("Incorrect product");

    CheckError(String errorText) {
        this.errorText = errorText;
    }

    private String errorText;

    public String getErrorText() {
        return errorText;
    }
}
