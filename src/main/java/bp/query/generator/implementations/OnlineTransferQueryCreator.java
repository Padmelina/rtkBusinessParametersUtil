package bp.query.generator.implementations;

import bp.checker.entitycheckers.entity.OnlineTransfer;
import bp.query.generator.QueryAbstractCreator;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static bp.context.Context.getContext;
import static bp.model.constants.Constants.CommonSqlQueries.FROM_DUAL;
import static bp.model.constants.Constants.CommonSqlQueries.SELECT_DUAL;
import static bp.model.constants.Constants.FieldsName.*;
import static bp.model.constants.Constants.SqlKeyWords.*;
import static bp.model.constants.Constants.SqlKeyWords.END_LOOP;
import static bp.model.constants.Constants.SqlKeyWords.TERMINAL_END;
import static bp.model.constants.Constants.SqlQueryConstants.*;
import static bp.model.constants.Constants.TableNames.*;
import static org.jooq.tools.StringUtils.isEmpty;

public class OnlineTransferQueryCreator extends QueryAbstractCreator<OnlineTransfer> {

    private class StringList {
        private ArrayList<String> strings;
        public StringList() {
            strings = new ArrayList<>();
        }

        public  StringList append(String s){
            strings.add(strings.size() - 1, strings.get(strings.size() - 1).concat(s));
            return this;
        }

        public  StringList appendLn(String s){
            strings.add(s);
            return this;
        }
    }


