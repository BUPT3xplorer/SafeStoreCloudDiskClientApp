public class CheckSQL {

    private String[] ill = new String[] {
            "\'", "\"", "\\", ";", "+", "-", "*", "/", ",",
            "=", "%", "(", ")", "&", "|", "^", ">", "<",
            "select", "from", "where", "distinct", "all",
            "and", "or", "not", "join", "union", "limit",
            "create", "table", "insert", "into", "values",
            "delete", "update", "as", "order", "desc", "by",
            "asc", "between", "intersect", "except", "null",
            "set", "drop", "alter", "add", "truncate",
            "avg", "min", "max", "sum", "count", "having",
            "exists", "in", "database", "first", "last" };// 过滤字符串

    public boolean exec(String s) {
        for(int i = 0; i < 61; i++) {
            if(s.toLowerCase().contains(ill[i])) {
                return false;// 检查未通过
            }
        }
        return true;// 检查通过
    }
}
