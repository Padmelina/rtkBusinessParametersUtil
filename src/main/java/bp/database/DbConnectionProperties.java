package bp.database;

import lombok.Getter;
import lombok.Setter;

public class DbConnectionProperties {
    @Getter
    @Setter
    private String url;
    @Getter
    @Setter
    private String login;
    @Getter
    @Setter
    private String password;
}
