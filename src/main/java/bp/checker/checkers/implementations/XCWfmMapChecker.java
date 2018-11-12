package bp.checker.checkers.implementations;

import bp.checker.checkers.AbstractDbChecker;
import bp.checker.entity.implementations.WfmMap;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.sql.Connection;

import static bp.model.Constants.FieldsName.*;
import static bp.model.Constants.SqlQueryConstants.ActiveStatus;
import static bp.model.Constants.TableNames.X_C_WFM_MAP;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class XCWfmMapChecker extends AbstractDbChecker<WfmMap> {

    public XCWfmMapChecker(Connection connection) {
        super(connection);
        table += X_C_WFM_MAP;
    }

    @Override
    public boolean check(WfmMap value) {
        Result<Record1<Integer>> sqlResult = DSL.using(connection)
                .selectCount()
                    .from(table(table))
                    .where(field(X_TECH_FAMILY).eq(value.getTechnologyName()))
                        .and(field(X_C_WFM_MAP2PART_NUM).eq(value.getPartNumObjid()))
                        .and(field(X_C_WFM_MAP2TERRITORY).eq(value.getTerritoryObjid()))
                        .and(field(X_C_WFM_MAP2C_TYPE_LVL3).eq(value.getTypeThreeObjid()))
                        .and(field(X_IS_ACTIVE, String.class).eq(ActiveStatus))
                .fetch();
        return sqlResult != null && 0 == sqlResult.get(0).value1();
    }
}