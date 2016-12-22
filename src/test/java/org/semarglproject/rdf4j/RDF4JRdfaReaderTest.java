/**
 * Copyright 2012-2013 the Semargl contributors. See AUTHORS for more details.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.semarglproject.rdf4j;

import org.eclipse.rdf4j.RDF4JException;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.rio.ParserConfig;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFHandlerException;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.BasicParserSettings;
import org.eclipse.rdf4j.rio.helpers.RDFaParserSettings;
import org.eclipse.rdf4j.rio.helpers.RDFaVersion;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;
import org.semarglproject.rdf.ParseException;
import org.semarglproject.rdf.rdfa.RdfaTestSuiteHelper;
import org.semarglproject.rdf4j.rdf.rdfa.SemarglParserSettings;
import org.semarglproject.test.TestNGHelper;
import org.semarglproject.vocab.RDFa;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Method;

import static org.semarglproject.rdf.rdfa.RdfaTestSuiteHelper.SaveToFileCallback;
import static org.semarglproject.rdf.rdfa.RdfaTestSuiteHelper.runTestBundle;

public class RDF4JRdfaReaderTest {

    private Model model;
    private SaveToFileCallback sesameCallback = new SaveToFileCallback() {
        @Override
        public void run(Reader input, String inputUri, Writer output, short rdfaVersion) throws ParseException {
            try {
                RDFParser rdfParser = Rio.createParser(RDFFormat.RDFA);
                ParserConfig parserConfig = rdfParser.getParserConfig();
                parserConfig.set(RDFaParserSettings.RDFA_COMPATIBILITY, RDFaVersion.RDFA_1_1);
                parserConfig.set(RDFaParserSettings.VOCAB_EXPANSION_ENABLED, true);
                parserConfig.set(SemarglParserSettings.PROCESSOR_GRAPH_ENABLED, true);
                parserConfig.addNonFatalError(BasicParserSettings.VERIFY_DATATYPE_VALUES);
                parserConfig.addNonFatalError(BasicParserSettings.VERIFY_LANGUAGE_TAGS);
                parserConfig.addNonFatalError(BasicParserSettings.VERIFY_RELATIVE_URIS);
                rdfParser.setRDFHandler(new StatementCollector(model));
                rdfParser.parse(input, inputUri);
            } catch (RDF4JException e) {
                // do nothing
                // FIXME: Why not fail quickly here?
            } catch (IOException e) {
                // do nothing
            } finally {
                try {
                    Rio.write(model, output, RDFFormat.TURTLE);
                } catch (RDFHandlerException e) {
                    // do nothing
                }
            }
        }

        @Override
        public String getOutputFileExt() {
            return "ttl";
        }
    };

    @BeforeClass
    public void init() throws SAXException, ClassNotFoundException {
        model = new LinkedHashModel();
    }

    @BeforeMethod
    public void setUp() {
        model.clear();
    }

    @DataProvider
    public static Object[][] getTestSuite(Method method) {
        String methodName = method.getName();
        String rdfaVersion = "rdfa1.0";
        if (methodName.startsWith("runRdfa11")) {
            rdfaVersion = "rdfa1.1";
        }
        String fileFormat = methodName.substring(9, methodName.indexOf("Tests")).toLowerCase();
        return TestNGHelper.toArray(RdfaTestSuiteHelper.getTestSuite(rdfaVersion, fileFormat));
    }

    @Test(dataProvider = "getTestSuite")
    public void runRdfa10Xhtml1Tests(RdfaTestSuiteHelper.TestCase testCase) {
        runTestBundle(testCase, sesameCallback, RDFa.VERSION_10);
    }

    @Test(dataProvider = "getTestSuite")
    public void runRdfa10SvgTests(RdfaTestSuiteHelper.TestCase testCase) {
        runTestBundle(testCase, sesameCallback, RDFa.VERSION_10);
    }

    @Test(dataProvider = "getTestSuite")
    public void runRdfa11Html4Tests(RdfaTestSuiteHelper.TestCase testCase) {
        runTestBundle(testCase, sesameCallback, RDFa.VERSION_11);
    }

    @Test(dataProvider = "getTestSuite")
    public void runRdfa11Xhtml1Tests(RdfaTestSuiteHelper.TestCase testCase) {
        runTestBundle(testCase, sesameCallback, RDFa.VERSION_11);
    }

    @Test(dataProvider = "getTestSuite")
    public void runRdfa11Html5Tests(RdfaTestSuiteHelper.TestCase testCase) {
        runTestBundle(testCase, sesameCallback, RDFa.VERSION_11);
    }

    @Test(dataProvider = "getTestSuite")
    public void runRdfa11XmlTests(RdfaTestSuiteHelper.TestCase testCase) {
        runTestBundle(testCase, sesameCallback, RDFa.VERSION_11);
    }

    @Test(dataProvider = "getTestSuite")
    public void runRdfa11SvgTests(RdfaTestSuiteHelper.TestCase testCase) {
        runTestBundle(testCase, sesameCallback, RDFa.VERSION_11);
    }

}
