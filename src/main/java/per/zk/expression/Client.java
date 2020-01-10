package per.zk.expression;

import per.zk.expression.base.Context;
import per.zk.expression.base.Expression;
import per.zk.expression.base.PostfixExpressionBuilder;

/**
 * @Author: Z.K
 * @FileName: Client
 * @DateTime: 2020/1/9 0009
 * @Version 1.0
 * @Description:
 */
public class Client {

    public static void main(String[] args) {
        Context ctx = new Context();
        ctx.assign("x", 5);
        ctx.assign("y", 3);
        System.out.println(ExpressionUtils.eval("!(x%y>3)",ctx));
        System.out.println(ExpressionUtils.eval("!(2*c>3)",new Context("c",2)));
    }
}
