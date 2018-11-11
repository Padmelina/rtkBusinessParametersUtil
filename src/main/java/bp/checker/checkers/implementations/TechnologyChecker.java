package bp.checker.checkers.implementations;

import bp.checker.checkers.AbstractDbChecker;
import bp.checker.entity.implementations.StringField;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.sql.Connection;

import static bp.model.Constants.FieldsName.X_NAME_TECHN;
import static bp.model.Constants.TableNames.DUAL;
import static bp.model.Constants.TableNames.TECHNOLOGY;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class TechnologyChecker extends AbstractDbChecker <StringField> {
    public TechnologyChecker(Connection connection) {
        super(connection);
        this.table += TECHNOLOGY;
    }

    @Override
    public boolean check(StringField field) {
        Result<Record1<Integer>> sqlResult = DSL.using(connection)
                .selectCount()
                .from(DUAL)
                .whereExists(DSL.using(connection)
                        .select()
                        .from(table(table))
                        .where(field(X_NAME_TECHN).eq(field.getValue())))
                .fetch();

        return sqlResult != null && sqlResult.size() == 1  && 1 == sqlResult.get(0).value1();
    }
}
