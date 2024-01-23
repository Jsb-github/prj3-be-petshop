package com.example.prj3bepetshop.mapper;

import com.example.prj3bepetshop.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PetProductMapper {
    @Select("""
            SELECT *
            FROM petproducts
            ORDER BY no DESC
            """)
    List<Product> selectAll();

    @Select("""
            SELECT *
            FROM petproducts
            WHERE category = #{category}
            ORDER BY no DESC
            """)
    List<Product> selectByCategory(String category);

    @Select("""
               SELECT
               pd.no, category, title, pd.price, fileName,
               og.no, consumer, quantity,og.total
               FROM petproducts pd
               LEFT JOIN ordergoods og
               ON pd.no = og.productNo
               WHERE og.detailNo = #{no};
            """)
   List<Product>  selectById(Integer no);


    @Select("""
                SELECT *
                FROM petproducts
                WHERE no =#{no}
            """)
    Product selectByProduct(Integer no);
}
