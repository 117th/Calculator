package calc;

import javax.script.*;

@SuppressWarnings("all")
public class Calculator {

    ScriptEngine js = new ScriptEngineManager().getEngineByName("javascript");
    Bindings bindings = js.getBindings(ScriptContext.ENGINE_SCOPE);

    public Calculator(){
        bindings.put("stdout", System.out);
    }

    public Double calc(String exp) throws ScriptException {
        Object result = js.eval(convertToJsFormat(exp));

        if(result instanceof Integer) return new Double((Integer) result);
        else return (Double) result;
    }

    private String convertToJsFormat(String exp){
        return exp.toLowerCase().replaceAll("cos", "Math.cos")
                .replaceAll("sin", "Math.sin")
                .replaceAll("tg", "Math.tan");
    }

}
