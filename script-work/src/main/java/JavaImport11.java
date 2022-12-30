import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 代码示例 11：脚本可以导入 Java 平台包。
 *   导入必要的 Java 平台包后，脚本可以使用任何本机 Java 类
 * @author bingshan
 * @date 2022/12/30 14:10
 */
public class JavaImport11 {
    public static void main(String[] args) {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine jsEngine = mgr.getEngineByName("JavaScript");
        try {
            jsEngine.eval("var optionPane = " +
                    "  javax.swing.JOptionPane.showMessageDialog(null, 'Hello, world!');");
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
    }
}
