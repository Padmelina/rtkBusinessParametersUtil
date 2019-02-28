package bp.model;

public enum CheckError {
    Ok,
    IncorrectTechnology,
    IncorrectCaseTypes,
    IncorrectTerritoryId,
    IncorrectProduct,
    IncorrectAction,
    RecordAlreadyExists,
    RecordContainsEmptyValues,
    RecordNotExists;

    public static CheckError parseError(String error) {
        if (IncorrectTechnology.toString().equals(error)) return IncorrectTechnology;
        if (IncorrectCaseTypes.toString().equals(error)) return IncorrectCaseTypes;
        if (IncorrectTerritoryId.toString().equals(error)) return IncorrectTerritoryId;
        if (IncorrectProduct.toString().equals(error)) return IncorrectProduct;
        if (IncorrectAction.toString().equals(error)) return IncorrectAction;
        if (RecordAlreadyExists.toString().equals(error)) return RecordAlreadyExists;
        if (RecordNotExists.toString().equals(error)) return RecordNotExists;
        if (RecordContainsEmptyValues.toString().equals(error)) return RecordContainsEmptyValues;
        return Ok;
    }

}
