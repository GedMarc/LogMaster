module com.guicedee.logmaster {
	exports com.guicedee.logger;
	exports com.guicedee.logger.logging;
	exports com.guicedee.logger.handlers;

	requires transitive java.logging;
	requires transitive jakarta.validation;

}
