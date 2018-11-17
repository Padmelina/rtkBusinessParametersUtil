package bp.checker.dbcheckers.implementations;

import bp.checker.dbcheckers.AbstractDbChecker;
import bp.model.entity.InstallerVisit;

import java.sql.Connection;
/**
 * TODO:
 SELECT count(*) FROM DUAL WHERE EXISTS (SELECT wfm.OBJID FROM SA.TABLE_X_C_WFM_MAP wfm
 INNER JOIN TABLE_X_TYPE_TECHN tech ON wfm.X_TECH_FAMILY = tech.X_NAME_TECHN AND tech.X_NAME_TECHN = 'FTTx'
 INNER JOIN TABLE_PART_NUM pn ON wfm.X_C_WFM_MAP2PART_NUM = pn.OBJID AND pn.PART_NUMBER = 'Интернет по Ethernet' AND pn.FAMILY <> 'ККФУ'
 INNER JOIN TABLE_TERRITORY ter ON wfm.X_C_WFM_MAP2TERRITORY = ter.OBJID AND ter.TERR_ID = 'SIB'
 INNER JOIN TABLE_HGBST_ELM type3 ON wfm.X_C_WFM_MAP2C_TYPE_LVL3 = type3.OBJID AND type3.state != 'Inactive'
 INNER JOIN MTM_HGBST_ELM0_HGBST_SHOW1 m5 ON type3.OBJID = m5.HGBST_ELM2HGBST_SHOW
 INNER JOIN TABLE_HGBST_SHOW show3 ON show3.OBJID = m5.HGBST_SHOW2HGBST_ELM
 INNER JOIN MTM_HGBST_ELM0_HGBST_SHOW1 m4 ON show3.objid = m4.hgbst_show2hgbst_elm AND show3.objid = m4.hgbst_show2hgbst_elm
 INNER JOIN TABLE_HGBST_ELM type2 ON type2.objid = m4.hgbst_elm2hgbst_show AND type3.objid != type2.objid AND type2.state != 'Inactive'
 INNER JOIN MTM_HGBST_ELM0_HGBST_SHOW1 m3 ON type2.objid = m3.hgbst_elm2hgbst_show
 INNER JOIN TABLE_HGBST_SHOW show2 ON show2.objid = m3.hgbst_show2hgbst_elm
 INNER JOIN MTM_HGBST_ELM0_HGBST_SHOW1 m2 ON show2.objid = m2.hgbst_show2hgbst_elm AND show3.objid != show2.objid
 INNER JOIN table_hgbst_elm type1 ON type1.objid = m2.hgbst_elm2hgbst_show AND type1.state != 'Inactive'
 INNER JOIN mtm_hgbst_elm0_hgbst_show1 m1 ON type1.objid = m1.hgbst_elm2hgbst_show
 INNER JOIN table_hgbst_show show1 ON show1.objid = m1.hgbst_show2hgbst_elm AND show2.objid != show1.objid
 INNER JOIN table_hgbst_lst list ON show1.objid = list.hgbst_lst2hgbst_show AND list.title = 'CASE_TYPE'
 WHERE type1.title = 'Техподдержка' AND type2.title = 'Сервис.обслуж-е клиентского оборудования' AND type3.title = 'Подключение клиентского оборудования'); */

import static bp.model.Constants.TableNames.X_C_WFM_MAP;

public class XCWfmMapChecker extends AbstractDbChecker<InstallerVisit> {

    public XCWfmMapChecker(Connection connection) {
        super(connection);
        table += X_C_WFM_MAP;
    }

    @Override
    public boolean check(InstallerVisit visit) {
        return false;
    }
}