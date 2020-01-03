package com.shengsiyuan.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class ProtoBufTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        DataInfo.Student student = DataInfo.Student.
                newBuilder().setName("胡啸").setAge(22).setAddress("桐城").build();

        byte[] toByteArray = student.toByteArray();

        DataInfo.Student student1 = DataInfo.Student.parseFrom(toByteArray);
        System.out.println(student1.getName());
        System.out.println(student1.getAge());
        System.out.println(student1.getAddress());
    }
}
