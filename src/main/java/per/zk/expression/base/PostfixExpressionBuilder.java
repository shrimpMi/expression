package per.zk.expression.base;

import per.zk.expression.comparison.*;
import per.zk.expression.logical.And;
import per.zk.expression.logical.Not;
import per.zk.expression.logical.Or;
import per.zk.expression.logical.Third;
import per.zk.expression.math.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName PostfixExpressionBuilder
 * @Author Z.K
 * @Date 2019/7/31 0:37
 * @Description :
 */
public class PostfixExpressionBuilder {


    public static Expression createExpression(String expStr){
        Queue<String> queue = expToIPE(expStr);
        return evaluation(queue);
    }


    //一些常用运算符
    private static List<String> rpo = new ArrayList<String>(){
        {
            this.add("+");this.add("-");this.add("*");this.add("/");this.add("%"); this.add(">");this.add("==");
            this.add("<");this.add(">=");this.add("<=");this.add("!="); this.add("||");this.add("&&");
            this.add("(");this.add(")");this.add("?");this.add(":");this.add("!");
        }
    };

    //运算符优先级
    private static Map<String,Integer> lev = new HashMap<String,Integer>(){
        {
            this.put("(",1);this.put(")",1);//一级运算符
            this.put("/",3);this.put("*",3);this.put("%",3);//二
            this.put("+",4);this.put("-",4);//
            this.put(">",6);this.put(">=",6);this.put("<=",6);this.put("<",6);//
            this.put("==",7);this.put("!=",7);//
            this.put("&&",8);this.put("||",8);//
            this.put("?",9);this.put(":",9);this.put("!",2);
        }
    };


    /**
     * 常规表达式转成逆波兰表达式
     * @return	栈形式
     */
    private static Queue<String> expToIPE(String expstr){
        if(expstr==null || expstr.length()==0)return null;

        LinkedList<String> in = new LinkedList();
        Queue<String> out = new LinkedBlockingQueue();

        /**压桟**/
        String exp = getExpStr(expstr);

        for(String s:exp.split(" ")){
            if(s==null || s.length()==0)continue;
            if(!rpo.contains(s)){//如果不是运算符 那就放入out
                out.offer(s);
            }else{//如果是运算符
                enterTr(in, out, s);
            }
        }

        /**解压in 到 out**/
        //系统读取中缀表达式结束后将入栈in中的所有符号按后进先出的顺序全部解压，并依次压入出栈out中
        while(in.size()>0){
            out.offer(in.pollLast());
        }

        return out;
    }

    /**
     * 运算符压桟
     * @param in
     * @param out
     * @param s
     */
    private static void enterTr(LinkedList<String> in,Queue<String> out,String s){
        //读入第一个运算符直接压入入栈（in）||读入"（"直接压入入栈（in）
        if(in.size()==0 || s.equals("(")){
            in.offer(s);
            return ;
        }
        //读取"）"时要找到入栈in中最近的"（"，将其前面所有符号全部按后进先出的顺序压入出栈，并解压，"（"与"）"抵消
        if(s.equals(")")){
            String r = "";
            do{
                r = in.pollLast();
                if(!r.equals("(")){//如果不是"("  解压到out
                    out.offer(r);
                }
            }while(!r.equals("("));
            return ;
        }
        //读取":"时要找到入栈in中最近的"?"，将其前面所有符号全部按后进先出的顺序压入出栈，并解压，"?"还回去
        if(s.equals(":")){
            String r = "";
            do{
                r = in.pollLast();
                if(!r.equals("?")){//如果不是"("  解压到out
                    out.offer(r);
                }
            }while(!r.equals("?"));
            in.offer("?");
            return ;
        }
        //第二个的运算符"+"，并将之与入栈（in）中的栈顶运算符"("进行比较，
        //g)高于栈顶运算符级别的算符直接进栈，低于或等于栈顶级别的要将入栈(in)解栈（即出栈），按次压入出栈（out）中
        String r = in.pollLast();
        if(lev.get(r)>=lev.get(s)||r.equals("(")){//如果in内的运算符优先级低
            in.offer(r);
            in.offer(s); //直接压入
        }else{
            out.offer(r);
            enterTr(in, out, s);
        }
    }

