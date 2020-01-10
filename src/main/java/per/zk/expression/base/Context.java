package per.zk.expression.base;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Z.K
 * @FileName: Context
 * @DateTime: 2020/1/9 0009
 * @Version 1.0
 * @Description: 上下文
 */
public class Context {

    private Map<String,Object> map = new HashMap();

    public void assign(String key , Object value){
        map.put(key,value);
    }

    public Object lookup(Variable var) throws IllegalArgumentException{
        return map.get(var.toString());
    }
}