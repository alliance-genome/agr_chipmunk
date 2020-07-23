package org.alliancegenome.agr_submission.main;

import org.alliancegenome.agr_submission.util.AESUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class GitTest {

	public static void main(String[] args) {
		//GitHelper helper = new GitHelper();
		
		//helper.setupSchemaRelease("1.0.1.0");
		//log.debug(ConfigHelper.getValidationSoftwarePath());
		
		AESUtil util = new AESUtil();
		
		String user = "";
		String key = "";
		String enc = util.encrypt(user, key);
		
		System.out.println(enc);
		
		log.info(util.decrypt(enc, key));
	}

}
