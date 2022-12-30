import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 代码示例 1：使用引擎名称创建对象
 * @author bingshan
 * @date 2022/12/30 11:33
 */
public class ScriptEngine1 {
    public static void main(String[] args) {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine jsEngine = mgr.getEngineByName("JavaScript");
        try {
            Object result = jsEngine.eval("print('Hello, world!')");
            System.out.println("result= " + result);
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
    }
}
