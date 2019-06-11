package bp.model.resources.type;

import bp.exceptions.UnknownIdentificatorException;

public enum Titles {
    TECHNOLOGY("TECHNOLOGY"),
    TYPE_ONE("TYPE_ONE"),
    TYPE_TWO("TYPE_TWO"),
    TYPE_THREE("TYPE_THREE"),
    MRF_ID("MRF_ID"),
    PRODUCT("PRODUCT"),
    ACTION("ACTION"),
    ERROR("ERROR");

    Titles(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    private String title;

    public static Titles parseTitle(String str) {
        if (TECHNOLOGY.title.equals(str)) return TECHNOLOGY;
        if (TYPE_ONE.title.equals(str)) return TYPE_ONE;
        if (TYPE_TWO.title.equals(str)) return TYPE_TWO;
        if (TYPE_THREE.title.equals(str)) return TYPE_THREE;
        if (MRF_ID.title.equals(str)) return MRF_ID;
        if (PRODUCT.title.equals(str)) return PRODUCT;
        if (ACTION.title.equals(str)) return ACTION;
        if (ERROR.title.equals(str)) return ERROR;
        throw new UnknownIdentificatorException("Unknown title: " + str);
    }
}
