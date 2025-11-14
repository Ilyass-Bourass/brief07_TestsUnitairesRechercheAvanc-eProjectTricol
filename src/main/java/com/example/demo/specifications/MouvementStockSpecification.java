package com.example.demo.specifications;

import com.example.demo.entity.MouvementStock;
import com.example.demo.entity.enums.TypeMouvement;
import org.springframework.data.jpa.domain.Specification;

public class MouvementStockSpecification {
    public static Specification<MouvementStock> hasType (TypeMouvement type) {
        return (root,query,cb)-> type==null ? null :cb.equal(root.get("typeMouvement"), type);
    }
}
