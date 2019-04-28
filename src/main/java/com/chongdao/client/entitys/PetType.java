package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description 宠物种类(猫/狗...)
 * @Author onlineS
 * @Date 2019/4/25
 * @Version 1.0
 **/
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pet_type")
public class PetType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String name;
    private String icon;//标志, 是个图片, 用来区分宠物种类
    private int sort;

    @Override
    public String toString() {
        return "PetType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", sort=" + sort +
                '}';
    }
}
