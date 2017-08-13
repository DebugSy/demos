package com.shi.java.JarScan;

import org.junit.Test;

public class SpecifyPathScanningCandidateProviderTest {

    @Test
    public void testFindCandidateComponents(){
        SpecifyPathScanningCandidateProvider provider = new SpecifyPathScanningCandidateProvider();
        provider.findCandidateComponents("file:D:\\tmp\\jars");
    }

}
