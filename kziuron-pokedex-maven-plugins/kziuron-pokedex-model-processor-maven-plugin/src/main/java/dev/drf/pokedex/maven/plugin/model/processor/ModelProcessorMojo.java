package dev.drf.pokedex.maven.plugin.model.processor;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "process-model", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class ModelProcessorMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Parameter(property = "process.model.package", required = true, readonly = true)
    private String modelPackage;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("!! Hello Mojo !!");
        getLog().info("Package: " + modelPackage);
    }
}
