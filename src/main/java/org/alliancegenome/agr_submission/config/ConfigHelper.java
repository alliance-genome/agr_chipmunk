package org.alliancegenome.agr_submission.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class ConfigHelper {

	private static Properties configProperties = new Properties();

	private static HashMap<String, String> defaults = new HashMap<>();
	private static HashMap<String, String> config = new HashMap<>();
	private static Set<String> allKeys;
	private static boolean init = false;

	public ConfigHelper() {
		init();
	}

	public static void init() {
		/* The purpose of the default values is that these are the values required by the application to run
		 * This config help looks at 3 methods to over write these values.
		 * 1. Via System Properties with are passed to the application at run time via the -DNAME=value
		 * 2. Via a config.properties file, this file only need to over write the values it wants to change from the defaults
		 * otherwise the config will be loaded from the default values.
		 * 3. Via the environment. Before the application is run export NAME=value in the shell the config helper will load the
		 * NAME and value that it finds in the environments and use those values rather then the defaults.
		 * The names and values need to not be changed as other things might depend on the defaulted values for executing. For each
		 * key in the defaults map there should be a corresponding get method for that value.
		 */

		defaults.put("API_ACCESS_TOKEN", null); // Api Value
		
		defaults.put("DEBUG", "false");

		defaults.put("AWS_ACCESS_KEY", null);
		defaults.put("AWS_SECRET_KEY", null);
		defaults.put("AWS_BUCKET_NAME", "mod-datadumps-dev"); // This needs to always be a dev bucket unless running in production
		
		defaults.put("FMS_HOST", "localhost:8080");
		
		defaults.put("ENC_PASS_FILE", "/data/encryptionPasswordKey");
		
		allKeys = defaults.keySet();

		if (configProperties.size() == 0) {
			InputStream in = ConfigHelper.class.getClassLoader().getResourceAsStream("config.properties");
			if (in == null) {
				log.debug("No config.properties file, other config options will be used");
			} else {
				try {
					configProperties.load(in);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		for (String key : allKeys) {
			// First checks the -D params and sets config[key] = value otherwise it will be null.
			if (config.get(key) == null) config.put(key, loadSystemProperty(key));
			// Second checks the config.properties file built into the application otherwise it will be null.
			if (config.get(key) == null) config.put(key, loadConfigProperty(key));
			// Third checks the environment for a NAME = value otherwise leaves it null.
			if (config.get(key) == null) config.put(key, loadSystemENVProperty(key));
			// Lastly loads the default value for NAME = value and loadDefaultProperty ensures it won't be null.
			if (config.get(key) == null) config.put(key, loadDefaultProperty(key));
		}
		printProperties();
		init = true;
	}
	
	public static void printProperties() {
		log.info("Running with Properties:");
		for (String key : allKeys) {
			log.info("\t" + key + ": " + config.get(key));
		}
	}

	private static String loadSystemProperty(String key) {
		String ret = System.getProperty(key);
		if (ret != null) log.debug("Found: -D " + key + "=" + ret);
		return ret;
	}

	private static String loadConfigProperty(String key) {
		String ret = configProperties.getProperty(key);
		if (ret != null) log.debug("Config File Property: " + key + "=" + ret);
		return ret;
	}

	private static String loadSystemENVProperty(String key) {
		String ret = System.getenv(key);
		if (ret != null) log.debug("Found Enviroment ENV[" + key + "]=" + ret);
		return ret;
	}

	private static String loadDefaultProperty(String key) {
		String ret = defaults.get(key);
		if (ret != null) log.debug("Setting default: " + key + "=" + ret);
		return ret;
	}
	
	// Accessor methods 
	
	public static String getAWSAccessKey() {
		if(!init) init();
		return config.get("AWS_ACCESS_KEY");
	}
	public static String getAWSSecretKey() {
		if(!init) init();
		return config.get("AWS_SECRET_KEY");
	}
	public static boolean getDebug() {
		if(!init) init();
		return Boolean.parseBoolean(config.get("DEBUG"));
	}
	public static String getAWSBucketName() {
		if(!init) init();
		return config.get("AWS_BUCKET_NAME");
	}
	public static String getFMSHost() {
		if(!init) init();
		return config.get("FMS_HOST");
	}
	public static String getJavaLineSeparator() {
		if(!init) init();
		return System.getProperty("line.separator");
	}
	public static String getJavaTmpDir() {
		if(!init) init();
		return System.getProperty("java.io.tmpdir");
	}
	public static String getValidationSoftwarePath() {
		if(!init) init();
		return getJavaTmpDir();
	}
	
	public static String getEncryptionPasswordKey() throws Exception {
		if(!init) init();
		String filePath = config.get("ENC_PASS_FILE");
		BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
		String password = reader.readLine();
		reader.close();
		return password;
	}

}
