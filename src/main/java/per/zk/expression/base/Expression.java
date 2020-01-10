package per.zk.expression.base;

/**
 * @Author: Z.K
 * @FileName: Express
 * @DateTime: 2020/1/9 0009
 * @Version 1.0
 * @Description:
 */
public abstract class Expression {
    /**
     * 以环境为准，本方法解释给定的任何一个表达式
     */
    public abstract Object interpret(Context ctx);

    /**
     * 将表达式转换成字符串
     */
    public abstract String toString();
}