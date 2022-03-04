package mappertest.testclasses;

import mapper.annotations.Exported;
import mapper.annotations.Ignored;
import mapper.annotations.PropertyName;

import java.util.List;
import java.util.Set;

@Exported
public class TestClassComplexTwo {
    public List<TestClassOne> list;
    @Ignored
    public TestClassOne testClassOne;
    public int x;
    @PropertyName("yaVmestoComplexBro")
    private Set<TestClassIgnored> set;
    private String nadpisb;

    public TestClassComplexTwo(List<TestClassOne> list, int x, Set<TestClassIgnored> set,
                               String nadpisb, TestClassOne testClassOne) {
        this.list = list;
        this.testClassOne = testClassOne;
        this.x = x;
        this.set = set;
        this.nadpisb = nadpisb;
    }

    public TestClassComplexTwo() {

    }
}
