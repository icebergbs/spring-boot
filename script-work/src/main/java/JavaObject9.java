import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;

/**
 * 代码示例 9：脚本代码可以访问和修改本机 Java 对象。
 * @author bingshan
 * @date 2022/12/30 11:52
 */
public class JavaObject9 {
    public static void main(String[] args) {
        List<String> namesList = new ArrayList<String>();
        namesList.add("Jill");
        namesList.add("Bob");
        namesList.add("Laureen");
        namesList.add("Ed");

        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine jsEngine = mgr.getEngineByName("JavaScript");
        jsEngine.put("namesListKey", namesList);
        System.out.println("Executing in script enviromment...");
        try {
            jsEngine.eval("var x;" +
                    "var names = namesListKey.toArray();" +
                    "for(x in names) {" +
                    "  print(names[x]);" +
                    "}" +
                    "namesListKey.add(\"Dana\");");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        System.out.println("Executing in Java environment...");
        for (String name: namesList) {
            System.out.println(name);
        }
    }
}
