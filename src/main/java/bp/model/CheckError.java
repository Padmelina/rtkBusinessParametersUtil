package bp.model;

public enum CheckError {
    Ok("Success"),
    IncorrectTechnology ("Incorrect tehnology"),
    IncorrectCaseTypes ("Incorrect case types mapping"),
    IncorrectTerritoryId ("Incorrect MRF ID"),
    IncorrectProduct ("Incorrect product"),
    IncorrectAction ("Incorrect action"),
    RecordAlreadyExists ("Adding record is already exists in target table"),
    RecordNotExists ("Deleting record does not exist in target table");

    CheckError(String errorText) {
        this.errorText = errorText;
    }

    private String errorText;

    public String getErrorText() {
        return errorText;
    }
}
