package com.scm.backend.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.scm.backend.model.entity.Item;
import com.scm.backend.model.entity.QItem;
import com.scm.backend.model.entity.QItemType;
import com.scm.backend.model.entity.QSupplier;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

public class ItemRepositoryCustomImpl extends SupperRepositoryCustom implements ItemRepositoryCustom{
    @Override
    public List<Item> findItemsQuery(String condition) {
        QItem item = QItem.item;

        BooleanBuilder builder = new BooleanBuilder();

        if(!condition.isEmpty()){
            builder.and(
                    new BooleanBuilder()
                            .or(item.itemNumber.stringValue().containsIgnoreCase(condition))
                            .or(item.name.containsIgnoreCase(condition))
                            .or(item.availableQuantity.stringValue().containsIgnoreCase(condition))
                            .or(item.salesPrice.stringValue().containsIgnoreCase(condition))
                            .or(item.itemType.typeName.containsIgnoreCase(condition))
                            .or(item.supplier.name.containsIgnoreCase(condition))
            );
        }

        return new JPAQuery<Item>(em)
                .from(QItem.item)
                .leftJoin(QItem.item.itemType, QItemType.itemType)
                .fetchJoin()
                .leftJoin(QItem.item.supplier, QSupplier.supplier)
                .fetchJoin()
                .where(builder)
                .orderBy(QItem.item.itemNumber.asc())
                .fetchAll().distinct().fetch();

    }
}
