package bp.checker.checkers.implementations;

import bp.checker.checkers.AbstractDbChecker;
import bp.checker.entity.implementations.StringField;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.sql.Connection;

import static bp.model.Constants.FieldsName.FAMILY;
import static bp.model.Constants.FieldsName.PART_NUMBER;
import static bp.model.Constants.SqlQueryConstants.KKFU;
import static bp.model.Constants.TableNames.DUAL;
import static bp.model.Constants.TableNames.PART_NUM;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class ProductChecker extends AbstractDbChecker <StringField> {
    public ProductChecker(Connection connection) {
        super(connection);
        table += PART_NUM;
    }

    @Override
    public boolean check(StringField field) {
        Result<Record1<Integer>> sqlResult = DSL.using(connection)
                .selectCount()
                .from(DUAL)
                .whereExists(DSL.using(connection)
                        .select()
                        .from(table(table))
                        .where(field(PART_NUMBER).eq(field.getValue()))
                        .and(field(FAMILY).notEqual(KKFU)))
                .fetch();

        return sqlResult != null && sqlResult.size() == 1  && 1 == sqlResult.get(0).value1();
    }
}
