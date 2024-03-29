package com.ayusha.repair.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayusha.repair.services.data.models.FaultyItemDataModel;
import com.ayusha.repair.services.data.models.FaultyItemsDataModel;
import com.ayusha.repair.services.data.models.SymptomDataModel;
import com.ayusha.repair.services.data.models.SymptomsDataModel;
import com.ayusha.repair.services.entity.FaultyItemEntity;
import com.ayusha.repair.services.entity.SymptomsEntity;
import com.ayusha.repair.services.repository.IFaultyItemRepository;
import com.ayusha.services.common.exceptions.DataPersistenceOperationException;
import com.ayusha.services.common.exceptions.InvalidServiceRequestException;
import com.ayusha.services.common.exceptions.ResourceNotFoundException;
import com.custom.logger.logging.Logger;
import com.custom.logger.manager.LogManager;



/**
 * 
 * @author author1
 * Date : 01-Aug-2019
 * Ticket Model class
 * 
 * 1. Recording (single):
 *      a. Persisting in DB
 *      b. on Success - sending an email/sms to customer
 *      c. assigning service invoking
 * 
 * 2. Update:
 *     a. On change of status - sending an email/sms notification
 *     
 * 3. Batch Recording:
 *     a.  Persisting in DB
 *     b. on Success - sending an email/sms to customer -seggregating and sending an single email
 *     c. assigning service invoking - Individually
 *      
 *  4. Search:
 *      a. search based on date, user, customer,logged date, issue,servicetype,serialnumber
 *      
 *  5. Sorting:
 *      a. soring based on logged date,status,servicetype (ASC | DSC)
 */
@Service
public class RepairFaultyItemsService implements IRepairFaultyItemsService{
	
	/** LOGGER **/
	private static Logger LOG = LogManager.getLogger(RepairFaultyItemsService.class);
	
	/** repository **/
	@Autowired
	private IFaultyItemRepository iFaultyItemRepository;
	
	
	
	/**
	 * default constructor
	 */
	public RepairFaultyItemsService() {
		LOG.info("Ticket Service Constructor");
	}
	
	/**
	 * add ticket
	 */
	public FaultyItemDataModel save(FaultyItemDataModel faultyItemDataModel) throws DataPersistenceOperationException,InvalidServiceRequestException{
		FaultyItemEntity faultyItemEntity = new FaultyItemEntity();
		faultyItemEntity.setId(faultyItemDataModel.getId());
		faultyItemEntity.setJob_id(faultyItemDataModel.getJobId());
		faultyItemEntity.setPartId(faultyItemDataModel.getPartId());
		faultyItemEntity.setHeading(faultyItemDataModel.getHeading());
		faultyItemEntity.setMedia(faultyItemDataModel.getMedia());
		faultyItemEntity.setFaulty(faultyItemDataModel.getFaulty());
		iFaultyItemRepository.save(faultyItemEntity);
		return faultyItemDataModel;
	}
		
	/** find job notes by id **/
	public FaultyItemsDataModel getFaultyItemsDataModel(String jobId) throws DataPersistenceOperationException,InvalidServiceRequestException,ResourceNotFoundException,Exception{
		FaultyItemsDataModel faultyItemsDataModel = new FaultyItemsDataModel();
		faultyItemsDataModel.setJob_id(jobId);
		faultyItemsDataModel.setLstFaultyItems(getFaultyItems(jobId));
		return faultyItemsDataModel;
	}
	
	/** find job notes by id **/
	public List<FaultyItemDataModel> getFaultyItems(String jobId) throws DataPersistenceOperationException,InvalidServiceRequestException,ResourceNotFoundException,Exception{
		
		List<FaultyItemDataModel> lstFaultyItemDataModel = new ArrayList();
		FaultyItemDataModel faultyItemDataModel = null;
		FaultyItemEntity faultyItemEntity = null;
		int size=0;
		
		List<FaultyItemEntity> lstFaultyItemEntity= iFaultyItemRepository.findAllFaultyItemsByJobId(jobId);
		
		size = lstFaultyItemEntity.size();
		
		for(int index=0; index<size; index++) {
			faultyItemDataModel = new FaultyItemDataModel();
			faultyItemEntity = lstFaultyItemEntity.get(index);
			faultyItemDataModel.setId(Integer.parseInt(""+faultyItemEntity.getId()));
			faultyItemDataModel.setJobId(faultyItemEntity.getJob_id());
			faultyItemDataModel.setPartId(faultyItemEntity.getPartId());
			faultyItemDataModel.setMedia(faultyItemEntity.getMedia());
			faultyItemDataModel.setHeading(faultyItemEntity.getHeading());
			faultyItemDataModel.setFaulty(faultyItemEntity.getFaulty());
			lstFaultyItemDataModel.add(faultyItemDataModel);
			
		}
		
		return lstFaultyItemDataModel;
	}
	
	
	/**
	 * add ticket
	 */
	public void deleteFaultyItems(String jobId) throws DataPersistenceOperationException,InvalidServiceRequestException{
		iFaultyItemRepository.deleteFaultyItemsByJobId(jobId);
	}
	
	/**
	 * add ticket
	 */
	public void updateFaultyItems(FaultyItemsDataModel faultyItemsDataModel) throws DataPersistenceOperationException,InvalidServiceRequestException{
		int size=0;
		deleteFaultyItems(faultyItemsDataModel.getJob_id());
		List<FaultyItemDataModel> lstSymptomDataModel = faultyItemsDataModel.getLstFaultyItems();
		
		size = lstSymptomDataModel.size();
		
		for(int index=0; index<size;index++) {
			save(lstSymptomDataModel.get(index));
		}
	}
}
