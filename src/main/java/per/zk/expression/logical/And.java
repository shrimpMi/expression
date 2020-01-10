package per.zk.expression.logical;

import per.zk.expression.base.Context;
import per.zk.expression.base.Expression;

/**
 * @Author: Z.K
 * @FileName: And
 * @DateTime: 2020/1/9 0009
 * @Version 1.0
 * @Description:
 */
public class And extends Expression {

    private Expression left,right;

    public And(Expression left , Expression right){
        this.left = left;
        this.right = right;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof And)
        {
            return left.equals(((And)obj).left) &&
                    right.equals(((And)obj).right);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public Object interpret(Context ctx) {
        return (Boolean)left.interpret(ctx) && (Boolean)right.interpret(ctx);
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " AND " + right.toString() + ")";
    }

}
