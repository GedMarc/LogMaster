module com.jwebmp.logmaster {
	exports com.jwebmp.logger;
	exports com.jwebmp.logger.logging;

	requires transitive java.logging;
	requires java.validation;
}
