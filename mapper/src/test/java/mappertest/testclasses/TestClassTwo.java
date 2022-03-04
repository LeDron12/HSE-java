package mappertest.testclasses;

import mapper.annotations.Exported;

@Exported
public class TestClassTwo {
    public int val;
    public char ch;
    public double d;
    public String str, str2;

    public TestClassTwo(int val, char ch, double d, String str, String str2) {
        this.val = val;
        this.ch = ch;
        this.d = d;
        this.str = str;
        this.str2 = str2;
    }

    public TestClassTwo() {
    }
}