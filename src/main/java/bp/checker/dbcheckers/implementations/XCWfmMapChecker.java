package bp.checker.dbcheckers.implementations;

import bp.checker.dbcheckers.AbstractDbChecker;
import bp.checker.entitycheckers.entity.InstallerVisit;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.impl.DSL;


import static bp.model.constants.Constants.FieldsName.*;
import static bp.model.constants.Constants.SqlQueryConstants.*;
import static bp.model.constants.Constants.TableNames.*;
import static org.jooq.impl.DSL.table;

public class XCWfmMapChecker extends AbstractDbChecker<InstallerVisit> {

    public XCWfmMapChecker() {
        super();
        table += X_C_WFM_MAP;
    }

    @Override
    public boolean check(InstallerVisit visit) {
        Table<Record> wfm = table(table).as("wfm");
        Table<Record> part = table(SA_TABLE + PART_NUM).as("part");
        Table<Record> terr = table(SA_TABLE + TERRITORY).as("terr");
        Table<Record> tehn = table(SA_TABLE + TECHNOLOGY).as("tehn");
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

        Result<Record1<Integer>> sqlResult = DSL.using(connection)
                .selectCount()
                .from(DUAL)
                .whereExists(DSL.using(connection)
                        .select(DSL.field(DSL.name(DSL.quotedName("wfm"), DSL.unquotedName(OBJID))))
                        .from(wfm)
                        .innerJoin(tehn)
                                .on(DSL.field(DSL.name(DSL.quotedName("wfm"), DSL.unquotedName(X_TECH_FAMILY)), String.class)
                                    .eq(DSL.field(DSL.name(DSL.quotedName("tehn"), DSL.unquotedName(X_NAME_TECHN)), String.class)))
                                .and(DSL.field(DSL.name(DSL.quotedName("tehn"), DSL.unquotedName(X_NAME_TECHN)), String.class).eq(visit.getTechnology()))
                        .innerJoin(part)
                                .on(DSL.field(DSL.name(DSL.quotedName("wfm"), DSL.unquotedName(X_C_WFM_MAP2PART_NUM)), String.class)
                                    .eq(DSL.field(DSL.name(DSL.quotedName("part"), DSL.unquotedName(OBJID)), String.class)))
                                .and(DSL.field(DSL.name(DSL.quotedName("part"), DSL.unquotedName(PART_NUMBER)), String.class).eq(visit.getPartNum()))
                                .and(DSL.field(DSL.name(DSL.quotedName("part"), DSL.unquotedName(FAMILY)), String.class).notEqual(constants.get(KKFU)))
                        .innerJoin(terr)
                                .on(DSL.field(DSL.name(DSL.quotedName("wfm"), DSL.unquotedName(X_C_WFM_MAP2TERRITORY)), String.class)
                                    .eq(DSL.field(DSL.name(DSL.quotedName("terr"), DSL.unquotedName(OBJID)), String.class)))
                                .and(DSL.field(DSL.name(DSL.quotedName("terr"), DSL.unquotedName(TERR_ID)), String.class).eq(visit.getMrfId()))
                        .innerJoin(type3)
                                .on(DSL.field(DSL.name(DSL.quotedName("wfm"), DSL.unquotedName(X_C_WFM_MAP2C_TYPE_LVL3)), String.class)
                                    .eq(DSL.field(DSL.name(DSL.quotedName("type3"), DSL.unquotedName(OBJID)), String.class)))
                                .and(DSL.field(DSL.name(DSL.quotedName("type3"), DSL.unquotedName(STATE)), String.class).notEqual(InactiveState))
                        .innerJoin(m5)
                                .on(DSL.field(DSL.name(DSL.quotedName("type3"), DSL.unquotedName(OBJID)), String.class)
                                    .eq(DSL.field(DSL.name(DSL.quotedName("m5"), DSL.unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                        .innerJoin(show3)
                                .on(DSL.field(DSL.name(DSL.quotedName("show3"), DSL.unquotedName(OBJID)), String.class)
                                    .eq(DSL.field(DSL.name(DSL.quotedName("m5"), DSL.unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                        .innerJoin(m4)
                                .on(DSL.field(DSL.name(DSL.quotedName("show3"), DSL.unquotedName(OBJID)), String.class)
                                    .eq(DSL.field(DSL.name(DSL.quotedName("m4"), DSL.unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                        .innerJoin(type2)
                                .on(DSL.field(DSL.name(DSL.quotedName("type2"), DSL.unquotedName(OBJID)), String.class)
                                    .eq(DSL.field(DSL.name(DSL.quotedName("m4"), DSL.unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                                .and(DSL.field(DSL.name(DSL.quotedName("type3"), DSL.unquotedName(OBJID)), String.class)
                                     .notEqual(DSL.field(DSL.name(DSL.quotedName("type2"), DSL.unquotedName(OBJID)), String.class)))
                                .and(DSL.field(DSL.name(DSL.quotedName("type2"), DSL.unquotedName(STATE)), String.class).notEqual(InactiveState))
                        .innerJoin(m3)
                                .on(DSL.field(DSL.name(DSL.quotedName("type2"), DSL.unquotedName(OBJID)), String.class)
                                    .eq(DSL.field(DSL.name(DSL.quotedName("m3"), DSL.unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                        .innerJoin(show2)
                                .on(DSL.field(DSL.name(DSL.quotedName("show2"), DSL.unquotedName(OBJID)), String.class)
                                        .eq(DSL.field(DSL.name(DSL.quotedName("m3"), DSL.unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                        .innerJoin(m2)
                                .on(DSL.field(DSL.name(DSL.quotedName("show2"), DSL.unquotedName(OBJID)), String.class)
                                        .eq(DSL.field(DSL.name(DSL.quotedName("m2"), DSL.unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                                .and(DSL.field(DSL.name(DSL.quotedName("show3"), DSL.unquotedName(OBJID)), String.class)
                                        .notEqual(DSL.field(DSL.name(DSL.quotedName("show2"), DSL.unquotedName(OBJID)), String.class)))
                        .innerJoin(type1)
                                .on(DSL.field(DSL.name(DSL.quotedName("type1"), DSL.unquotedName(OBJID)), String.class)
                                        .eq(DSL.field(DSL.name(DSL.quotedName("m2"), DSL.unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                                .and(DSL.field(DSL.name(DSL.quotedName("type1"), DSL.unquotedName(STATE)), String.class).notEqual(InactiveState))
                        .innerJoin(m1)
                                .on(DSL.field(DSL.name(DSL.quotedName("type1"), DSL.unquotedName(OBJID)), String.class)
                                        .eq(DSL.field(DSL.name(DSL.quotedName("m1"), DSL.unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                        .innerJoin(show1)
                                .on(DSL.field(DSL.name(DSL.quotedName("show1"), DSL.unquotedName(OBJID)), String.class)
                                        .eq(DSL.field(DSL.name(DSL.quotedName("m1"), DSL.unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                                .and(DSL.field(DSL.name(DSL.quotedName("show2"), DSL.unquotedName(OBJID)), String.class)
                                        .notEqual(DSL.field(DSL.name(DSL.quotedName("show1"), DSL.unquotedName(OBJID)), String.class)))
                        .innerJoin(list)
                                .on(DSL.field(DSL.name(DSL.quotedName("show1"), DSL.unquotedName(OBJID)), String.class)
                                        .eq(DSL.field(DSL.name(DSL.quotedName("list"), DSL.unquotedName(HGBST_LST2HGBST_SHOW)), String.class)))
                                .and(DSL.field(DSL.name(DSL.quotedName("list"), DSL.unquotedName(TITLE)), String.class).eq(CaseTypeList))
                        .where(DSL.field(DSL.name(DSL.quotedName("type1"), DSL.unquotedName(TITLE)), String.class)
                                .eq(visit.getTypeOne()))
                            .and(DSL.field(DSL.name(DSL.quotedName("type2"), DSL.unquotedName(TITLE)), String.class)
                                .eq(visit.getTypeTwo()))
                            .and(DSL.field(DSL.name(DSL.quotedName("type3"), DSL.unquotedName(TITLE)), String.class)
                                .eq(visit.getTypeThree()))
                        .and(DSL.field(DSL.name(DSL.quotedName("wfm"), DSL.unquotedName(X_IS_ACTIVE)), String.class)
                                .eq(ActiveStatus)))
                .fetch();
        return sqlResult != null && sqlResult.size() == 1 && 1 == sqlResult.get(0).value1();
    }
}