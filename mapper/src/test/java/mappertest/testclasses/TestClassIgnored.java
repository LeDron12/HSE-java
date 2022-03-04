package mappertest.testclasses;

import mapper.annotations.Exported;
import mapper.annotations.Ignored;

@Exported
public class TestClassIgnored {
    @Ignored
    public int val;

    public char ch;

    @Ignored
    public double d;

    public String str, str2;

    public TestClassIgnored(int val, char ch, double d, String str, String str2) {
        this.val = val;
        this.ch = ch;
        this.d = d;
        this.str = str;
        this.str2 = str2;
    }

    public TestClassIgnored() {
    }
}