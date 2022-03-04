package mappertest.testclasses;

import mapper.annotations.Exported;

import java.util.List;

@Exported
public class TestClassLists {
    public int x;
    public String str;
    public List<String> list1, list2, list3;
    public double y;

    public TestClassLists(int x, double y, String s, List<String> list1,
                          List<String> list2, List<String> list3) {
        this.x = x;
        this.y = y;
        this.str = s;
        this.list1 = list1;
        this.list2 = list2;
        this.list3 = list3;
    }

    public TestClassLists() {
    }
}
