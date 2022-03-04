package mappertest.testclasses;

import mapper.annotations.Exported;

import java.time.LocalTime;

@Exported
public class TestClassLocalTime {
    public int val;
    public LocalTime localTime, localPartTime;
    public String str;

    public TestClassLocalTime(int val, String str, LocalTime localTime, LocalTime localPartTime) {
        this.val = val;
        this.str = str;
        this.localTime = localTime;
        this.localPartTime = localPartTime;
    }

    public TestClassLocalTime() {
    }
}
