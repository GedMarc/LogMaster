module com.jwebmp.logmaster {
	exports com.jwebmp.logger;
	exports com.jwebmp.logger.logging;
	exports com.jwebmp.logger.handlers;

	requires transitive java.logging;
	requires java.validation;
}
