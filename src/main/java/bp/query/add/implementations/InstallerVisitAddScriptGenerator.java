package bp.query.add.implementations;

import bp.checker.entity.implementations.WfmMap;
import bp.query.add.AbstractAddScriptGenerator;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static bp.model.Action.ADD;
import static bp.model.ParametersType.INSTALLER_VISIT;
import static bp.utils.FileNamesGenerator.generateFileName;

public class InstallerVisitAddScriptGenerator extends AbstractAddScriptGenerator<WfmMap> {
    public InstallerVisitAddScriptGenerator(Connection connection) {
        super(connection);
    }

    // TODO normally not at night
    @Override
    public void generateAddScript(List<WfmMap> addRows) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("DECLARE");
        lines.add("objid NUMBER;");
        lines.add("BEGIN");
        lines.add("SELECT obj_num INTO objid FROM sa.adp_tbl_oid " +
        "WHERE type_id = (SELECT o.type_id FROM sa.adp_object o WHERE o.type_name = 'x_c_wfm_map') FOR UPDATE;");
        lines.add("UPDATE sa.adp_tbl_oid SET obj_num = objid + 1");
        lines.add("WHERE type_id = (SELECT o.type_id FROM sa.adp_object o WHERE o.type_name = 'x_c_wfm_map');");
        lines.add("INSERT ALL");
        for (int i = 0; i < addRows.size(); i++) {
            lines.add("INTO sa.TABLE_X_C_WFM_MAP ");
            lines.add("(OBJID, DEV, X_IS_ACTIVE, X_TECH_FAMILY, X_C_WFM_MAP2C_TYPE_LVL3, X_C_WFM_MAP2TERRITORY, X_C_WFM_MAP2PART_NUM)");
            lines.add("VALUES");
            lines.add(MessageFormat.format("(objid + {0, number}, '1', '1', ''{1}'', ''{2}'', ''{3}'', ''{4}'')",
                    i + 1,
                    addRows.get(i).getTechnologyName(),
                    addRows.get(i).getTypeThreeObjid(),
                    addRows.get(i).getTerritoryObjid(),
                    addRows.get(i).getPartNumObjid()));
        }
        lines.add("SELECT * FROM dual;");
        lines.add("COMMIT;");
        lines.add("END;");
        Path file = Paths.get(generateFileName(INSTALLER_VISIT, ADD));
        Files.write(file, lines, Charset.forName("UTF-8"));
    }

    @Override
    public void generateRevertAddScript() {

    }
}
