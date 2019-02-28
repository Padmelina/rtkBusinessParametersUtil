package bp.query.generator.implementations;

import bp.model.entity.InstallerVisit;
import bp.query.generator.QueryAbstractCreator;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static bp.model.Constants.CommonSqlQueries.FROM_DUAL;
import static bp.model.Constants.CommonSqlQueries.SELECT_DUAL;
import static bp.model.Constants.FieldsName.*;
import static bp.model.Constants.SqlKeyWords.*;
import static bp.model.Constants.SqlQueryConstants.CaseTypeList;
import static bp.model.Constants.SqlQueryConstants.InactiveState;
import static bp.model.Constants.SqlQueryConstants.KKFU;
import static bp.model.Constants.TableNames.*;
import static org.jooq.tools.StringUtils.isEmpty;

public class InstallerVisitQueryCreator extends QueryAbstractCreator<InstallerVisit> {
    public InstallerVisitQueryCreator(Map<String, String> constants) {
        super(constants);
    }

    @Override
    public boolean generateAdd(String fileName, List<InstallerVisit> records) throws IOException {
        if (isEmpty(fileName) || records == null || records.size() == 0) return false;
        Path filePath = Paths.get(fileName);
        // TODO: make normal file generating, not like that
        List<String> lines = new ArrayList<>();
        lines.add(DECLARE);
        lines.add("first_objid" + " " + NUMBER + ";");
        lines.add("part" + " " + NUMBER + ";");
        lines.add("territory" + " " + NUMBER + ";");
        lines.add("lvl3" + " " + NUMBER + ";");
        lines.add("record_count" + " " + NUMBER + ";");
        lines.add(TYPE + " virt_row " + IS_RECORD + " (" +
                  TERR_ID + " " + MessageFormat.format(VARCHAR2, 5) + ", " +
                  PART_NUM + " " + MessageFormat.format(VARCHAR2, 30) + ", " +
                  FAMILY + " " + MessageFormat.format(VARCHAR2, 20) + ", " +
                  TYPE1 + " " + MessageFormat.format(VARCHAR2, 40) + ", " +
                  TYPE2 + " " + MessageFormat.format(VARCHAR2, 40) + ", " +
                  TYPE3 + " " + MessageFormat.format(VARCHAR2, 40) + ");");
        lines.add(TYPE + " virt_array " + IS_TABLE_OF + " virt_row " + INDEX_BY + " " + MessageFormat.format(VARCHAR2, 10) + ";");
        lines.add("x_inp_params virt_array;");
        lines.add("x_inp_params_row virt_row;");
        lines.add(BEGIN);
        for (int i = 0; i < records.size(); i++) {
            lines.add(SELECT + " '" +
                    records.get(i).getMrfId() + "', '" +
                    records.get(i).getPartNum() + "', '" +
                    records.get(i).getTechnology() + "', '" +
                    records.get(i).getTypeOne() + "', '" +
                    records.get(i).getTypeTwo() + "', '" +
                    records.get(i).getTypeThree() + "'" );
            lines.add(INTO + " x_inp_params_row");
            lines.add(FROM_DUAL);
            lines.add("x_inp_params(" + i + ") := x_inp_params_row;");
        }
        lines.add("record_count := x_inp_params.count;");
        lines.add(SELECT + " " + OBJ_NUM + " " + INTO + " first_objid " +
                FROM + " " + SA_SCHEMA + ADP_TBL_OID + " " + WHERE + " " + TYPE_ID + " = (" +
                SELECT + " " + "o." + TYPE_ID + " " + FROM + " " +
                SA_SCHEMA + ADP_OBJECT + " o " +
                WHERE + " o." + TYPE_NAME + " = '" + X_C_WFM_MAP + "') " + FOR_UPDATE + ";");
        lines.add(UPDATE + " " + SA_SCHEMA + ADP_TBL_OID + " " +
                SET + " " + OBJ_NUM + " = first_objid + record_count " +
                WHERE + " " + TYPE_ID + " = (" +
                SELECT + " " + "o." + TYPE_ID + " " + FROM + " " +
                SA_SCHEMA + ADP_OBJECT + " o " +
                WHERE + " o." + TYPE_NAME + " = '" + X_C_WFM_MAP + "');");
        lines.add(COMMIT);
        lines.add(FOR + " i " + IN + " 0..record_count-1 " + LOOP);
        lines.add(SELECT + " terr." + OBJID + " " + INTO + " territory " +
                FROM + " " + SA_TABLE + TERRITORY + " terr " +
                WHERE + " terr." + TERR_ID + " = x_inp_params(i)." + TERR_ID + ";");
        lines.add(SELECT + " pn." + OBJID + " " + INTO + " part " +
                FROM + " " + SA_TABLE + PART_NUM + " pn " +
                WHERE + " pn." + PART_NUMBER + " = x_inp_params(i)." + PART_NUM + " " + AND + " pn." + FAMILY + " <> '" + constants.get(KKFU) + "';");
        lines.add(SELECT + " type3." + OBJID);
        lines.add(INTO + " lvl3");
        lines.add(FROM + " " + SA_TABLE + HGBST_LST + " list");
        lines.add(INNER_JOIN + " " + SA_TABLE + HGBST_SHOW + " show1 " + ON + " show1." + OBJID + " = list." + HGBST_LST2HGBST_SHOW);
        lines.add(INNER_JOIN + " " + SA_SCHEMA + MTM_HGBST_ELM0_HGBST_SHOW1 + " m1 " + ON + " show1." + OBJID + " = m1." + HGBST_SHOW2HGBST_ELM);
        lines.add(INNER_JOIN + " " + SA_TABLE + HGBST_ELM + " type1 " + ON + " type1." + OBJID + " = m1." + HGBST_ELM2HGBST_SHOW + " " + AND + " type1." + STATE + "!= '" + InactiveState + "'");
        lines.add(INNER_JOIN + " " + SA_SCHEMA + MTM_HGBST_ELM0_HGBST_SHOW1 + " m2 " + ON + " type1." + OBJID + " = m2." + HGBST_ELM2HGBST_SHOW);
        lines.add(INNER_JOIN + " " + SA_TABLE + HGBST_SHOW + " show2 " + ON + " show2." + OBJID + " = m2." + HGBST_SHOW2HGBST_ELM + " " + AND + " show2." + OBJID + " != show1." + OBJID);
        lines.add(INNER_JOIN + " " + SA_SCHEMA + MTM_HGBST_ELM0_HGBST_SHOW1 + " m3 " + ON + " show2." + OBJID + " = m3." + HGBST_SHOW2HGBST_ELM);
        lines.add(INNER_JOIN + " " + SA_TABLE + HGBST_ELM + " type2 " + ON + " type2." + OBJID + " = m3." + HGBST_ELM2HGBST_SHOW + " " + AND + " type2." + OBJID + " != type1." + OBJID + " " + AND + " type2." + STATE + "!= '" + InactiveState + "'");
        lines.add(INNER_JOIN + " " + SA_SCHEMA + MTM_HGBST_ELM0_HGBST_SHOW1 + " m4 " + ON + " type2." + OBJID + " = m4." + HGBST_ELM2HGBST_SHOW);
        lines.add(INNER_JOIN + " " + SA_TABLE + HGBST_SHOW + " show3 " + ON + " show3." + OBJID + " = m4." + HGBST_SHOW2HGBST_ELM + " " + AND + " " + "show3." + OBJID + " = m4." + HGBST_SHOW2HGBST_ELM + " " +  AND + " show3." + OBJID + " != show2." + OBJID);
        lines.add(INNER_JOIN + " " + SA_SCHEMA + MTM_HGBST_ELM0_HGBST_SHOW1 + " m5 " + ON + " show3." + OBJID + " = m5." + HGBST_SHOW2HGBST_ELM);
        lines.add(INNER_JOIN + " " + SA_TABLE + HGBST_ELM + " type3 " + ON + " type3." + OBJID + " = m5." + HGBST_ELM2HGBST_SHOW + " " + AND + " type3." + OBJID + " !=  type2." + OBJID + " " + AND + " type3." + STATE  + "!= '" + InactiveState + "'");
        lines.add(WHERE + " list." + TITLE + " = '" + CaseTypeList + "' " + AND + " type1." + TITLE + " = x_inp_params(i).TYPE1 " + AND + " type2." + TITLE + " = x_inp_params(i).TYPE2 " + AND + " type3." + TITLE + " = x_inp_params(i).TYPE3;");
        lines.add(INSERT_ALL);
        lines.add(INTO + " " + SA_TABLE + X_C_WFM_MAP);
        lines.add("(" + OBJID + ", " + DEV + ", " + X_IS_ACTIVE + ", " + X_TECH_FAMILY + ", " + X_C_WFM_MAP2C_TYPE_LVL3 + ", " + X_C_WFM_MAP2TERRITORY + ", " + X_C_WFM_MAP2PART_NUM + ")");
        lines.add(VALUES);
        lines.add("(first_objid + i, 1, 1, x_inp_params(i).FAMILY, lvl3, territory, part)");
        lines.add(SELECT_DUAL);
        lines.add(COMMIT);
        lines.add(END_LOOP);
        lines.add(TERMINAL_END);
        Files.write(filePath, lines, Charset.defaultCharset());
        return true;
    }

