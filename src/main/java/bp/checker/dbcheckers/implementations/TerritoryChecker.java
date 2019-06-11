package bp.checker.dbcheckers.implementations;

import bp.checker.dbcheckers.AbstractDbChecker;
import bp.checker.dbcheckers.entity.implementations.StringField;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.DSL;

import static bp.model.constants.Constants.FieldsName.TERR_ID;
import static bp.model.constants.Constants.TableNames.DUAL;
import static bp.model.constants.Constants.TableNames.TERRITORY;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class TerritoryChecker extends AbstractDbChecker <StringField> {
    public TerritoryChecker() {
        super();
        table += TERRITORY;
    }

    @Override
    public boolean check(StringField field) {
        Result<Record1<Integer>> sqlResult = DSL.using(connection)
                .selectCount()
                .from(DUAL)
                .whereExists(DSL.using(connection)
                        .select()
                        .from(table(table))
                        .where(field(TERR_ID).eq(field.getValue())))
                .fetch();

        return sqlResult != null && sqlResult.size() == 1  && 1 == sqlResult.get(0).value1();
    }
}
