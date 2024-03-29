package com.ayusha.products.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayusha.products.data.models.SymptomDataModel;
import com.ayusha.products.data.models.SymptomsDataModel;
import com.ayusha.products.service.entity.ProductSymptomsEntity;
import com.ayusha.products.service.entity.SymptomEntity;
import com.ayusha.products.service.repository.IProductSymptomsRepository;
import com.ayusha.products.service.repository.ISymptomRepository;
import com.ayusha.services.common.exceptions.DataPersistenceOperationException;
import com.ayusha.services.common.exceptions.InvalidServiceRequestException;
import com.custom.logger.logging.Logger;
import com.custom.logger.manager.LogManager;
/**
 * 
 * @author Finch
 * Date : 01-Aug-2019
 * Product Service Methods
 */
@Service
public class SymptomService implements ISymptomService{
	
	/** LOGGER **/
	private static Logger LOG = LogManager.getLogger(SymptomService.class);
	
	/** iSymptomRepository **/
	@Autowired
	private ISymptomRepository iSymptomRepository;
	
	/**iProductSymptomsRepository  **/
	@Autowired
	private IProductSymptomsRepository iProductSymptomsRepository;
	/**
	 * default constructor
	 */
	public SymptomService() {
		
	}

	/**
	 * getSymptom
	 */
	public SymptomDataModel getSymptom(String id) throws DataPersistenceOperationException,InvalidServiceRequestException{
		SymptomDataModel symptomDataModel = new SymptomDataModel();
		Optional<SymptomEntity> modelEntity=  iSymptomRepository.findById(Integer.parseInt(id));
		
		symptomDataModel.setId(Integer.parseInt(""+modelEntity.get().getId()));
		symptomDataModel.setDescription(modelEntity.get().getDescription());
		symptomDataModel.setSymptom(modelEntity.get().getSymptom());
		symptomDataModel.setSymptomId(modelEntity.get().getSymptomId());
		return symptomDataModel;
	}
	
	/** 
	 * getProductSymptom
	 */
	public SymptomsDataModel getProductSymptom(String productId) throws DataPersistenceOperationException,InvalidServiceRequestException{
		SymptomDataModel symptomDataModel = new SymptomDataModel();
		ProductSymptomsEntity productSymptomsEntity=null;
		SymptomEntity symptomEntity =null;
		List<SymptomDataModel> lstSymptomDataModel = new ArrayList();
		List<ProductSymptomsEntity> lstProductSymptomsEntity = iProductSymptomsRepository.findSymptomById(productId);
		int size=0;
		
		size = lstProductSymptomsEntity.size();
		SymptomsDataModel symptomsDataModel = new SymptomsDataModel();
		symptomsDataModel.setProductId(productId);
		symptomsDataModel.setLstSymptomDataModel(new ArrayList());
		for(int index=0; index<size; index++) {
			productSymptomsEntity = (ProductSymptomsEntity)lstProductSymptomsEntity.get(index);
			symptomDataModel = new SymptomDataModel();
			symptomEntity = iSymptomRepository.findSymptomById(productSymptomsEntity.getSymptomId());
			symptomDataModel.setId(Integer.parseInt(""+symptomEntity.getId()));
			symptomDataModel.setDescription(symptomEntity.getDescription());
			symptomDataModel.setSymptom(symptomEntity.getSymptom());
			symptomDataModel.setProductId(productId);
			symptomDataModel.setSymptomId(symptomEntity.getSymptomId());
			lstSymptomDataModel.add(symptomDataModel);
			symptomsDataModel.setLstSymptomDataModel(lstSymptomDataModel);
		}
		
		return symptomsDataModel;
	}
	/**
	 * updateSymptom
	 */
	public  SymptomDataModel updateSymptom(SymptomDataModel symptomDataModel) throws DataPersistenceOperationException,InvalidServiceRequestException{
		SymptomEntity modelEntity = new SymptomEntity();
		
		modelEntity.setId(symptomDataModel.getId());
		modelEntity.setDescription(symptomDataModel.getDescription());
		modelEntity.setSymptom(symptomDataModel.getSymptom());
		modelEntity.setSymptomId(symptomDataModel.getSymptomId());
		symptomDataModel.setSymptomId(symptomDataModel.getSymptomId());
		iSymptomRepository.save(modelEntity);
		return symptomDataModel;
	}
	/**
	 * add
	 */
	public SymptomDataModel add(SymptomDataModel symptomDataModel) throws DataPersistenceOperationException,InvalidServiceRequestException{
		
		SymptomEntity modelEntity = new SymptomEntity();
		
		modelEntity.setId(symptomDataModel.getId());
		modelEntity.setDescription(symptomDataModel.getDescription());
		modelEntity.setSymptom(symptomDataModel.getSymptom());
		modelEntity.setSymptomId(symptomDataModel.getSymptomId());
		ProductSymptomsEntity productSymptomsEntity = new ProductSymptomsEntity();
		productSymptomsEntity.setSymptomId(symptomDataModel.getSymptomId());
		productSymptomsEntity.setProductId(symptomDataModel.getProductId());
		iProductSymptomsRepository.save(productSymptomsEntity);
		
		symptomDataModel.setSymptomId(symptomDataModel.getSymptomId());
		iSymptomRepository.save(modelEntity);
		return symptomDataModel;
	}
}
