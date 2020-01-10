package per.zk.expression.logical;

import per.zk.expression.base.Context;
import per.zk.expression.base.Expression;

/**
 * @Author: Z.K
 * @FileName: Or
 * @DateTime: 2020/1/9 0009
 * @Version 1.0
 * @Description:
 */
public class Or extends Expression {
    private Expression left,right;

    public Or(Expression left , Expression right){
        this.left = left;
        this.right = right;
    }

    @Override
    public Object interpret(Context ctx) {
        return (Boolean)left.interpret(ctx) || (Boolean)right.interpret(ctx);
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " OR " + right.toString() + ")";
    }

}
