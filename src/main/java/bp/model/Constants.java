package bp.model;

public interface Constants {
    interface TableNames {
        String SA_TABLE = "sa.table_";
        String SA_SCHEMA = "sa.";
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
        String OBJ_NUM = "obj_num";
        String TYPE_ID = "type_id";
        String TYPE_NAME = "type_name";
        String X_TECH_FAMILY = "x_tech_family";
        String X_C_WFM_MAP2C_TYPE_LVL3 = "x_c_wfm_map2c_type_lvl3";
        String X_C_WFM_MAP2TERRITORY = "x_c_wfm_map2territory";
        String X_C_WFM_MAP2PART_NUM = "x_c_wfm_map2part_num";
        String X_IS_ACTIVE = "x_is_active";
        String TYPE1 = "TYPE1";
        String TYPE2 = "TYPE2";
        String TYPE3 = "TYPE3";
    }


    interface SqlQueryConstants {
        String KKFU = "KKFU";
        String InactiveState = "Inactive";
        String CaseTypeList = "CASE_TYPE";
        String ActiveStatus = "1";
    }

    interface FileChooserConstants {
        String XML_FILES = "XML Files";
        String XLSX_FILES = "XLSX Files";
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
        String MESSAGES = "form_messages.yml";
        String SQL_CONSTANTS = "sql_constants.yml";
        String QUERIES_NAMES = "query_names.yml";
        String ERROR_MAPPING = "error_mapping.yml";
    }

    interface SqlKeyWords {
        String DECLARE = "DECLARE";
        String BEGIN = "BEGIN";
        String TERMINAL_END = "END;";
        String NUMBER = "NUMBER";
        String COMMIT = "COMMIT;";
        String INSERT_ALL = "INSERT ALL";
        String TYPE = "TYPE";
        String VARCHAR2 = "VARCHAR2({0, number})";
        String IS_RECORD = "IS RECORD";
        String IS_TABLE_OF = "IS TABLE OF";
        String INDEX_BY = "INDEX BY";
        String SELECT = "SELECT";
        String INTO = "INTO";
        String FROM = "FROM";
        String WHERE = "WHERE";
        String UPDATE = "UPDATE";
        String SET = "SET";
        String FOR_UPDATE = "FOR UPDATE";
        String FOR = "FOR";
        String IN = "IN";
        String LOOP = "LOOP";
        String AND = "AND";
        String INNER_JOIN = "INNER JOIN";
        String ON = "ON";
        String VALUES = "VALUES";
        String END_LOOP = "END LOOP;";
        String DELETE = "DELETE";
    }

    interface CommonSqlQueries {
        String SELECT_DUAL = "SELECT * FROM DUAL;";
        String FROM_DUAL = "FROM DUAL;";
    }

}