    @Override
    public boolean generateAdd(String fileName, List<OnlineTransfer> records){
        if (isEmpty(fileName) || records == null || records.size() == 0) return false;
        Path filePath = Paths.get(fileName);
        List<String> lines = new ArrayList<>();
        lines.add(DECLARE);
        lines.add("first_objid" + " " + NUMBER + ";");
        lines.add("part" + " " + NUMBER + ";");
        lines.add("territory" + " " + NUMBER + ";");
        lines.add("lvl3" + " " + NUMBER + ";");
        lines.add("record_count" + " " + NUMBER + ";");
        lines.add("technology" + " " + NUMBER + ";");
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
                WHERE + " o." + TYPE_NAME + " = '" + X_C_LST_MAP + "') " + FOR_UPDATE + ";");
        lines.add(UPDATE + " " + SA_SCHEMA + ADP_TBL_OID + " " +
                SET + " " + OBJ_NUM + " = first_objid + record_count " +
                WHERE + " " + TYPE_ID + " = (" +
                SELECT + " " + "o." + TYPE_ID + " " + FROM + " " +
                SA_SCHEMA + ADP_OBJECT + " o " +
                WHERE + " o." + TYPE_NAME + " = '" + X_C_LST_MAP + "');");
        lines.add(COMMIT);
        lines.add(FOR + " i " + IN + " 0..record_count-1 " + LOOP);
        lines.add(SELECT + " techn." + OBJID + " " + INTO + " technology " +
                FROM + " " + SA_TABLE + TECHNOLOGY + " techn " +
                WHERE + " techn." + X_NAME_TECHN + " = x_inp_params(i)." + FAMILY + ";");
        lines.add(SELECT + " terr." + OBJID + " " + INTO + " territory " +
                FROM + " " + SA_TABLE + TERRITORY + " terr " +
                WHERE + " terr." + TERR_ID + " = x_inp_params(i)." + TERR_ID + ";");
        lines.add(IF + " x_inp_params(i).part_num = 'ANY' " + THEN);
        lines.add("part := -2;");
        lines.add(ELSE);
        lines.add(SELECT + " pn." + OBJID + " " + INTO + " part " +
                FROM + " " + SA_TABLE + PART_NUM + " pn " +
                WHERE + " pn." + PART_NUMBER + " = x_inp_params(i)." + PART_NUM + " " + AND + " pn." + FAMILY + " <> '" + getContext().getResources().getSqlConstants().get(KKFU) + "';");
        lines.add(END_IF);
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
        lines.add(INTO + " " + SA_TABLE + X_C_LST_MAP);
        lines.add("(" + OBJID + ", " + DEV + ", " + X_IS_ACTIVE + ", " + X_C_LTS_MAP2X_TYPE_TECHN + ", " + X_C_LTS_MAP2C_TYPE_LVL3 + ", " + X_C_LTS_MAP2TERRITORY + ", " + X_C_LTS_MAP2X_PART_NUM + ")");
        lines.add(VALUES);
        lines.add("(first_objid + i, 1, 1, technology, lvl3, territory, part)");
        lines.add(SELECT_DUAL);
        lines.add(COMMIT);
        lines.add(END_LOOP);
        lines.add(TERMINAL_END);
        try {
            Files.write(filePath, lines, Charset.defaultCharset());
        } catch (IOException e) {
            getContext().getLogger().error(e.getMessage(), e);
        }
        return true;
    }

    @Override
    public boolean generateDelete(String fileName, List<OnlineTransfer> records) {
        if (isEmpty(fileName) || records == null || records.size() == 0) return false;
        Path filePath = Paths.get(fileName);
        List<String> lines = new ArrayList<>();
        lines.add(DECLARE);
        lines.add("first_objid" + " " + NUMBER + ";");
        lines.add("technology" + " " + NUMBER + ";");
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
        lines.add(SELECT + " techn." + OBJID + " " + INTO + " technology " +
                FROM + " " + SA_TABLE + TECHNOLOGY + " techn " +
                WHERE + " techn." + X_NAME_TECHN + " = x_inp_params(i)." + FAMILY + ";");
        lines.add(SELECT + " terr." + OBJID + " " + INTO + " territory " +
                FROM + " " + SA_TABLE + TERRITORY + " terr " +
                WHERE + " terr." + TERR_ID + " = x_inp_params(i)." + TERR_ID + ";");
        lines.add(IF + " x_inp_params(i).part_num = 'ANY' " + THEN);
        lines.add("part := -2;");
        lines.add(ELSE);
        lines.add(SELECT + " pn." + OBJID + " " + INTO + " part " +
                FROM + " " + SA_TABLE + PART_NUM + " pn " +
                WHERE + " pn." + PART_NUMBER + " = x_inp_params(i)." + PART_NUM + " " + AND + " pn." + FAMILY + " <> '" + getContext().getResources().getSqlConstants().get(KKFU) + "';");
        lines.add(END_IF);
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
        lines.add(DELETE + " " + FROM + " " + SA_TABLE + X_C_LST_MAP + " " + WHERE);
        lines.add(X_IS_ACTIVE + " = '1' " + AND);
        lines.add(X_C_LTS_MAP2X_TYPE_TECHN + " = technology " +  AND);
        lines.add(X_C_LTS_MAP2C_TYPE_LVL3 + " = lvl3 " + AND);
        lines.add(X_C_LTS_MAP2TERRITORY + " = territory " + AND);
        lines.add(X_C_LTS_MAP2X_PART_NUM + " = part;");
        lines.add(COMMIT);
        lines.add(END_LOOP);
        lines.add(TERMINAL_END);
        try {
            Files.write(filePath, lines, Charset.defaultCharset());
        } catch (IOException e) {
            getContext().getLogger().error(e.getMessage(), e);
        }
        return true;
    }

    @Override
    public boolean generateCheckAdd(String fileName, List<OnlineTransfer> records) {
        if (isEmpty(fileName) || records == null || records.size() == 0) return false;
        Path filePath = Paths.get(fileName);
        List<String> lines = new ArrayList<>();
        lines.add(DECLARE);
        lines.add("first_objid" + " " + NUMBER + ";");
        lines.add("part" + " " + NUMBER + ";");
        lines.add("territory" + " " + NUMBER + ";");
        lines.add("lvl3" + " " + NUMBER + ";");
        lines.add("record_count" + " " + NUMBER + ";");
        lines.add("technology" + " " + NUMBER + ";");
        lines.add("current_count" + " " + NUMBER + ";");
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
        lines.add("DBMS_OUTPUT.ENABLE(1000000);");
        lines.add(FOR + " i " + IN + " 0..record_count-1 " + LOOP);
        lines.add("current_count := 0;");
        lines.add(SELECT + " techn." + OBJID + " " + INTO + " technology " +
                FROM + " " + SA_TABLE + TECHNOLOGY + " techn " +
                WHERE + " techn." + X_NAME_TECHN + " = x_inp_params(i)." + FAMILY + ";");
        lines.add(SELECT + " terr." + OBJID + " " + INTO + " territory " +
                FROM + " " + SA_TABLE + TERRITORY + " terr " +
                WHERE + " terr." + TERR_ID + " = x_inp_params(i)." + TERR_ID + ";");
        lines.add(IF + " x_inp_params(i).part_num = 'ANY' " + THEN);
        lines.add("part := -2;");
        lines.add(ELSE);
        lines.add(SELECT + " pn." + OBJID + " " + INTO + " part " +
                FROM + " " + SA_TABLE + PART_NUM + " pn " +
                WHERE + " pn." + PART_NUMBER + " = x_inp_params(i)." + PART_NUM + " " + AND + " pn." + FAMILY + " <> '" + getContext().getResources().getSqlConstants().get(KKFU) + "';");
        lines.add(END_IF);
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
        lines.add(SELECT + " " + COUNT_ALL + " " + INTO + " current_count " + FROM + " " + SA_TABLE + X_C_LST_MAP);
        lines.add(WHERE + " " + X_C_LTS_MAP2X_TYPE_TECHN + " = technology ");
        lines.add(AND + " " + X_C_LTS_MAP2C_TYPE_LVL3 + " = lvl3 ");
        lines.add(AND + " " + X_C_LTS_MAP2TERRITORY + " = territory ");
        lines.add(AND + " " + X_C_LTS_MAP2X_PART_NUM + " = part ");
        lines.add(AND + " " + X_IS_ACTIVE + " = " + ActiveStatusQuery + ";");
        lines.add(IF + " " + "current_count != 1" + " " + THEN);
        lines.add(MessageFormat.format(DBMS_PUT_LINE, "'Record: {' || x_inp_params(i).family || ', ' || x_inp_params(i).terr_id || ', ' || x_inp_params(i).part_num || ', ' || x_inp_params(i).TYPE1 || ', ' || x_inp_params(i).TYPE2 || ', ' || x_inp_params(i).TYPE3 || '} not found!'"));
        lines.add(END_IF);
        lines.add(END_LOOP);
        lines.add(MessageFormat.format(DBMS_PUT_LINE, Completed_message));
        lines.add(TERMINAL_END);
        try {
            Files.write(filePath, lines, Charset.defaultCharset());
        } catch (IOException e) {
            getContext().getLogger().error(e.getMessage(), e);
        }
        return true;
    }

    @Override
    public boolean generateCheckDelete(String fileName, List<OnlineTransfer> records) {
        if (isEmpty(fileName) || records == null || records.size() == 0) return false;
        Path filePath = Paths.get(fileName);
        List<String> lines = new ArrayList<>();
        lines.add(DECLARE);
        lines.add("first_objid" + " " + NUMBER + ";");
        lines.add("part" + " " + NUMBER + ";");
        lines.add("territory" + " " + NUMBER + ";");
        lines.add("lvl3" + " " + NUMBER + ";");
        lines.add("record_count" + " " + NUMBER + ";");
        lines.add("technology" + " " + NUMBER + ";");
        lines.add("current_count" + " " + NUMBER + ";");
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
        lines.add("DBMS_OUTPUT.ENABLE(1000000);");
        lines.add(FOR + " i " + IN + " 0..record_count-1 " + LOOP);
        lines.add("current_count := 0;");
        lines.add(SELECT + " techn." + OBJID + " " + INTO + " technology " +
                FROM + " " + SA_TABLE + TECHNOLOGY + " techn " +
                WHERE + " techn." + X_NAME_TECHN + " = x_inp_params(i)." + FAMILY + ";");
        lines.add(SELECT + " terr." + OBJID + " " + INTO + " territory " +
                FROM + " " + SA_TABLE + TERRITORY + " terr " +
                WHERE + " terr." + TERR_ID + " = x_inp_params(i)." + TERR_ID + ";");
        lines.add(IF + " x_inp_params(i).part_num = 'ANY' " + THEN);
        lines.add("part := -2;");
        lines.add(ELSE);
        lines.add(SELECT + " pn." + OBJID + " " + INTO + " part " +
                FROM + " " + SA_TABLE + PART_NUM + " pn " +
                WHERE + " pn." + PART_NUMBER + " = x_inp_params(i)." + PART_NUM + " " + AND + " pn." + FAMILY + " <> '" + getContext().getResources().getSqlConstants().get(KKFU) + "';");
        lines.add(END_IF);
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
        lines.add(SELECT + " " + COUNT_ALL + " " + INTO + " current_count " + FROM + " " + SA_TABLE + X_C_LST_MAP);
        lines.add(WHERE + " " + X_C_LTS_MAP2X_TYPE_TECHN + " = technology ");
        lines.add(AND + " " + X_C_LTS_MAP2C_TYPE_LVL3 + " = lvl3 ");
        lines.add(AND + " " + X_C_LTS_MAP2TERRITORY + " = territory ");
        lines.add(AND + " " + X_C_LTS_MAP2X_PART_NUM + " = part ");
        lines.add(AND + " " + X_IS_ACTIVE + " = " + ActiveStatusQuery + ";");
        lines.add(IF + " " + "current_count != 0" + " " + THEN);
        lines.add(MessageFormat.format(DBMS_PUT_LINE, "'Record: {' || x_inp_params(i).family || ', ' || x_inp_params(i).terr_id || ', ' || x_inp_params(i).part_num || ', ' || x_inp_params(i).TYPE1 || ', ' || x_inp_params(i).TYPE2 || ', ' || x_inp_params(i).TYPE3 || '} not deleted!'"));
        lines.add(END_IF);
        lines.add(END_LOOP);
        lines.add(MessageFormat.format(DBMS_PUT_LINE, Completed_message));
        lines.add(TERMINAL_END);
        try {
            Files.write(filePath, lines, Charset.defaultCharset());
        } catch (IOException e) {
            getContext().getLogger().error(e.getMessage(), e);
        }
        return true;
    }
}
