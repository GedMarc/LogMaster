package za.co.mmagon.logger.handlers;

import java.lang.annotation.*;

/**
 *
 * @author GedMarc
 * @since 13 Dec 2016
 *
 */
@Target(
        {
            ElementType.FIELD, ElementType.TYPE, ElementType.LOCAL_VARIABLE, ElementType.PACKAGE, ElementType.METHOD, ElementType.TYPE_PARAMETER, ElementType.TYPE_USE
        })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface LogMasterHandler
{

    /**
     * Returns the name value associated with the handler
     *
     * @return
     */
    public String value();
}
