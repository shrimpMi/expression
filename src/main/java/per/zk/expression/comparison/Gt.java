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
public class Gt extends Expression {

    private Expression left,right;

    public Gt(Expression left , Expression right){
        this.left = left;
        this.right = right;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Gt)
        {
            return left.equals(((Gt)obj).left) &&
                    right.equals(((Gt)obj).right);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public Object interpret(Context ctx) {
        return new BigDecimal(left.interpret(ctx).toString()).compareTo(new BigDecimal(right.interpret(ctx).toString())) > 0;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " > " + right.toString() + ")";
    }

}
