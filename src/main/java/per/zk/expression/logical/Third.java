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
public class Third extends Expression {

    private Expression exp,left,right;

    public Third(Expression exp ,Expression left , Expression right){
        this.exp = exp;
        this.left = left;
        this.right = right;
    }

    @Override
    public Object interpret(Context ctx) {
        return (Boolean)exp.interpret(ctx) ? left.interpret(ctx) : right.interpret(ctx);
    }

    @Override
    public String toString() {
        return "(" + exp.toString() + " ? " + left.toString() + " : " + right.toString() + ")";
    }

}