    @Override
    public boolean generateDelete(String fileName, List<InstallerVisit> records) throws IOException {
        if (isEmpty(fileName) || records == null || records.size() == 0) return false;
        Path filePath = Paths.get(fileName);
        // TODO: make normal file generating, not like that
        List<String> lines = new ArrayList<>();
        lines.add(DECLARE);
        lines.add("first_objid" + " " + NUMBER + ";");
        lines.add("part" + " " + NUMBER + ";");
        lines.add("territory" + " " + NUMBER + ";");
        lines.add("lvl3" + " " + NUMBER + ";");
        lines.add("record_count" + " " + NUMBER + ";");
        lines.add(TYPE + " virt_row " + IS_RECORD + " (" +
                TERR_ID + " " + MessageFormat.format(VARCHAR2, 5) + ", " +
                PART_NUM + " " + MessageFormat.format(VARCHAR2, 30) + ", " +
                FAMILY + " " + MessageFormat.format(VARCHAR2, 20) + ", " +
                TYPE1 + " " + MessageFormat.format(VARCHAR2, 40) + ", " +
                TYPE2 + " " + MessageFormat.format(VARCHAR2, 40) + ", " +
                TYPE3 + " " + MessageFormat.format(VARCHAR2, 40) + ");");
        lines.add(TYPE + " virt_array " + IS_TABLE_OF + " virt_row " + INDEX_BY + " " + MessageFormat.format(VARCHAR2, 10) + ";");
        lines.add("x_inp_params virt_array;");
        lines.add("x_inp_params_row virt_row;");
        lines.add(BEGIN);
        for (int i = 0; i < records.size(); i++) {
            lines.add(SELECT + " '" +
                    records.get(i).getMrfId() + "', '" +
                    records.get(i).getPartNum() + "', '" +
                    records.get(i).getTechnology() + "', '" +
                    records.get(i).getTypeOne() + "', '" +
                    records.get(i).getTypeTwo() + "', '" +
                    records.get(i).getTypeThree() + "'" );
            lines.add(INTO + " x_inp_params_row");
            lines.add(FROM_DUAL);
            lines.add("x_inp_params(" + i + ") := x_inp_params_row;");
        }
        lines.add("record_count := x_inp_params.count;");

        lines.add(FOR + " i " + IN + " 0..record_count-1 " + LOOP);
        lines.add(SELECT + " terr." + OBJID + " " + INTO + " territory " +
                FROM + " " + SA_TABLE + TERRITORY + " terr " +
                WHERE + " terr." + TERR_ID + " = x_inp_params(i)." + TERR_ID + ";");
        lines.add(SELECT + " pn." + OBJID + " " + INTO + " part " +
                FROM + " " + SA_TABLE + PART_NUM + " pn " +
                WHERE + " pn." + PART_NUMBER + " = x_inp_params(i)." + PART_NUM + " " + AND + " pn." + FAMILY + " <> '" + constants.get(KKFU) + "';");
        lines.add(SELECT + " type3." + OBJID);
        lines.add(INTO + " lvl3");
        lines.add(FROM + " " + SA_TABLE + HGBST_LST + " list");
        lines.add(INNER_JOIN + " " + SA_TABLE + HGBST_SHOW + " show1 " + ON + " show1." + OBJID + " = list." + HGBST_LST2HGBST_SHOW);
        lines.add(INNER_JOIN + " " + SA_SCHEMA + MTM_HGBST_ELM0_HGBST_SHOW1 + " m1 " + ON + " show1." + OBJID + " = m1." + HGBST_SHOW2HGBST_ELM);
        lines.add(INNER_JOIN + " " + SA_TABLE + HGBST_ELM + " type1 " + ON + " type1." + OBJID + " = m1." + HGBST_ELM2HGBST_SHOW + " " + AND + " type1." + STATE + "!= '" + InactiveState + "'");
        lines.add(INNER_JOIN + " " + SA_SCHEMA + MTM_HGBST_ELM0_HGBST_SHOW1 + " m2 " + ON + " type1." + OBJID + " = m2." + HGBST_ELM2HGBST_SHOW);
        lines.add(INNER_JOIN + " " + SA_TABLE + HGBST_SHOW + " show2 " + ON + " show2." + OBJID + " = m2." + HGBST_SHOW2HGBST_ELM + " " + AND + " show2." + OBJID + " != show1." + OBJID);
        lines.add(INNER_JOIN + " " + SA_SCHEMA + MTM_HGBST_ELM0_HGBST_SHOW1 + " m3 " + ON + " show2." + OBJID + " = m3." + HGBST_SHOW2HGBST_ELM);
        lines.add(INNER_JOIN + " " + SA_TABLE + HGBST_ELM + " type2 " + ON + " type2." + OBJID + " = m3." + HGBST_ELM2HGBST_SHOW + " " + AND + " type2." + OBJID + " != type1." + OBJID + " " + AND + " type2." + STATE + "!= '" + InactiveState + "'");
        lines.add(INNER_JOIN + " " + SA_SCHEMA + MTM_HGBST_ELM0_HGBST_SHOW1 + " m4 " + ON + " type2." + OBJID + " = m4." + HGBST_ELM2HGBST_SHOW);
        lines.add(INNER_JOIN + " " + SA_TABLE + HGBST_SHOW + " show3 " + ON + " show3." + OBJID + " = m4." + HGBST_SHOW2HGBST_ELM + " " + AND + " " + "show3." + OBJID + " = m4." + HGBST_SHOW2HGBST_ELM + " " +  AND + " show3." + OBJID + " != show2." + OBJID);
        lines.add(INNER_JOIN + " " + SA_SCHEMA + MTM_HGBST_ELM0_HGBST_SHOW1 + " m5 " + ON + " show3." + OBJID + " = m5." + HGBST_SHOW2HGBST_ELM);
        lines.add(INNER_JOIN + " " + SA_TABLE + HGBST_ELM + " type3 " + ON + " type3." + OBJID + " = m5." + HGBST_ELM2HGBST_SHOW + " " + AND + " type3." + OBJID + " !=  type2." + OBJID + " " + AND + " type3." + STATE  + "!= '" + InactiveState + "'");
        lines.add(WHERE + " list." + TITLE + " = '" + CaseTypeList + "' " + AND + " type1." + TITLE + " = x_inp_params(i).TYPE1 " + AND + " type2." + TITLE + " = x_inp_params(i).TYPE2 " + AND + " type3." + TITLE + " = x_inp_params(i).TYPE3;");
        lines.add(DELETE + " " + FROM + " " + SA_TABLE + X_C_WFM_MAP + " " + WHERE);
        lines.add(X_IS_ACTIVE + " = '1' " + AND);
        lines.add(X_TECH_FAMILY + " = x_inp_params(i)." + FAMILY  + " " + AND);
        lines.add(X_C_WFM_MAP2C_TYPE_LVL3 + " = lvl3 " + AND);
        lines.add(X_C_WFM_MAP2TERRITORY + " = territory " + AND);
        lines.add(X_C_WFM_MAP2PART_NUM + " = part;");
        lines.add(COMMIT);
        lines.add(END_LOOP);
        lines.add(TERMINAL_END);
        Files.write(filePath, lines, Charset.defaultCharset());
        return true;
    }
}
