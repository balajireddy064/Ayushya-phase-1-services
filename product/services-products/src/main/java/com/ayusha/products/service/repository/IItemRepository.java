package com.ayusha.products.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ayusha.products.service.entity.ItemEntity;
import com.ayusha.products.service.entity.MakeEntity;


/**
 * 
 * @author Finch
 * Date : 01-Aug-2019
 * Product Model class 
 * Defines the methods for the product management persistance CRUD operations
 *
 */
@Component
@Repository
public interface IItemRepository extends CrudRepository<ItemEntity,Integer>{

	 @Query("SELECT t FROM ItemEntity t WHERE t.id = ?1")
	 ItemEntity findMakeById(String id);
	 
	 @Query("SELECT t FROM ItemEntity t WHERE t.itemId = ?1")
	 ItemEntity findItemByItemId(String itemId);
	 
	 @Query("select t FROM ItemEntity t WHERE t.productId=?1")
	    List<ItemEntity> findItemsByProductId(String productId);
	 
	 @Query("select t FROM ItemEntity t WHERE t.productId=:productId and t.type=:typeId")
	    List<ItemEntity> findItemsByProductIdandType(@Param("productId") String productId,@Param("typeId") String typeId);
}
