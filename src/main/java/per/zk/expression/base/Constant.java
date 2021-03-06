package per.zk.expression.base;

/**
 * @Author: Z.K
 * @FileName: Constant
 * @DateTime: 2020/1/9 0009
 * @Version 1.0
 * @Description: 常量类
 */
public class Constant extends Expression{

    private Object value;

    public Constant(Object value){
        this.value = value;
    }

    @Override
    public Object interpret(Context ctx) {
        return value;
    }

    @Override
    public String toString() {
        if(value==null){
            return "null";
        }else if(value instanceof String){
            return "'"+value+"'";
        }
        return value.toString();
    }

}