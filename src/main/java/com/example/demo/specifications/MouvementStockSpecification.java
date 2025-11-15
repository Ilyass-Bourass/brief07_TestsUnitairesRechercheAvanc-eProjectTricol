package com.example.demo.specifications;

import com.example.demo.entity.MouvementStock;
import com.example.demo.entity.enums.TypeMouvement;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class MouvementStockSpecification {
    public static Specification<MouvementStock> hasType (TypeMouvement type) {
        return (root,query,cb)-> type==null ? null :cb.equal(root.get("typeMouvement"), type);
    }

    public static Specification<MouvementStock> betweenDates(LocalDateTime start, LocalDateTime end) {
        return (root, query, cb) -> {
            if(start == null && end == null) return null;
            if(start != null && end != null) return cb.between(root.get("dateMouvement"), start, end);
            if(start != null) return cb.greaterThanOrEqualTo(root.get("dateMouvement"), start);
            return cb.lessThanOrEqualTo(root.get("dateMouvement"), end);
        };
    }

    public static Specification<MouvementStock> hasProduitId(Long id) {
        return (root, query, cb) -> id==null ? null : cb.equal(root.get("stock").get("produit").get("id"), id);
    }


}
