package per.zk.expression.comparison;

import per.zk.expression.base.Context;
import per.zk.expression.base.Expression;
import java.math.BigDecimal;

/**
 * @Author: Z.K
 * @FileName: And
 * @DateTime: 2020/1/9 0009
 * @Version 1.0
 * @Description:
 */
public class Lte extends Expression {

    private Expression left,right;

    public Lte(Expression left , Expression right){
        this.left = left;
        this.right = right;
    }

    @Override
    public Object interpret(Context ctx) {
        return new BigDecimal(left.interpret(ctx).toString()).compareTo(new BigDecimal(right.interpret(ctx).toString())) <= 0;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " <= " + right.toString() + ")";
    }

}
