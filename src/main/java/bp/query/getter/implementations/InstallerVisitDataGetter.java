package bp.query.getter.implementations;

import bp.checker.entity.implementations.WfmMap;
import bp.model.entity.InstallerVisit;
import bp.query.getter.AbstractDataGetter;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.impl.DSL;

import java.sql.Connection;

import static bp.model.Constants.FieldsName.*;
import static bp.model.Constants.SqlQueryConstants.CaseTypeList;
import static bp.model.Constants.SqlQueryConstants.InactiveState;
import static bp.model.Constants.SqlQueryConstants.KKFU;
import static bp.model.Constants.TableNames.*;
import static bp.model.Constants.TableNames.MTM_HGBST_ELM0_HGBST_SHOW1;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class InstallerVisitDataGetter extends AbstractDataGetter<InstallerVisit, WfmMap> {
    public InstallerVisitDataGetter(Connection connection) {
        super(connection);
    }

    @Override
    public WfmMap getDataFromDb(InstallerVisit value) {
        String typeThreeObjid = getCaseTypeTreeObjid(value);
        String partNumObjid = getProductObjid(value.getPartNum());
        String terrObjid = getTerritoryObjid(value.getMrfId());
        return WfmMap.builder()
                .technologyName(value.getTechnology())
                .typeThreeObjid(typeThreeObjid)
                .territoryObjid(terrObjid)
                .partNumObjid(partNumObjid)
                .build();
    }

    private String getCaseTypeTreeObjid(InstallerVisit value) {
        Table<Record> list = table(SA_TABLE + HGBST_LST).as("list");

        Table<Record> show1 = table(SA_TABLE + HGBST_SHOW).as("show1");
        Table<Record> show2 = table(SA_TABLE + HGBST_SHOW).as("show2");
        Table<Record> show3 = table(SA_TABLE + HGBST_SHOW).as("show3");

        Table<Record> type1 = table(SA_TABLE + HGBST_ELM).as("type1");
        Table<Record> type2 = table(SA_TABLE + HGBST_ELM).as("type2");
        Table<Record> type3 = table(SA_TABLE + HGBST_ELM).as("type3");

        Table<Record> m1 = table(MTM_HGBST_ELM0_HGBST_SHOW1).as("m1");
        Table<Record> m2 = table(MTM_HGBST_ELM0_HGBST_SHOW1).as("m2");
        Table<Record> m3 = table(MTM_HGBST_ELM0_HGBST_SHOW1).as("m3");
        Table<Record> m4 = table(MTM_HGBST_ELM0_HGBST_SHOW1).as("m4");
        Table<Record> m5 = table(MTM_HGBST_ELM0_HGBST_SHOW1).as("m5");

        Result<Record1<String>> sqlResult = DSL.using(connection)
                .select(DSL.field(DSL.name(DSL.quotedName("type3"), DSL.unquotedName(OBJID)), String.class))
                .from(list)
                .innerJoin(show1)
                .on(DSL.field(DSL.name(DSL.quotedName("show1"), DSL.unquotedName(OBJID)), String.class)
                        .eq(DSL.field(DSL.name(DSL.quotedName("list"), DSL.unquotedName(HGBST_LST2HGBST_SHOW)), String.class)))
                .innerJoin(m1)
                .on(DSL.field(DSL.name(DSL.quotedName("show1"), DSL.unquotedName(OBJID)), String.class)
                        .eq(DSL.field(DSL.name(DSL.quotedName("m1"), DSL.unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                .innerJoin(type1)
                .on(DSL.field(DSL.name(DSL.quotedName("type1"), DSL.unquotedName(OBJID)), String.class)
                        .eq(DSL.field(DSL.name(DSL.quotedName("m1"), DSL.unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                .and(DSL.field(DSL.name(DSL.quotedName("type1"), DSL.unquotedName(STATE)), String.class).notEqual(InactiveState))
                .innerJoin(m2)
                .on(DSL.field(DSL.name(DSL.quotedName("type1"), DSL.unquotedName(OBJID)), String.class)
                        .eq(DSL.field(DSL.name(DSL.quotedName("m2"), DSL.unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                .innerJoin(show2)
                .on(DSL.field(DSL.name(DSL.quotedName("show2"), DSL.unquotedName(OBJID)), String.class)
                        .eq(DSL.field(DSL.name(DSL.quotedName("m2"), DSL.unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                .and(DSL.field(DSL.name(DSL.quotedName("show2"), DSL.unquotedName(OBJID)), String.class)
                        .notEqual(DSL.field(DSL.name(DSL.quotedName("show1"), DSL.unquotedName(OBJID)), String.class)))
                .innerJoin(m3)
                .on(DSL.field(DSL.name(DSL.quotedName("show2"), DSL.unquotedName(OBJID)), String.class)
                        .eq(DSL.field(DSL.name(DSL.quotedName("m3"), DSL.unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                .innerJoin(type2)
                .on(DSL.field(DSL.name(DSL.quotedName("type2"), DSL.unquotedName(OBJID)), String.class)
                        .eq(DSL.field(DSL.name(DSL.quotedName("m3"), DSL.unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                .and(DSL.field(DSL.name(DSL.quotedName("type2"), DSL.unquotedName(OBJID)), String.class)
                        .notEqual(DSL.field(DSL.name(DSL.quotedName("type1"), DSL.unquotedName(OBJID)), String.class)))
                .and(DSL.field(DSL.name(DSL.quotedName("type2"), DSL.unquotedName(STATE)), String.class)
                        .notEqual(InactiveState))
                .innerJoin(m4)
                .on(DSL.field(DSL.name(DSL.quotedName("type2"), DSL.unquotedName(OBJID)), String.class)
                        .eq(DSL.field(DSL.name(DSL.quotedName("m4"), DSL.unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                .innerJoin(show3)
                .on(DSL.field(DSL.name(DSL.quotedName("show3"), DSL.unquotedName(OBJID)), String.class)
                        .eq(DSL.field(DSL.name(DSL.quotedName("m4"), DSL.unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                .and(DSL.field(DSL.name(DSL.quotedName("show3"), DSL.unquotedName(OBJID)), String.class)
                        .eq(DSL.field(DSL.name(DSL.quotedName("m4"), DSL.unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                .and(DSL.field(DSL.name(DSL.quotedName("show3"), DSL.unquotedName(OBJID)), String.class)
                        .notEqual(DSL.field(DSL.name(DSL.quotedName("show2"), DSL.unquotedName(OBJID)), String.class)))
                .innerJoin(m5)
                .on(DSL.field(DSL.name(DSL.quotedName("show3"), DSL.unquotedName(OBJID)), String.class)
                        .eq(DSL.field(DSL.name(DSL.quotedName("m5"), DSL.unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                .innerJoin(type3)
                .on(DSL.field(DSL.name(DSL.quotedName("type3"), DSL.unquotedName(OBJID)), String.class)
                        .eq(DSL.field(DSL.name(DSL.quotedName("m5"), DSL.unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                .and(DSL.field(DSL.name(DSL.quotedName("type3"), DSL.unquotedName(OBJID)), String.class)
                        .notEqual(DSL.field(DSL.name(DSL.quotedName("type2"), DSL.unquotedName(OBJID)), String.class)))
                .and(DSL.field(DSL.name(DSL.quotedName("type3"), DSL.unquotedName(STATE)), String.class).notEqual(InactiveState))
                .where(DSL.field(DSL.name(DSL.quotedName("list"), DSL.unquotedName(TITLE)), String.class)
                        .eq(CaseTypeList))
                .and(DSL.field(DSL.name(DSL.quotedName("type1"), DSL.unquotedName(TITLE)), String.class)
                        .eq(value.getTypeOne()))
                .and(DSL.field(DSL.name(DSL.quotedName("type2"), DSL.unquotedName(TITLE)), String.class)
                        .eq(value.getTypeTwo()))
                .and(DSL.field(DSL.name(DSL.quotedName("type3"), DSL.unquotedName(TITLE)), String.class)
                        .eq(value.getTypeThree())).fetch();
        return sqlResult.get(0).value1();
    }

    private String getProductObjid(String partName) {
        Result<Record1<String>> sqlResult = DSL.using(connection)
                        .select(field(OBJID, String.class))
                        .from(table(SA_TABLE + PART_NUM))
                        .where(field(PART_NUMBER).eq(partName))
                        .and(field(FAMILY).notEqual(KKFU))
                .fetch();

        return sqlResult.get(0).value1();
    }

    private String getTerritoryObjid(String mrfId) {
        Result<Record1<String>> sqlResult = DSL.using(connection)
                .select(field(OBJID, String.class))
                .from(table(SA_TABLE + TERRITORY))
                .where(field(TERR_ID).eq(mrfId))
                .fetch();

        return sqlResult.get(0).value1();
    }
}
