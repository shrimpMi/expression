package per.zk.expression;

import per.zk.expression.base.Context;
import per.zk.expression.base.Expression;
import per.zk.expression.base.PostfixExpressionBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Z.K
 * @FileName: ExpressionUtils
 * @DateTime: 2020/1/10 0010
 * @Version 1.0
 * @Description:
 */
public class ExpressionUtils {

    private static Map<String,Expression> exps = new HashMap<>();

    public static Object eval(String expStr){
        Expression expression = getExpression(expStr);
        return expression.interpret(null);
    }

    public static Object eval(String expStr, Context cxt){
        Expression expression = getExpression(expStr);
        return expression.interpret(cxt);
    }

    private static Expression getExpression(String expStr){
        Expression expression = exps.get(expStr);
        if(expression!=null){
            return expression;
        }
        return createExp(expStr);
    }

    private static synchronized Expression createExp(String expStr){
        Expression expression = exps.get(expStr);
        if(expression!=null){
            return expression;
        }
        expression = PostfixExpressionBuilder.createExpression(expStr);
        exps.put(expStr,expression);
        return expression;
    }

}
