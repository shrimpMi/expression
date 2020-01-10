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
public class Neq extends Expression {

    private Expression left,right;

    public Neq(Expression left , Expression right){
        this.left = left;
        this.right = right;
    }

    @Override
    public Object interpret(Context ctx) {
        Object l = left.interpret(ctx);
        Object r = right.interpret(ctx);
        if(l==null || r==null){
            if(l==null && r==null){
                return false;
            }else{
                return true;
            }
        }
        if(l instanceof String){
            if(!(r instanceof String)){
                return true;
            }
            return !l.equals(r.toString());
        }
        return new BigDecimal(l.toString()).compareTo(new BigDecimal(r.toString())) != 0;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " != " + right.toString() + ")";
    }

}
