package com.example.prj3bepetshop.mapper;

import com.example.prj3bepetshop.domain.Order;
import com.example.prj3bepetshop.domain.OrderDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert("""
                INSERT INTO orderdetail(
                consumer, totalPrice
                ) VALUES (#{consumer},#{totalPrice})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "no")
    Integer saveDetail(OrderDetail detail);


    @Insert("""
                INSERT INTO ordergoods(
                  consumer, productNo, productName,quantity, total, detailNo
                ) VALUES (
                    #{consumer}, #{productNo}, #{productName}, #{quantity}, #{total}, #{detailNo}
                )
            """)
    int saveGoods(Order order);


    @Select("""
                SELECT *
                FROM orderdetail
                WHERE consumer = #{email}
            """)
    List<Order> selectByDetail(String email);
}
