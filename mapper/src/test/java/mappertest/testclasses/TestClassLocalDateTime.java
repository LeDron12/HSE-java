package mappertest.testclasses;

import mapper.annotations.Exported;

import java.time.LocalDateTime;

@Exported
public class TestClassLocalDateTime {
    public int val;
    public LocalDateTime localDateTime;
    public String str;

    public TestClassLocalDateTime(int val, String str, LocalDateTime localDateTime) {
        this.val = val;
        this.str = str;
        this.localDateTime = localDateTime;
    }

    public TestClassLocalDateTime() {
    }
}
