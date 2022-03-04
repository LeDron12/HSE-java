package mappertest.testclasses;

import mapper.annotations.Exported;

@Exported
public enum TestSimpleEnum {
    KILOMETER("km", 1000),
    MILE("miles", 1609.34),
    METER("meters", 1),
    INCH("inches", 0.0254),
    CENTIMETER("cm", 0.01),
    MILLIMETER("mm", 0.001);

    public String name;
    public double val;

    TestSimpleEnum(String key, double value) {
        name = key;
        val = value;
    }
}