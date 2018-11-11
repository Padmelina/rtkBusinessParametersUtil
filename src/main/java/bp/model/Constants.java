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
    }

    interface SqlQueryConstants {
        String KKFU = "ККФУ";
        String InactiveState = "Inactive";
        String CaseTypeList = "CASE_TYPE";
    }

    interface FileChooserConstants {
        String XML_FILES = "XML Files";
        String XLSX_FILES = "XLSX Files";
        String CHOOSER_TITLE = "Выберете файл";
        String XML_EXTENSION = "*.xml";
        String XLSX_EXTENSION = "*.xlsx";
    }

    interface DataBaseConstants {
        String DRIVER_CLASS_NAME = "oracle.jdbc.driver.OracleDriver";
    }
}
