package mappertest.testclasses;

import mapper.annotations.Exported;

import java.util.List;

@Exported
public class TestClassList {
    public int x;
    public String str;
    public List<String> list;
    public double y;

    public TestClassList(int x, double y, String s, List<String> list) {
        this.x = x;
        this.y = y;
        this.str = s;
        this.list = list;
    }

    public TestClassList() {
    }
}
