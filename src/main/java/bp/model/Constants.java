package bp.model;

public interface Constants {
    interface TableNames {
        String SA_TABLE = "sa.table_";
        String TECHNOLOGY = "x_type_techn";
        String HGBST_ELM = "hgbst_elm";
        String HGBST_LST = "hgbst_lst";
        String HGBST_SHOW = "hgbst_show";
        String MTM_HGBST_ELM0_HGBST_SHOW1 = "mtm_hgbst_elm0_hgbst_show1";
        String PART_NUM = "part_num";
        String TERRITORY = "territory";
        String X_C_WFM_MAP = "x_c_wfm_map";
        String ADP_TBL_OID = "adp_tbl_oid";
        String ADP_OBJECT = "adp_object";
        String DUAL = "dual";
    }

    interface FieldsName {
        String OBJID = "objid";
        String X_NAME_TECHN = "x_name_techn";
        String TERR_ID = "terr_id";
        String PART_NUMBER = "part_number";
        String FAMILY = "family";
        String HGBST_ELM2HGBST_SHOW = "hgbst_elm2hgbst_show";
        String HGBST_SHOW2HGBST_ELM = "hgbst_show2hgbst_elm";
        String HGBST_LST2HGBST_SHOW = "hgbst_lst2hgbst_show";
        String STATE = "state";
        String TITLE = "title";
        String DEV = "dev";
        String X_TECH_FAMILY = "x_tech_family";
        String X_C_WFM_MAP2C_TYPE_LVL3 = "x_c_wfm2c_type_lvl3";
        String X_C_WFM_MAP2TERRITORY = "x_c_wfm_map2_territory";
        String X_C_WFM_MAP2PART_NUM = "x_c_wfm_map2part_num";
        String X_IS_ACTIVE = "x_is_active";
    }


    interface SqlQueryConstants {
        String KKFU = "ККФУ";
        String InactiveState = "Inactive";
        String CaseTypeList = "CASE_TYPE";
        String ActiveStatus = "1";
    }

    interface FileChooserConstants {
        String XML_FILES = "XML Files";
        String XLSX_FILES = "XLSX Files";
        String CHOOSER_TITLE = "Выберете файл";
        String XML_EXTENSION = "*.xml";
        String XLSX_EXTENSION = "*.xlsx";
        String UTF_8_CHARSET = "utf-8";
    }

    interface DataBaseConstants {
        String DRIVER_CLASS_NAME = "oracle.jdbc.driver.OracleDriver";
    }

    interface ResourceFilesNames {
        String DB_CONNECTION = "db_connection.yml";
        String SHEETS_NAME = "sheet_names.yml";
        String ACTIONS = "actions.yml";
        String MESSAGES = "messages.yml";
    }
}
