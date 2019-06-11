package bp.model.resources.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class FileNames {
    @Getter
    private String mainScriptName;
    @Getter
    private String revertScriptName;
    @Getter
    private String checkMainScriptName;
    @Getter
    private String checkRevertScriptName;
}
