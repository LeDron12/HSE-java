package mappertest.testclasses;

import mapper.annotations.Exported;

@Exported
public class TestClassComplexOne {
    public TestClassOne testClassOne;
    public TestClassList testClassList;
    public TestClassLocalTime testClassLocalTime;

    public TestClassComplexOne(TestClassOne testClassOne,
                               TestClassList testClassList, TestClassLocalTime testClassLocalTime) {
        this.testClassOne = testClassOne;
        this.testClassList = testClassList;
        this.testClassLocalTime = testClassLocalTime;
    }

    public TestClassComplexOne() {
    }
}
