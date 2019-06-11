package bp.checker.dbcheckers.implementations;

import bp.checker.dbcheckers.AbstractDbChecker;
import bp.checker.entitycheckers.entity.OnlineTransfer;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.impl.DSL;

import static bp.context.Context.getContext;
import static bp.model.constants.Constants.FieldsName.*;
import static bp.model.constants.Constants.FieldsName.TITLE;
import static bp.model.constants.Constants.SqlQueryConstants.*;
import static bp.model.constants.Constants.TableNames.*;
import static bp.model.constants.Constants.TableNames.DUAL;
import static bp.model.constants.Constants.TableNames.MTM_HGBST_ELM0_HGBST_SHOW1;
import static org.jooq.impl.DSL.*;

public class XCLstMapChecker extends AbstractDbChecker<OnlineTransfer> {
    public XCLstMapChecker() {
        super();
        table += X_C_LST_MAP;
    }

    @Override
    public boolean check(OnlineTransfer transfer) {
        Table<Record> lst = table(table).as("lst");
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
        Result<Record1<Integer>> sqlResult;

        if (getContext().getResources().getSqlConstants().get(ANY).equals(transfer.getPartNum())) {
            sqlResult = using(connection)
                    .selectCount()
                    .from(DUAL)
                    .whereExists(using(connection)
                            .select(field(name(quotedName("lst"), unquotedName(OBJID))))
                            .from(lst)
                            .innerJoin(tehn)
                            .on(field(name(quotedName("lst"), unquotedName(X_C_LTS_MAP2X_TYPE_TECHN)), String.class)
                                    .eq(field(name(quotedName("tehn"), unquotedName(OBJID)), String.class)))
                            .and(field(name(quotedName("tehn"), unquotedName(X_NAME_TECHN)), String.class).eq(transfer.getTechnology()))
                            .innerJoin(terr)
                            .on(field(name(quotedName("lst"), unquotedName(X_C_LTS_MAP2TERRITORY)), String.class)
                                    .eq(field(name(quotedName("terr"), unquotedName(OBJID)), String.class)))
                            .and(field(name(quotedName("terr"), unquotedName(TERR_ID)), String.class).eq(transfer.getMrfId()))
                            .innerJoin(type3)
                            .on(field(name(quotedName("lst"), unquotedName(X_C_LTS_MAP2C_TYPE_LVL3)), String.class)
                                    .eq(field(name(quotedName("type3"), unquotedName(OBJID)), String.class)))
                            .and(field(name(quotedName("type3"), unquotedName(STATE)), String.class).notEqual(InactiveState))
                            .innerJoin(m5)
                            .on(field(name(quotedName("type3"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m5"), unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                            .innerJoin(show3)
                            .on(field(name(quotedName("show3"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m5"), unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                            .innerJoin(m4)
                            .on(field(name(quotedName("show3"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m4"), unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                            .innerJoin(type2)
                            .on(field(name(quotedName("type2"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m4"), unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                            .and(field(name(quotedName("type3"), unquotedName(OBJID)), String.class)
                                    .notEqual(field(name(quotedName("type2"), unquotedName(OBJID)), String.class)))
                            .and(field(name(quotedName("type2"), unquotedName(STATE)), String.class).notEqual(InactiveState))
                            .innerJoin(m3)
                            .on(field(name(quotedName("type2"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m3"), unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                            .innerJoin(show2)
                            .on(field(name(quotedName("show2"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m3"), unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                            .innerJoin(m2)
                            .on(field(name(quotedName("show2"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m2"), unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                            .and(field(name(quotedName("show3"), unquotedName(OBJID)), String.class)
                                    .notEqual(field(name(quotedName("show2"), unquotedName(OBJID)), String.class)))
                            .innerJoin(type1)
                            .on(field(name(quotedName("type1"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m2"), unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                            .and(field(name(quotedName("type1"), unquotedName(STATE)), String.class).notEqual(InactiveState))
                            .innerJoin(m1)
                            .on(field(name(quotedName("type1"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m1"), unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                            .innerJoin(show1)
                            .on(field(name(quotedName("show1"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m1"), unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                            .and(field(name(quotedName("show2"), unquotedName(OBJID)), String.class)
                                    .notEqual(field(name(quotedName("show1"), unquotedName(OBJID)), String.class)))
                            .innerJoin(list)
                            .on(field(name(quotedName("show1"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("list"), unquotedName(HGBST_LST2HGBST_SHOW)), String.class)))
                            .and(field(name(quotedName("list"), unquotedName(TITLE)), String.class).eq(CaseTypeList))
                            .where(field(name(quotedName("type1"), unquotedName(TITLE)), String.class)
                                    .eq(transfer.getTypeOne()))
                            .and(field(name(quotedName("type2"), unquotedName(TITLE)), String.class)
                                    .eq(transfer.getTypeTwo()))
                            .and(field(name(quotedName("type3"), unquotedName(TITLE)), String.class)
                                    .eq(transfer.getTypeThree()))
                            .and(field(name(quotedName("lst"), unquotedName(X_IS_ACTIVE)), String.class)
                                    .eq(ActiveStatus))
                            .and(field(name(quotedName("lst"), unquotedName(X_C_LTS_MAP2X_PART_NUM)), String.class)
                                    .eq(UndefinedObjid)))
                    .fetch();
        } else {
            sqlResult = using(connection)
                    .selectCount()
                    .from(DUAL)
                    .whereExists(using(connection)
                            .select(field(name(quotedName("lst"), unquotedName(OBJID))))
                            .from(lst)
                            .innerJoin(tehn)
                            .on(field(name(quotedName("lst"), unquotedName(X_C_LTS_MAP2X_TYPE_TECHN)), String.class)
                                    .eq(field(name(quotedName("tehn"), unquotedName(OBJID)), String.class)))
                            .and(field(name(quotedName("tehn"), unquotedName(X_NAME_TECHN)), String.class).eq(transfer.getTechnology()))
                            .innerJoin(part)
                            .on(field(name(quotedName("lst"), unquotedName(X_C_LTS_MAP2X_PART_NUM)), String.class)
                                    .eq(field(name(quotedName("part"), unquotedName(OBJID)), String.class)))
                            .and(field(name(quotedName("part"), unquotedName(PART_NUMBER)), String.class).eq(transfer.getPartNum()))
                            .and(field(name(quotedName("part"), unquotedName(FAMILY)), String.class).notEqual(constants.get(KKFU)))
                            .innerJoin(terr)
                            .on(field(name(quotedName("lst"), unquotedName(X_C_LTS_MAP2TERRITORY)), String.class)
                                    .eq(field(name(quotedName("terr"), unquotedName(OBJID)), String.class)))
                            .and(field(name(quotedName("terr"), unquotedName(TERR_ID)), String.class).eq(transfer.getMrfId()))
                            .innerJoin(type3)
                            .on(field(name(quotedName("lst"), unquotedName(X_C_LTS_MAP2C_TYPE_LVL3)), String.class)
                                    .eq(field(name(quotedName("type3"), unquotedName(OBJID)), String.class)))
                            .and(field(name(quotedName("type3"), unquotedName(STATE)), String.class).notEqual(InactiveState))
                            .innerJoin(m5)
                            .on(field(name(quotedName("type3"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m5"), unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                            .innerJoin(show3)
                            .on(field(name(quotedName("show3"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m5"), unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                            .innerJoin(m4)
                            .on(field(name(quotedName("show3"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m4"), unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                            .innerJoin(type2)
                            .on(field(name(quotedName("type2"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m4"), unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                            .and(field(name(quotedName("type3"), unquotedName(OBJID)), String.class)
                                    .notEqual(field(name(quotedName("type2"), unquotedName(OBJID)), String.class)))
                            .and(field(name(quotedName("type2"), unquotedName(STATE)), String.class).notEqual(InactiveState))
                            .innerJoin(m3)
                            .on(field(name(quotedName("type2"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m3"), unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                            .innerJoin(show2)
                            .on(field(name(quotedName("show2"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m3"), unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                            .innerJoin(m2)
                            .on(field(name(quotedName("show2"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m2"), unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                            .and(field(name(quotedName("show3"), unquotedName(OBJID)), String.class)
                                    .notEqual(field(name(quotedName("show2"), unquotedName(OBJID)), String.class)))
                            .innerJoin(type1)
                            .on(field(name(quotedName("type1"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m2"), unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                            .and(field(name(quotedName("type1"), unquotedName(STATE)), String.class).notEqual(InactiveState))
                            .innerJoin(m1)
                            .on(field(name(quotedName("type1"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m1"), unquotedName(HGBST_ELM2HGBST_SHOW)), String.class)))
                            .innerJoin(show1)
                            .on(field(name(quotedName("show1"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("m1"), unquotedName(HGBST_SHOW2HGBST_ELM)), String.class)))
                            .and(field(name(quotedName("show2"), unquotedName(OBJID)), String.class)
                                    .notEqual(field(name(quotedName("show1"), unquotedName(OBJID)), String.class)))
                            .innerJoin(list)
                            .on(field(name(quotedName("show1"), unquotedName(OBJID)), String.class)
                                    .eq(field(name(quotedName("list"), unquotedName(HGBST_LST2HGBST_SHOW)), String.class)))
                            .and(field(name(quotedName("list"), unquotedName(TITLE)), String.class).eq(CaseTypeList))
                            .where(field(name(quotedName("type1"), unquotedName(TITLE)), String.class)
                                    .eq(transfer.getTypeOne()))
                            .and(field(name(quotedName("type2"), unquotedName(TITLE)), String.class)
                                    .eq(transfer.getTypeTwo()))
                            .and(field(name(quotedName("type3"), unquotedName(TITLE)), String.class)
                                    .eq(transfer.getTypeThree()))
                            .and(field(name(quotedName("lst"), unquotedName(X_IS_ACTIVE)), String.class)
                                    .eq(ActiveStatus)))
                    .fetch();
        }
        return sqlResult != null && sqlResult.size() == 1 && 1 == sqlResult.get(0).value1();
    }
}
