package com.xhh.smalldemobackend.Enum;

public class ConstantEnum {

    public enum UserType {
        
        STUDENT("学生", (byte) 1),
        
        PARENTS("家长",(byte)2),
        
        TEACHER("老师", (byte) 6);
        
        public String name;
        public Byte type;

        UserType(String name, Byte type) {
            this.name = name;
            this.type = type;
        }

        public static UserType getUserTypeByType(Byte type) {
            UserType[] values = values();
            for (UserType userType : values) {
                if (userType.type.equals(type)) {
                    return userType;
                }
            }
            return null;
        }
    }

    public enum ItemType {                                                                      
         
        POSITIVE("积极", (byte) 1),
        
        NEGATIVE("消极", (byte) -1);

        public String name;
        public Byte type;

        ItemType(String name, Byte type) {
            this.name = name;
            this.type = type;
        }
        
    }
}
