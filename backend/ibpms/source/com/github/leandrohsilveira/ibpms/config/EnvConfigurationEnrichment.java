package com.github.leandrohsilveira.ibpms.config;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.inject.Singleton;

import kikaha.config.Config;
import kikaha.config.ConfigEnrichment;
import kikaha.config.MergeableConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class EnvConfigurationEnrichment implements ConfigEnrichment {

	@Override
	public Config enrich(Config mergeableConfig) {
		MergeableConfig config = new MergeableConfig(mergeableConfig.toMap(), "");
		String configFilePath = Optional.ofNullable(System.getenv("ENV_CONFIG_FILE")).orElse("/home/kikaha/ibpms/env.yml");
		File configFile = new File(configFilePath);
		if(configFile.exists()) {
			try {
				config.load(configFile);
			} catch (IOException e) {
				log.error("Unable to read or parse the environments configuration properties of file {}", configFilePath, e);
			}
		} else {
			log.warn("The environments configuration properties file {} does not exists, using application default configurations", configFilePath);
		}
		return config; 
	}

}
