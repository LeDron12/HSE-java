package mappertest.testclasses;

import mapper.annotations.Exported;

import java.time.LocalDate;

@Exported
public class TestClassLocalDate {
    public int val;
    public LocalDate localDate;
    public String str;

    public TestClassLocalDate(int val, String str, LocalDate localDate) {
        this.val = val;
        this.str = str;
        this.localDate = localDate;
    }

    public TestClassLocalDate() {
    }
}
