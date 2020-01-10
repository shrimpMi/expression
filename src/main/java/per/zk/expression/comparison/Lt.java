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
public class Lt extends Expression {

    private Expression left,right;

    public Lt(Expression left , Expression right){
        this.left = left;
        this.right = right;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Lt)
        {
            return left.equals(((Lt)obj).left) &&
                    right.equals(((Lt)obj).right);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public Object interpret(Context ctx) {
        return new BigDecimal(left.interpret(ctx).toString()).compareTo(new BigDecimal(right.interpret(ctx).toString())) < 0;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " < " + right.toString() + ")";
    }

}
