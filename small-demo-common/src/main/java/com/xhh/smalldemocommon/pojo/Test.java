package com.xhh.smalldemocommon.pojo;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;

import java.sql.SQLException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws SQLException {
        Table<Long, Long, List<Student>> table = HashBasedTable.create();
        List<Student> students = Lists.newArrayList();
        Student student = new Student.Builder().code("code").name("name").status((byte) 1).build();
        students.add(student);
        table.put(1L, 1L, students);

        System.out.println(table.get(1L, 2L));
        

    }
}
