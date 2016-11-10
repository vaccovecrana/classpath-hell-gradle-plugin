package classpathHell

import org.gradle.api.artifacts.ResolvedArtifact
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ClasspathHellPluginExtension {

    static Logger logger = LoggerFactory.getLogger('classpathHell')
    static def log(String s) {
        logger.debug("classpathHell: " + s)
    }

    /* utility */
    static boolean excludeArtifactPaths(List<String> excludedPatterns, ResolvedArtifact f) {
        boolean inc = true
        excludedPatterns.each { String ex ->
            if (f.file.getAbsolutePath().matches(ex))
                inc = false
        }

        return inc
    }

    /* override to supply a list of artifacts to exclude from the check (assuming includeArtifact has not been overridden).
    */
    List<String> artifactExclusions = []

    /*
    override to provide an alternative inclusion strategy to the default.
     */
    def Closure<ResolvedArtifact> includeArtifact = { ResolvedArtifact f ->
        // default strategy is to exclude artefacts according to a black list
        excludeArtifactPaths(artifactExclusions.toList(), f)
    }

    /* utility */
    static boolean excludeMatches(List<String> excludedPatterns, String f) {

        boolean inc = true
        excludedPatterns.each { ex ->
            log("comparing resource " + f + " to " + ex)
            if (f.matches(ex))
                inc = false
        }

        return inc
    }


    /* A convenience constant defining a set of common defaults that are not very interesting.
    */
    List<String> CommonResourceExclusions() {
        return [
            "^rootdoc.txt\$",
            "^about.html\$",
            "^NOTICE\$",
            "^LICENSE\$",
            "^LICENSE.*.txt\$",
            "^META-INF/.*",
            ".*/\$",
            ".*com/sun/.*",
            ".*javax/annotation/.*"
    ] }

    /** Optionall override or modify to provide an alternative list of resources to exclude from the check.
     */
    List<String> resourceExclusions = []

    /*
    override to provide an alternative inclusion strategy to the default.
     */
    def Closure<Boolean> includeResource = { String f ->
        excludeMatches(resourceExclusions, f)
    }
}