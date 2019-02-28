package bp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class FileNames {
    @Getter
    private String mainScriptName;
    @Getter
    private String revertScriptName;
}
