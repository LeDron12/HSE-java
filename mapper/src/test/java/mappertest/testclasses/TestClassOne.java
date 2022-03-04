package mappertest.testclasses;

import mapper.annotations.Exported;

@Exported
public class TestClassOne {
    public int val;
    public String str;

    public TestClassOne(int val, String str) {
        this.val = val;
        this.str = str;
    }

    public TestClassOne() {

    }
}