    /**
     * 把表达式中的值和运算符用空格分割	便于后续操作
     * c   +xy*(ccc-hhh   ||abc      &&aaa)
     *   ||
     *   \/
     * c + xy *( ccc - hhh || abc && aaa )
     * @param expstr
     * @return
     */
    private static String getExpStr(String expstr){
        if(expstr==null || expstr.length()==0)return null;
        String exp = ""; 	//缓存表达式
        int isCol = -1;		//缓存本次读取的是否是参数	-1表示空  	0表示读到运算符 	1表示读到字母
        for(int i = 0 ; i < expstr.length() ; i++){
            char e = expstr.charAt(i);
            //如果是运算符
            if(e==33||e==37||e==38||e==40||e==41||e==42||e==43||e==45||e==47
                    ||e==60||e==61||e==62||e==124||e==63){
                if(isCol == 1 || e==40){
                    exp += " ";
                }
                exp += e ;
                if(e==41){
                    exp += " ";
                }
                isCol = 0;
            }else{//其他字符作空格处理
                if(isCol==0){
                    exp += " ";
                }
                exp += e ;
                isCol = 1;
            }
        }
        return exp.trim();
    }

    private static Expression evaluation(Queue<String> ipeQueue){
        //结果栈
        LinkedList<Expression> stack = new LinkedList();
        while(ipeQueue.size()>0){
            String st = ipeQueue.poll();
            if(":".equals(st)){
                continue;
            }
            if(!rpo.contains(st)){ //如果是操作数,则将其压入操作数堆栈
                //有引号包裹 '12'  只取其中内容 直接放入栈    不考虑 'ab 或者  ab' 本身作为字符串值的情况
                if(st.startsWith("'") && st.endsWith("'")){
                    stack.offer(new Constant(st.substring(1,st.length()-1)));
                }else if(st.startsWith("\"") && st.endsWith("\"")){
                    stack.offer(new Constant(st.substring(1,st.length()-1)));
                }else if(st.equalsIgnoreCase("null")){//如果有null字符串   还以 null值
                    stack.offer(new Constant(null));
                }else if(isNum(st)){//没有被引号包裹的 如果是纯数字 则是值
                    stack.offer(new Constant(new BigDecimal(st)));
                }else{//没有被引号包裹的 并且不是纯数字的 理论上是引用变量	必须是要传入具体值
                    stack.offer(new Variable(st));
                }
                continue;
            }
            /***如果扫描的项目是一个二元运算符，则对栈的顶上两个操作数执行该运算。**/
            Expression e1 = stack.pollLast();
            //如果是算数运算
            switch (st) {
                /**以下是算数运算**/
                case "+":
                    stack.offer(new Add(stack.pollLast(),e1));
                    break;
                case "-":
                    stack.offer(new Sub(stack.pollLast(),e1));
                    break;
                case "*":
                    stack.offer(new Multiply(stack.pollLast(),e1));
                    break;
                case "/":
                    stack.offer(new Divide(stack.pollLast(),e1));
                    break;
                case "%":
                    stack.offer(new Modulo(stack.pollLast(),e1));
                    break;
                /**以下是逻辑运算 	>,>=,<,<=	默认是数值比较大小 转成Double双精度比较**/
                case ">":
                    stack.offer(new Gt(stack.pollLast(),e1));
                    break;
                case ">=":
                    stack.offer(new Gte(stack.pollLast(),e1));
                    break;
                case "<":
                    stack.offer(new Lt(stack.pollLast(),e1));
                    break;
                case "<=":
                    stack.offer(new Lte(stack.pollLast(),e1));
                    break;
                /** ==,!= 此两类比较麻烦  **/
                case "==":
                    //目前只考虑字符串和数值相等比较
                    stack.offer(new Eq(stack.pollLast(),e1));
                    break;
                case "!=":
                    stack.offer(new Neq(stack.pollLast(),e1));
                    break;
                /**以下默认是两个Boolean型进行运算***/
                case "&&":
                    stack.offer(new And(stack.pollLast(),e1));
                    break;
                case "||":
                    stack.offer(new Or(stack.pollLast(),e1));
                    break;
                case "?":
                    Expression e2 = stack.pollLast();
                    Expression e3 = stack.pollLast();
                    stack.offer(new Third(e3,e2,e1));
                    break;
                case "!":
                    stack.offer(new Not(e1));
                    break;
                default:
                    break;
            }
        }
        return stack.pollLast();
    }

    static Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]*");
    public static boolean isNum(String str) {
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

}
