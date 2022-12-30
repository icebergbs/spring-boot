import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;

/**
 * 代码示例 10：应用程序可以使用接口将值传递给脚本。Invocable
 *  脚本代码可以访问和修改通过接口提供的过程参数，而不是使用键值对绑定机制。
 *  代码示例 10 演示如何通过接口使用 Java 对象。代码将值作为方法的参数传递给脚本环境。
 * @author bingshan
 * @date 2022/12/30 14:04
 */
public class Invocable10 {

    public static void main(String[] args) {
        List<String> namesList = new ArrayList<String>();
        namesList.add("Jill");
        namesList.add("Bob");
        namesList.add("Laureen");
        namesList.add("Ed");

        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine jsEngine = mgr.getEngineByName("JavaScript");
        Invocable invocableEngine = (Invocable) jsEngine;
        try {
            jsEngine.eval("function printNames1(namesList) {" +
                    "  var x;" +
                    "  var names = namesList.toArray();" +
                    "  for(x in names) {" +
                    "    print(names[x]);" +
                    "  }" +
                    "}" +

                    "function addName(namesList, name) {" +
                    "  namesList.add(name);" +
                    "}");
            invocableEngine.invokeFunction("printNames1", namesList);
            invocableEngine.invokeFunction("addName", namesList, "Dana");
        } catch (ScriptException ex) {
            ex.printStackTrace();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        }

        System.out.println("Executing in Java environment...");
        for (String name: namesList) {
            System.out.println(name);
        }

    }
}
