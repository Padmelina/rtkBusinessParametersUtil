package bp.checker.dbcheckers.implementations;

import bp.checker.dbcheckers.AbstractDbChecker;
import bp.checker.dbcheckers.entity.implementations.StringField;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.DSL;

import static bp.model.constants.Constants.FieldsName.FAMILY;
import static bp.model.constants.Constants.FieldsName.PART_NUMBER;
import static bp.model.constants.Constants.SqlQueryConstants.ANY;
import static bp.model.constants.Constants.SqlQueryConstants.KKFU;
import static bp.model.constants.Constants.TableNames.DUAL;
import static bp.model.constants.Constants.TableNames.PART_NUM;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class ProductChecker extends AbstractDbChecker <StringField> {
    public ProductChecker(){
        super();
        table += PART_NUM;
    }

    @Override
    public boolean check(StringField field) {
        if (field.getValue().equals(constants.get(ANY))) return true;
        Result<Record1<Integer>> sqlResult = DSL.using(connection)
                .selectCount()
                .from(DUAL)
                .whereExists(DSL.using(connection)
                        .select()
                        .from(table(table))
                        .where(field(PART_NUMBER).eq(field.getValue()))
                        .and(field(FAMILY).notEqual(constants.get(KKFU))))
                .fetch();

        return sqlResult != null && sqlResult.size() == 1  && 1 == sqlResult.get(0).value1();
    }
}
