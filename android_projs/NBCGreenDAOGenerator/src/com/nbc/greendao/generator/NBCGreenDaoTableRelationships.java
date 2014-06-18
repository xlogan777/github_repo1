package com.nbc.greendao.generator;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;

/**
 * this class will handler all the table relationships 
 * needed for the data model.
 * 
 * author J.Mena
 *
 */
public class NBCGreenDaoTableRelationships 
{
	/*
	 * this will setup all the relationships for the content item and its
	 * internal details.
	 */
	public static void createRelationshipContentItemsToDetails
	(Entity cntItem, Entity cntLeadMedia, Entity cntMedia, Entity cntItemDetail)
	{
//simulate the 1:1 table relationship
		//for production will create the 1:empty_relationship since we wont be accessing the 
		//specific items details of the content item outside of the content item table.
		
		//create FK relationship with lead_media table
		Property fk_cntLeadMediaID = cntItem.addIntProperty("cntLeadMediaID").notNull().getProperty();
		cntItem.addToOne(cntLeadMedia, fk_cntLeadMediaID);
		
		//create FK relationship with content media table
		Property fk_cntMediaID = cntItem.addIntProperty("cntMediaID").notNull().getProperty();
		cntItem.addToOne(cntMedia, fk_cntMediaID);
		
		//create FK relationship with content detail table
		Property fk_cntItemDetailID = cntItem.addIntProperty("cntItemDetailID").notNull().getProperty();
		cntItem.addToOne(cntItemDetail, fk_cntItemDetailID);
//simulate the 1:1 table relationship
	}
	
}
