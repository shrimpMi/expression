package per.zk.expression.base;

/**
 * @Author: Z.K
 * @FileName: Variable
 * @DateTime: 2020/1/9 0009
 * @Version 1.0
 * @Description: 变量类
 */
public class Variable extends Expression {

    private String name;

    public Variable(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Object interpret(Context ctx) {
        return ctx.lookup(this);
    }

}
