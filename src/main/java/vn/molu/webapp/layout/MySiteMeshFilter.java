package vn.molu.webapp.layout;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

import java.util.logging.Logger;

/**
 * @author phaolo
 * @since 2020-03-09
 */
public class MySiteMeshFilter extends ConfigurableSiteMeshFilter {
    private  final Logger log = Logger.getLogger(getClass().toString());

    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        log.info("Site Mesh layout template.");
// tất cả các đường dẫn đều dô decorator -> bắt đầu từ main ( trừ login, logout, sample)
        builder.addDecoratorPath("/*", "/decorator/main.jsp")

                .addExcludedPath("/login*")
                .addExcludedPath("/logout*")
                .addExcludedPath("/sample*");
//                .addExcludedPath("/mportal/*");
    }
}
