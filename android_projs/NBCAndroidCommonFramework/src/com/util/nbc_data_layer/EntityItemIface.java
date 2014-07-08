package com.util.nbc_data_layer;

import com.util.nbc_data_layer.nbcGreenDaoSrcGen.UrlImgFileTable;

/**
 * this will be implemented by each POJO from the green dao generated code.
 * This is part of the visitor pattern from the GOF.
 */
public interface EntityItemIface 
{
	public void accept
	(EntityVisitorIface entityVisitorIface, NBCDataBaseHelper.T_UrlTypeToId typeID, UrlImgFileTable urlImgFileTable);
}
