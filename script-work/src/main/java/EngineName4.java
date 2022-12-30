import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import java.util.List;

/**
 * 代码示例 4：您可以搜索满足应用程序要求的脚本引擎。
 * @author bingshan
 * @date 2022/12/30 11:00
 */
public class EngineName4 {
    public static void main(String[] args) {
        ScriptEngineManager mgr = new ScriptEngineManager();
        List<ScriptEngineFactory> scriptFactories =
                mgr.getEngineFactories();
        for (ScriptEngineFactory factory: scriptFactories) {
            String langName = factory.getLanguageName();
            String langVersion = factory.getLanguageVersion();
            if (langName.equals("ECMAScript") &&
                    langVersion.equals("1.6")) {
                ScriptEngine engine = factory.getScriptEngine();
                System.out.println("ECMAScript");
                break;
            }
        }
    }
}
