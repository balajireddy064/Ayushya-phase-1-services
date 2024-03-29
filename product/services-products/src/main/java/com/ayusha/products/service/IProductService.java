package com.ayusha.products.service;

import com.ayusha.products.data.models.ProductDataModel;
import com.ayusha.products.data.models.ProductsDataModel;
import com.ayusha.products.service.entity.ProductEntity;
import com.ayusha.services.common.exceptions.DataPersistenceOperationException;
import com.ayusha.services.common.exceptions.InvalidServiceRequestException;
import com.ayusha.services.common.exceptions.ResourceNotFoundException;

/**
 * 
 * @author author1 Date : 01-Aug-2019 Ticket Model class Defines the ticket
 *         service methods
 *
 */
public interface IProductService {

	/** add **/
	public ProductDataModel getProductById(String productId)
			throws DataPersistenceOperationException, InvalidServiceRequestException;

	/** add **/
	public ProductsDataModel getProductFor(String categoryId, String subCategoryId, String makeId)
			throws DataPersistenceOperationException, InvalidServiceRequestException;

	/** add **/
	public ProductDataModel getProductFor(String categoryId, String subCategoryId, String makeId, String modelId)
			throws DataPersistenceOperationException, InvalidServiceRequestException,ResourceNotFoundException;

	/** update **/
	public ProductDataModel updateProduct(ProductDataModel productDataModel)
			throws DataPersistenceOperationException, InvalidServiceRequestException;

	/** update **/
	public ProductDataModel add(ProductDataModel productDataModel)
			throws DataPersistenceOperationException, InvalidServiceRequestException;

	public ProductEntity getProduct(String product_id) throws DataPersistenceOperationException,
			InvalidServiceRequestException, ResourceNotFoundException, Exception;

}
