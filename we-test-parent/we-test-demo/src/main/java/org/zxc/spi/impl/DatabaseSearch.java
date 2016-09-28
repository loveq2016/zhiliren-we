package org.zxc.spi.impl;

import java.util.List;

import javax.print.Doc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zxc.spi.Search;

public class DatabaseSearch implements Search {
	
	private static final Logger logger = LoggerFactory.getLogger(DatabaseSearch.class) ;

	@Override
	public List<Doc> search(String keyword) {
		logger.info("now use database search. keyword:" + keyword);  
		return null;
	}

}
