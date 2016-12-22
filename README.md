Semargl integration with RDF4J
==============================

Semargl is a modular framework for crawling [linked data](http://en.wikipedia.org/wiki/Linked_data)
from structured documents. The main goal of the project is to provide lightweight
and performant tool without excess dependencies.

This module integrates with Eclipse RDF4J to provide direct access to the RDFa parser using the RDF4J Parser APIs.

[![Maven Central](https://img.shields.io/maven-central/v/org.semarglproject/semargl-rdf4j.svg?style=flat-square)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.semarglproject%22%20semargl-rdf4j)
[![Build Status](https://img.shields.io/travis/semarglproject/semargl-rdf4j/master.svg?style=flat-square)](https://travis-ci.org/semarglproject/semargl-rdf4j)
[![Coverage Status](https://img.shields.io/coveralls/semarglproject/semargl-rdf4j.svg?style=flat-square)](https://coveralls.io/r/semarglproject/semargl-rdf4j?branch=master)

Use from Maven
==============

```xml
<dependency>
    <groupId>org.semarglproject</groupId>
    <artifactId>semargl-rdf4j</artifactId>
    <version>0.7</version>
</dependency>
```

```java
Model parsed = Rio.parse(inputStream, baseURI, RDFFormat.RDFA);
```

Build
=====

To build framework just run `mvn clean install`.
