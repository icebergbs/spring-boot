import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 代码示例 7：可以使用接口调用脚本中的特定方法。Invocable
 *
 *  如果脚本包含一个名为 的函数，则可以通过将对象强制转换为对象并调用其方法来重复调用它。
 *  或者，如果脚本定义了对象，则可以使用该方法调用对象方法。
 * @author bingshan
 * @date 2022/12/30 11:45
 */
public class Invocable7 {

    public static void main(String[] args) {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine jsEngine = mgr.getEngineByName("JavaScript");
        try {
            jsEngine.eval("function sayHello() {" +
                    "  print('Hello, world!');" +
                    "}");
            Invocable invocableEngine = (Invocable) jsEngine;
            Object result = invocableEngine.invokeFunction("sayHello");
            System.out.println("result= " + result);
        } catch (ScriptException | NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }
}
