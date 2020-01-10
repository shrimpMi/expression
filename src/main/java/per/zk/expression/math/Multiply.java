package per.zk.expression.math;

import per.zk.expression.base.Context;
import per.zk.expression.base.Expression;
import java.math.BigDecimal;

/**
 * @Author: Z.K
 * @FileName: And
 * @DateTime: 2020/1/9 0009
 * @Version 1.0
 * @Description: 乘法
 */
public class Multiply extends Expression {

    private Expression left,right;

    public Multiply(Expression left , Expression right){
        this.left = left;
        this.right = right;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Multiply){
            return left.equals(((Multiply)obj).left) &&
                    right.equals(((Multiply)obj).right);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public Object interpret(Context ctx) {
        return new BigDecimal(left.interpret(ctx).toString()).multiply(new BigDecimal(right.interpret(ctx).toString()));
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " * " + right.toString() + ")";
    }

}
