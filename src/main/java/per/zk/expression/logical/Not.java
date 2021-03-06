package per.zk.expression.logical;

import per.zk.expression.base.Context;
import per.zk.expression.base.Expression;

/**
 * @Author: Z.K
 * @FileName: Not
 * @DateTime: 2020/1/9 0009
 * @Version 1.0
 * @Description:
 */
public class Not extends Expression {

    private Expression exp;

    public Not(Expression exp){
        this.exp = exp;
    }

    @Override
    public Object interpret(Context ctx) {
        return !((Boolean)exp.interpret(ctx));
    }

    @Override
    public String toString() {
        return "(Not " + exp.toString() + ")";
    }

}
