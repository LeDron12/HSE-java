package mappertest.testclasses;

import mapper.annotations.Exported;
import mapper.annotations.Ignored;
import mapper.annotations.PropertyName;

@Exported
public class TestClassPropertyName {
    public int val;

    public char ch;

    @PropertyName("changed")
    public double d;

    @PropertyName("whatHappens")
    public String str, str2;

    public TestClassPropertyName(int val, char ch, double d, String str, String str2) {
        this.val = val;
        this.ch = ch;
        this.d = d;
        this.str = str;
        this.str2 = str2;
    }

    public TestClassPropertyName() {
    }
}