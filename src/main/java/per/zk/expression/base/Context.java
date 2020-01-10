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

    private Map<String,Object> map;

    public Context(Map<String, Object> map) {
        this.map = map;
    }
    public Context(String key,Object value) {
        this.map = new HashMap();
        map.put(key,value);
    }
    public Context() {
        this.map = new HashMap();
    }
    public Context assign(String key , Object value){
        map.put(key,value);
        return this;
    }

    public Object lookup(Variable var) throws IllegalArgumentException{
        return map.get(var.toString());
    }
}