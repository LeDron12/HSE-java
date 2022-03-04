package mappertest.testclasses;

import mapper.annotations.Exported;

import java.util.List;
import java.util.Set;

@Exported
public class TestClassSet {
    public int x;
    public String str;
    public Set<String> set;
    public double y;

    public TestClassSet(int x, double y, String s, Set<String> set) {
        this.x = x;
        this.y = y;
        this.str = s;
        this.set = set;
    }

    public TestClassSet() {

    }
}
